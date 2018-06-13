package com.github.wxiaoqi.security.admin.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxiaoqi.security.admin.biz.OrgBiz;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;

/**
 * 缓存 Created by Jason on 16/5/23.
 */
@IgnoreClientToken
@IgnoreUserToken
@Controller
@RequestMapping(value = "/cache")
public class CacheController {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private OrgBiz orgBiz;

	@RequestMapping(value = "/refresh/org", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> refreshOrg() {
		Map<String, Object> map = new HashMap<String, Object>();
		ListOperations<String, Org> list = redisTemplate.opsForList();
		list.getOperations().delete("org");
		List<Org> orgList = orgBiz.selectAll();
		for (Org org : orgList) {
			list.rightPush("orgList", org);
		}
		map.put("result", true);
		return map;
	}
}
