package com.igrowth.app.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.constant.CommonConstants;
import com.github.wxiaoqi.security.common.context.BaseContextHandler;
import com.igrowth.app.biz.AccountBiz;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-15 8:48
 */
public class BaseController{
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected AccountBiz accountBiz;
	Logger logger = Logger.getLogger(BaseController.class);

    public String getCurrentUserName(){
        String authorization = request.getHeader("Authorization");
        return new String(Base64Utils.decodeFromString(authorization));
    }
    
	public Long getCurrentChildId() {
		Long accountId = getCurrentAccountId();
		if (accountId == null) {
			logger.error("请求异常，accountId为空");
			return null;
		} else {
			Account account = accountBiz.findAccountById(accountId);
			return account.getCurrentCid();
		}
	}
    
	public Long getCurrentAccountId() {
		if("noAuth".equals(request.getHeader("Authorization"))){
			logger.error("请求异常，accountId为空");
			return null;
		}
		if (StringUtils.isEmpty(BaseContextHandler.getUserID())) {
			logger.error("请求异常，accountId为空");
			return null;
		}	
		return Long.valueOf(BaseContextHandler.getUserID());
	}
    
	@IgnoreClientToken
	@RequestMapping("/loginmsg")
	@ResponseBody
	public Map<String, Object> loginmsg(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", CommonConstants.EX_USER_LOGIN_CODE);
		resultMap.put("msg", "please login.");
		return resultMap;
	}
}
