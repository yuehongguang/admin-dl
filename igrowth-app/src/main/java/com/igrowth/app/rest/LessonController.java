package com.igrowth.app.rest;

import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.Lesson;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.common.msg.AppResponse;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.igrowth.app.biz.CourseBiz;
import com.igrowth.app.biz.LessonBiz;
import com.igrowth.app.biz.MLessonBiz;
import com.igrowth.app.vo.LessonVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * ClassName: LessonController <br/>  
 * 课表接口
 * date: 2017年11月27日 下午3:12:25 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@RequestMapping("lesson")
public class LessonController extends BaseController {
	
	@Autowired
	private LessonBiz lessonBiz;
	
	@Autowired
	private MLessonBiz mLessonBiz;
	
	@Autowired
	private CourseBiz courseBiz;
 
	/**
	 * 选课
	 * @author dingshuyan  
	 * @param messageType
	 * @return  
	 * @throws ParseException 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/lessons/{courseId}", method = RequestMethod.GET)
	@ResponseBody
	public AppResponse<Object> page(@PathVariable Long courseId,
									@RequestParam(defaultValue = "") String dateStr) throws Exception {
		Date date = null;
		if(StringUtils.isEmpty(dateStr)){
			date = new Date();
			dateStr = DateUtil.formatDateByPattern(date, DateUtil.DATE_FROMAT_PATTERN_ONE);
		}else{
			date = DateUtil.formateStringToDate(dateStr, DateUtil.DATE_FROMAT_PATTERN_ONE);
		}
		List<LessonVO> list = lessonBiz.findLessonByDateAndCourseId(courseId,dateStr);
		List<Long> mIdList = mLessonBiz.findMLessonIdByDateAndCourseId(courseId, getCurrentChildId(), date);
		Course course = courseBiz.selectById(courseId);	
		for (LessonVO lv : list) {
			if (mIdList.contains(lv.getId())) {
				lv.setLessonSubStatus(1);
			} else {
				if (lv.getSubnum() < course.getCourseMaxStudent()) {
					lv.setLessonSubStatus(0);
				} else {
					lv.setLessonSubStatus(2);
				}
			}
		}
		return new AppResponse<Object>(200, "success", list);
	} 
	
	/**
	 * 约课
	 * @author dingshuyan  
	 * @param messageType
	 * @return  
	 * @throws ParseException 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/sublesson/{lessonId}", method = RequestMethod.PUT)
	@ResponseBody
	public AppResponse<Object> sublesson(@PathVariable Long lessonId){
		try{
			MLesson mlesson = mLessonBiz.findByChildIdAndLessonId(getCurrentChildId(),lessonId);
			if(mlesson!=null&&mlesson.getLessonStatus()!=-1){
				return new AppResponse<Object>(200, "当前课程已经预约，请不要重复预约.", false);
			}
			Lesson lesson = lessonBiz.selectById(lessonId);
			if(lesson.getEndTime().before(new Date())){
				return new  AppResponse<Object>(200, "当前课程已结束，暂不能预约.", false);
			}
			if(lesson.getStartTime().before(new Date())){
				return new  AppResponse<Object>(200, "当前课程已开课，暂不能预约.", false);
			}
			Course course = courseBiz.selectById(lesson.getCourseId());
			LessonVO lv = lessonBiz.findLessonByLessonId(lessonId);
			if (lv.getSubnum() >= course.getCourseMaxStudent()) {
				return new AppResponse<Object>(200, "约课失败，当前课程已约满.", false);
			}
			lessonBiz.sublesson(lessonId, getCurrentAccountId(), getCurrentChildId(), mlesson);
			return new AppResponse<Object>(200, "约课成功.", true);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return new AppResponse<Object>(200, "fail", false);
		}
	} 
	
	
	/**
	 * 取消约课
	 * @author dingshuyan  
	 * @param messageType
	 * @return  
	 * @throws ParseException 
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/nsublesson/{lessonId}", method = RequestMethod.PUT)
	@ResponseBody
	public AppResponse<Object> nsublesson(@PathVariable Long lessonId){
		try{
			MLesson mlesson = mLessonBiz.findByChildIdAndLessonId(getCurrentChildId(),lessonId);
			if(mlesson.getLessonStatus()!=3){
				return new AppResponse<Object>(200, "当前课程无法取消.", false);
			}
			lessonBiz.nsublesson(mlesson,getCurrentChildId());
			return new AppResponse<Object>(200, "课程取消预约成功.", true);
		}catch(Exception e){
			return new AppResponse<Object>(200, "课程取消预约失败.", false);
		}
	} 
}
