/**  
 * Project Name:ace-auth-server  
 * File Name:IdentifyCodeUtil.java  
 * Package Name:com.github.wxiaoqi.security.auth.util.user  
 * Date:2017年11月21日下午7:24:04  
 * Copyright (c) 2017, suveen@163.com All Rights Reserved.  
 *  
*/

package com.igrowth.app.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.common.constant.CommonConstants;

/**
 * 校验验证码工具类 Date: 2017年11月21日 下午7:24:04 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 * @see
 */
@Service
public class IdentifyCodeBiz {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public boolean identifyCode(String phone, String identifyCode) {
		String randomkey = String.format(CommonConstants.RANDOMKEY, phone);
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		if (redisTemplate.hasKey(randomkey) && identifyCode.equals(ops.get(randomkey))) {
			return true;
		}
		return false;
	}
}
