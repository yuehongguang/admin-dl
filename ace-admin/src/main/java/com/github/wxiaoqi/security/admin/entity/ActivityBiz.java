package com.github.wxiaoqi.security.admin.entity;

import com.github.wxiaoqi.security.admin.mapper.ActivityMapper;
import com.github.wxiaoqi.security.api.entity.Activity;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-12-20 14:18:45
 */
@Service
public class ActivityBiz extends BaseBiz<ActivityMapper,Activity> {

	public Map<String, Object> findByOrgId(Query query, Long orgId,String activityShort) {
		List<Activity> feedBackList = mapper.findByOrgIdAndPage(orgId, query.getLimit(), query.getPage()-1,activityShort);
		Long total = mapper.findCountByOrgId(orgId);
		Map<String,Object> resultPage = new HashMap<String,Object>();
		resultPage.put("total",total);
	    resultPage.put("data",feedBackList);
	    return resultPage;
	}
}