package com.igrowth.app.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.Fav;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.CourseBiz;
import com.igrowth.app.biz.FavBiz;

/**
 * ClassName: OrgController <br/>  
 * Function: 有关机构的api类 <br/>  
 * date: 2017年10月19日 下午6:06:06 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@RequestMapping("course")
public class CourseController extends BaseController {
	
	@Autowired
	private CourseBiz courseBiz;
	
	@Autowired
	private FavBiz favBiz;

	
	/**
	 * 查询机构查询课程
	 * getCourse: <br/>  
	 * @author dingshuyan  
	 * @param id
	 * @return  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@RequestMapping(value = "/org/{orgId}", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Course> getOrgCourse(@RequestParam(defaultValue = "10") int limit,
							   @RequestParam(defaultValue = "1") int page,
							   @PathVariable Long orgId) {
		Page<Course> result =  courseBiz.findCoursesByOrgIdByPage(limit,page,orgId);
		return new TableResultResponse<Course>(result.getTotal(), result.getResult());
	}
	
	/**
	 * 根据Id查询课程
	 * getCourse: <br/>  
	 * @author dingshuyan  
	 * @param id
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "course/{id}", method = RequestMethod.GET)
	@ResponseBody
	@IgnoreClientToken
	public Course getCourse(@PathVariable Long id) {
		Course course =  courseBiz.getCourseById(id);
		if(getCurrentAccountId()==null){
			course.setFav(-1);
		}else{
			Fav fav = favBiz.findFavByAccountIdAndOrgId(getCurrentAccountId(),course.getId(),2);
			if(fav!=null){
				course.setFav(1);
			}else{
				course.setFav(0);
			}
		}
		return course;
	}
	
	/**
	 * 查询所有课程标签
	 * getCourse: <br/>  
	 * @author dingshuyan  
	 * @param id
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "courseLables", method = RequestMethod.GET)
	@ResponseBody
	@IgnoreClientToken
	public Map<String, Object> getcourseLables() {
		Map<String, Object> reMap = new HashMap<>();
		reMap.put("result", courseBiz.getAllCourseLables());
		return reMap;
	}
}
