package com.github.wxiaoqi.security.gate.filter;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.api.authority.PermissionInfo;
import com.github.wxiaoqi.security.api.entity.AppUrl;
import com.github.wxiaoqi.security.api.log.LogInfo;
import com.github.wxiaoqi.security.api.user.UserInfo;
import com.github.wxiaoqi.security.auth.client.config.ServiceAuthConfig;
import com.github.wxiaoqi.security.auth.client.config.UserAuthConfig;
import com.github.wxiaoqi.security.auth.client.jwt.ServiceAuthUtil;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.common.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.auth.TokenErrorResponse;
import com.github.wxiaoqi.security.common.msg.auth.TokenForbiddenResponse;
import com.github.wxiaoqi.security.common.util.ClientUtil;
import com.github.wxiaoqi.security.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.gate.feign.IAccountService;
import com.github.wxiaoqi.security.gate.feign.ILogService;
import com.github.wxiaoqi.security.gate.feign.IUserService;
import com.github.wxiaoqi.security.gate.utils.DBLog;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-23 8:25
 */
@Component
@Slf4j
public class SessionAccessFilter extends ZuulFilter {

    @Autowired
    private IUserService userService;
    @Autowired
    private ILogService logService;
    @Autowired
    private IAccountService accountService;

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Value("${zuul.prefix}")
    private String zuulPrefix;
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        final String method = request.getMethod();
        BaseContextHandler.setToken(null);
        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
            ctx.addZuulRequestHeader(serviceAuthConfig.getTokenHeader(),serviceAuthUtil.getClientToken());
            //TODO 在app端登陆的url中根据手机号+密码生成token并放入header中
            return null;
        }
        if("noAuth".equals(request.getHeader("Authorization"))){
            return null;
        }

        //需要拦截的地址（根据url是否包含“app”来区分前后台拦截）
        if(requestUri.contains("app")){
            // 申请客户端密钥头
            ctx.addZuulRequestHeader(serviceAuthConfig.getTokenHeader(),serviceAuthUtil.getClientToken());
            //app端拦截处理
            //根据app的token解析account，判断account访问此url是否需要登录
           // Boolean needLogin = needLogin(requestUri,method);
          
                IJWTInfo account = null;
                try {
                    account = getJWTUser(request);
                } catch (Exception e) {
                    setFailedRequest(JSON.toJSONString(new TokenErrorResponse(e.getMessage())),200);
                }
            return null;

        }else{
            //后台管理系统拦截处理

            IJWTInfo user = null;
            try {
                user = getJWTUser(request);
                ctx.addZuulRequestHeader(userAuthConfig.getTokenHeader(),BaseContextHandler.getToken());
            } catch (Exception e) {
                setFailedRequest(JSON.toJSONString(new TokenErrorResponse(e.getMessage())),200);
                return null;
            }

            List<PermissionInfo> permissionInfos = userService.getAllPermissionInfo();
            // 判断资源是否启用权限约束
            Collection<PermissionInfo> result = getPermissionInfos(requestUri, method, permissionInfos);
            if(result.size()>0){
                if(requestUri.contains("/delSession")){//退出登录清空session
                    request.getSession().setAttribute("permission",null);
                }else{
                    checkAllow(requestUri, method, ctx, user.getUniqueName());
                }
            }
            // 申请客户端密钥头
            ctx.addZuulRequestHeader(serviceAuthConfig.getTokenHeader(),serviceAuthUtil.getClientToken());
            return null;
        }

    }

    /**
     * 获取目标权限资源
     * @param requestUri
     * @param method
     * @param serviceInfo
     * @return
     */
    private Collection<PermissionInfo> getPermissionInfos(final String requestUri, final String method, List<PermissionInfo> serviceInfo) {
        return Collections2.filter(serviceInfo, new Predicate<PermissionInfo>() {
                @Override
                public boolean apply(PermissionInfo permissionInfo) {
                    String url = permissionInfo.getUri();
                    String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                    String regEx = "^" + uri + "$";
                    return (Pattern.compile(regEx).matcher(requestUri).find() || requestUri.startsWith(url + "/"))
                            && method.equals(permissionInfo.getMethod());
                }
            });
    }

    private void setCurrentUserInfoAndLog(RequestContext ctx, String username, PermissionInfo pm) {
        UserInfo info = userService.getUserByUsername(username);
        String host =  ClientUtil.getClientIp(ctx.getRequest());
        ctx.addZuulRequestHeader("userId", info.getId());
        ctx.addZuulRequestHeader("userName", URLEncoder.encode(info.getName()));
        ctx.addZuulRequestHeader("userHost", ClientUtil.getClientIp(ctx.getRequest()));
        LogInfo logInfo = new LogInfo(pm.getMenu(),pm.getName(),pm.getUri(),new Date(),info.getId(),info.getName(),host);
        DBLog.getInstance().setLogService(logService).offerQueue(logInfo);
    }

    /**
     * 返回session中的用户信息
     * @param request
     * @return
     */
    private IJWTInfo getJWTUser(HttpServletRequest request) throws Exception {
        String authToken = request.getHeader(userAuthConfig.getTokenHeader());
        if(StringUtils.isBlank(authToken)){
            authToken = request.getParameter("token");
        }
        BaseContextHandler.setToken(authToken);
        return userAuthUtil.getInfoFromToken(authToken);
    }

    /**
     * 读取权限
     * @param request
     * @param username
     * @return
     */
    private List<PermissionInfo> getPermissionInfos(HttpServletRequest request, String username) {
        List<PermissionInfo> permissionInfos;
        if (request.getSession().getAttribute("permission") == null) {
            permissionInfos = userService.getPermissionByUsername(username);
            request.getSession().setAttribute("permission", permissionInfos);
        } else {
            permissionInfos = (List<PermissionInfo>) request.getSession().getAttribute("permission");
        }
        return permissionInfos;
    }

    /**
     * 权限校验
     * @param requestUri
     * @param method
     */
    private void checkAllow(final String requestUri, final String method ,RequestContext ctx,String username) {
        log.debug("uri：" + requestUri + "----method：" + method);
        List<PermissionInfo> permissionInfos = getPermissionInfos(ctx.getRequest(), username) ;
        Collection<PermissionInfo> result = getPermissionInfos(requestUri, method, permissionInfos);
        if (result.size() <= 0) {
            setFailedRequest(JSON.toJSONString(new TokenForbiddenResponse("Token Forbidden!")), 200);
        } else{
            PermissionInfo[] pms =  result.toArray(new PermissionInfo[]{});
            PermissionInfo pm = pms[0];
            if(!"GET".equals(method)){
                setCurrentUserInfoAndLog(ctx, username, pm);
            }
        }
    }

    /**
     * 访问app端的url是否需要登录
     * @param requestUri
     * @param method
     * @return
     */
    private Boolean needLogin(final String requestUri,final String method){
        //查询所有需要登录的url
        List<AppUrl> appUrls = accountService.getAllAppLoginUrl();
        for(AppUrl appUrl : appUrls){
            if(requestUri.equals(appUrl.getUrl())&&method.equals(appUrl.getMethod())){
                return true;
            }
        }
        return false;
    }

    /**
     * URI是否以什么打头
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * Reports an error message given a response body and code.
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
        log.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
//            throw new RuntimeException("Code: " + code + ", " + body); //optional
        }
    }
}
