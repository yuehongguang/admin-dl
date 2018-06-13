package com.github.wxiaoqi.security.admin.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.admin.mapper.CourseMapper;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: OrgBiz <br/>
 * Function: 课程服务类 <br/>
 * date: 2017年10月19日 下午6:13:37 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class CourseBiz extends BaseBiz<CourseMapper, Course> {
	
	@Autowired
	private OrgBiz orgBiz;
	
	@Autowired
	private LessonBiz lessonBiz;

	public List<Course> selectCourseByOrgId(Integer userId) {
		List<Course> list = mapper.selectCourseByOrgId(userId);
		for (Course course : list) {
			course.setCourseLable(course.getCourseLables().split(","));
		}
		return list;
	}

	public Page<Course> findCoursesByOrgIdByPage(int limit, int page, Long orgId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orgId", orgId);
		Page<Course> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Course> list = selectByExample(example);
		for (Course c : list) {
			c.setCourseLable(c.getCourseLables().split(","));
		}
		return result;
	}

	public TableResultResponse<Course> selectByQuery(Query query) {
		Class<Course> clazz = (Class<Course>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		Example example = new Example(clazz);
		Example.Criteria criteria = example.createCriteria();
		for (Map.Entry<String, Object> entry : query.entrySet()) {
			if (StringUtils.isNotEmpty(entry.getValue().toString())) {
				criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
			}
		}
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Course> list = mapper.selectByExample(example);
		for(Course c : list){
			c.setOrgName(orgBiz.getOrgById(c.getOrgId()).getOrgName());
		}
		return new TableResultResponse<Course>(result.getTotal(), list);
	}
	
	public TableResultResponse<Course> selectByTeacherId(Query query,Long teacherId) {
		Class<Course> clazz = (Class<Course>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		Example example = new Example(clazz);
		Example.Criteria criteria = example.createCriteria();
		for (Map.Entry<String, Object> entry : query.entrySet()) {
			if (StringUtils.isNotEmpty(entry.getValue().toString())) {
				criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
			}
		}
		example.orderBy("id").desc();
		criteria.andEqualTo("teacherId", teacherId);
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Course> list = mapper.selectByExample(example);
		String orgName = "";
		for(Course c : list){
			if(StringUtils.isNotEmpty(orgName)){
				c.setOrgName(orgName);
			}else{
				orgName = orgBiz.getOrgById(c.getOrgId()).getOrgName();
			}
			c.setLessonJson(lessonBiz.findByCourseId(c.getId()));
		}
		return new TableResultResponse<Course>(result.getTotal(), list);
	}


	public TableResultResponse<Course> selectByOrgId(Query query,Long orgId) {
		Class<Course> clazz = (Class<Course>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		Example example = new Example(clazz);
		example.orderBy("id").desc();
		Example.Criteria criteria = example.createCriteria();
		for (Map.Entry<String, Object> entry : query.entrySet()) {
			if (StringUtils.isNotEmpty(entry.getValue().toString())) {
				criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
			}
		}
		criteria.andEqualTo("orgId", orgId);
		Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Course> list = mapper.selectByExample(example);
		Date date = new Date();
		for(Course c : list){
			if(c.getStartTime().after(date)){
				c.setCourseStatus(0);
			}else if(c.getEndTime().before(date)){
				c.setCourseStatus(2);
			}else{
				c.setCourseStatus(1);
			}
		}

		return new TableResultResponse<Course>(result.getTotal(), list);
	}

	public List<Course> selectByTeacherIdAndOrgId(Long teacherId,Long orgId){
		Course course = new Course();
		course.setTeacherId(teacherId);
		course.setOrgId(orgId);
		return selectList(course);
	}

	public Course insertCourse(Course course) {
		mapper.insertCourse(course);
		Course c = super.selectById(course.getId());
		return c;
	}
	public List<Course> findCourseByTeacherId(Long teacherId) {
		return mapper.findCourseByTeacherId(teacherId);
	}
	
	public List<Course> selectActiveCourseByOrgId(Long orgId,Integer courseType) {
		return mapper.selectActiveCourseByOrgId(orgId,courseType);
	}
	
	public List<Long> findOrgClassifyIdsByOrgId(Long orgId){
		return mapper.findOrgClassifyIdsByOrgId(orgId);
	}
}
