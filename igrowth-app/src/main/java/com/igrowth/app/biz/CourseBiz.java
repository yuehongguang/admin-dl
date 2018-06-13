package com.igrowth.app.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.Lesson;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import com.igrowth.app.mapper.CourseMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * ClassName: OrgBiz <br/>
 * Function: 课程服务类 <br/>
 * date: 2017年10月19日 下午6:13:37 <br/>
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

	public List<Course> selectCourseByOrgId(Long orgId) {
		List<Course> list = mapper.selectCourseByOrgId(orgId);
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
		List<Course> list = selectByExample(example,page,result);
		for (Course c : list) {
			c.setCourseLable(c.getCourseLables().split(","));
		}
		return result;
	}

	public Course getCourseById(Long courseId) {
		Course course = selectById(courseId);
		Org org = orgBiz.getOrgById(course.getOrgId());
		List<Lesson> lessonList = lessonBiz.findByCourseId(courseId);
		String[] lessons = null; 
		if(!lessonList.isEmpty()){
			lessons = new String [lessonList.size()];
			for(int i=0;i<lessonList.size();i++){
				lessons[i]=lessonList.get(i).getCourseName();
			}
		}
		course.setOrgTel(org.getOrgTel());
		course.setLessons(lessons);
		course.setOrgName(org.getOrgName());
		return course;
	}
	
	public List<String> getAllCourseLables() {
		 return mapper.getAllCourseLables();
	}

	public List<String> findCourseLablesByOrgId(Long orgId) {
		return mapper.findCourseLablesByOrgId(orgId);
	}
	
	public List<Long> findOrgClassifyIdsByOrgId(Long orgId) {
		return mapper.findOrgClassifyIdsByOrgId(orgId);
	}
}
