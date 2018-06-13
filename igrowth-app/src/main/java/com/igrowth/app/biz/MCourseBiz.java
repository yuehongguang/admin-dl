package com.igrowth.app.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.MCourse;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import com.igrowth.app.mapper.MCourseMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * ClassName: MCourseBiz <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2017年11月23日 上午11:17:24 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Service
public class MCourseBiz extends BaseBiz<MCourseMapper, MCourse> {

	public Page<MCourse> getMcourseByChildId(int limit, int page, Long childId, Integer courseStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("childId", childId);
		if (courseStatus == 0) {
			criteria.andGreaterThan("remainTimes", 0);
			criteria.andGreaterThan("endTime", new Date());
			example.orderBy("endTime");
		}else{
			criteria.andLessThanOrEqualTo("remainTimes", 0);
			Example.Criteria criteria2 = example.createCriteria();
			criteria2.andLessThan("endTime", new Date());
			criteria2.andEqualTo("childId", childId);
			example.or(criteria2);  
			example.orderBy("startTime");
		}
		
		Page<MCourse> result = PageHelper.startPage(query.getPage(), query.getLimit());
		selectByExample(example, page, result);
		return result;
	}
	
	public List<MCourse> findMcourseByCourseName(Long childId, String courseName){
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("childId", childId);
		criteria.andEqualTo("courseName", courseName);
		return selectByExample(example);	
	}

	public MCourse findByCourseIdAndChildId(Long courseId, Long childId) {
		return mapper.findByCourseIdAndChildId(courseId,childId);
	}
}
