package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.CourseBiz;
import com.github.wxiaoqi.security.admin.biz.IgrowthCourseCardBiz;
import com.github.wxiaoqi.security.admin.biz.OrgBiz;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.IgrowthCourseCard;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-12 8:49
 */
@Controller
@RequestMapping("course")
@Api("机构模块")
public class CourseController extends BaseController<CourseBiz, Course> {
	@Autowired
	private CourseBiz courseBiz;
	
	@Autowired
	private OrgBiz orgBiz;
	
	@Autowired
	private IgrowthCourseCardBiz igrowthCourseCardBiz;

	@Override
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Course> list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		return courseBiz.selectByQuery(query);
	}

	@GetMapping(value="courses/list")
	@ResponseBody
	public List<Course> findCourse(){
		return courseBiz.selectListAll();
	}

	@GetMapping(value="courses/{id}")
	@ResponseBody
	public Course findCourse(@PathVariable Long id){
		return courseBiz.selectById(id);
	}

	@GetMapping(value="courses/org")
	@ResponseBody
	public List<Course> findCourseByOrg(@RequestParam(defaultValue = "-1") int courseType){

		Org org = new Org();
        org.setBaseUserId(Integer.parseInt(getCurrentUserId()));
        org = orgBiz.selectOne(org);
		if(org!=null){
			return courseBiz.selectActiveCourseByOrgId(org.getId(),courseType);
		}else{
			return new ArrayList<>();
		}
	}

	@GetMapping(value="courseCard/org")
	@ResponseBody
	public List<IgrowthCourseCard> findCourseCardByOrg(){
		Org org = new Org();
		org.setBaseUserId(Integer.parseInt(getCurrentUserId()));
		org = orgBiz.selectOne(org);
		if(org!=null){
			return igrowthCourseCardBiz.selectActiveCourseCardByOrgId(org.getId());
		}else{
			return new ArrayList<>();
		}
	}

}
