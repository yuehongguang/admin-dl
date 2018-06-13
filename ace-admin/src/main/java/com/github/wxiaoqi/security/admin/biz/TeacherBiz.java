package com.github.wxiaoqi.security.admin.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.admin.mapper.TeacherMapper;
import com.github.wxiaoqi.security.api.entity.Teacher;
import com.github.wxiaoqi.security.api.vo.TeacherVo;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 
 * ClassName: OrgBiz <br/>
 * Function: 教师类 <br/>
 * date: 2017年10月19日 下午6:13:37 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class TeacherBiz extends BaseBiz<TeacherMapper, Teacher> {
	
	public Page<Teacher> findTeacherByPage(int limit, int page, String sortColumn, String orderBy) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		if ("asc".equals(orderBy)) {
			example.orderBy(sortColumn).asc();
		} else {
			example.orderBy(sortColumn).desc();
		}
		Page<Teacher> result = PageHelper.startPage(query.getPage(), query.getLimit());
		selectByExample(example);
		return result;
	}

	public Map<String,Object> findTeacherByOrgIdAndPage(long orgId, int limit, int page,String namePhone) {
		String teacherName = null;
		String phone = null;
		if(StringUtils.isNotEmpty(namePhone)){
			if(Pattern.compile("[0-9]*").matcher(namePhone).matches()){
				phone = namePhone;
			}else{
				teacherName = namePhone;
			}
		}
		List<TeacherVo> teacherList = mapper.findTeacherByOrgIdAndPage(orgId, limit, page-1,teacherName,phone);
		Long total = mapper.findTeacherCountByOrgId(orgId,teacherName,phone);
		Map<String,Object> resultPage = new HashMap<String,Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		resultPage.put("total",total);
	    resultPage.put("data",teacherList);
		return resultPage;
	}

	public List<Teacher> findTeacherByOrgId(long orgId) {
		List<Teacher> teacherList = mapper.findTeacherByOrgId(orgId);
		return teacherList;
	}

	public Teacher findTeacherByPhone(String phone) {
		return mapper.findTeacherByPhone(phone);
	}

	public Teacher findTeacherById(Long teacherId){
			return mapper.findTeacherById(teacherId);
	}
}
