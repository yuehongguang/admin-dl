package com.github.wxiaoqi.security.admin.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.admin.mapper.FeedbackMapper;
import com.github.wxiaoqi.security.api.entity.Feedback;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-12-19 15:39:18
 */
@Service
public class FeedbackBiz extends BaseBiz<FeedbackMapper,Feedback> {
	
	public Map<String,Object> findByOrgId(Query query,Long orgId,String name){
		List<Feedback> feedBackList = mapper.findByOrgIdAndPage(orgId, query.getLimit(), (query.getPage()-1)*query.getLimit(),name);
		Long total = mapper.findCountByOrgId(orgId,name);
		Map<String,Object> resultPage = new HashMap<String,Object>();
		resultPage.put("total",total);
	    resultPage.put("data",feedBackList);
	    return resultPage;
	}
}