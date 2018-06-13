package com.github.wxiaoqi.security.auth.client.interceptor;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.config.ServiceAuthConfig;
import com.github.wxiaoqi.security.auth.client.exception.JwtTokenExpiredException;
import com.github.wxiaoqi.security.auth.client.feign.ServiceAuthFeign;
import com.github.wxiaoqi.security.auth.client.jwt.ServiceAuthUtil;
import com.github.wxiaoqi.security.common.constant.CommonConstants;
import com.github.wxiaoqi.security.common.exception.auth.ClientForbiddenException;
import com.github.wxiaoqi.security.common.exception.auth.TokenErrorException;
import com.github.wxiaoqi.security.common.msg.BaseResponse;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.jwt.IJWTInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ace on 2017/9/12.
 */
@SuppressWarnings("ALL")
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(ServiceAuthRestInterceptor.class);

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Autowired
    private ServiceAuthFeign serviceAuthFeign;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    private List<String> allowedClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 配置该注解，说明不进行服务拦截
        IgnoreClientToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreClientToken.class);
        if (annotation == null)
            annotation = handlerMethod.getMethodAnnotation(IgnoreClientToken.class);
        if(annotation!=null)
            return super.preHandle(request, response, handler);
        if(this.allowedClient==null)
            refresh();
        String token = request.getHeader(serviceAuthConfig.getTokenHeader());
        if(token==null){
        	request.getRequestDispatcher("/account/loginmsg").forward(request,response);
        	return false ;
        }
        IJWTInfo infoFromToken = serviceAuthUtil.getInfoFromToken(token);
        String uniqueName = infoFromToken.getUniqueName(); // clientName（code）
        for(String client:allowedClient){
            if(client.equals(uniqueName)){
                return super.preHandle(request, response, handler);
            }
        }
        throw new ClientForbiddenException("Client is Forbidden!");
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void refresh() {
        logger.debug("refresh allowedClient.....");
        BaseResponse resp = serviceAuthFeign.getAllowedClient(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            ObjectRestResponse<List<String>> allowedClient = (ObjectRestResponse<List<String>>) resp;
            this.allowedClient = allowedClient.getData();
        }
    }
}
