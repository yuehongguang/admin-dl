package com.igrowth.app.biz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.wxiaoqi.security.api.entity.Lesson;
import com.github.wxiaoqi.security.api.entity.MCourse;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.igrowth.app.mapper.LessonMapper;
import com.igrowth.app.vo.LessonVO;

/**
 * ClassName: MLessonBiz <br/>
 * 我的课程 date: 2017年11月20日 下午7:30:20 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class LessonBiz extends BaseBiz<LessonMapper, Lesson> {
	
	@Autowired
	private MLessonBiz mLessonBiz;
	
	@Autowired
	private MCourseBiz mCourseBiz;

	public List<Lesson> findByCourseIdAndDate(Long courseId,Date date) {
		return mapper.findMLessonByCourseIdAndDate(courseId, date);
	}
	
	public Lesson findById(Long id){
		return selectById(id);
	}
	
	public List<Lesson> findByCourseId(Long courseId) {
		return mapper.findByCourseId(courseId);
	}

	public List<Lesson> findByCourseIdAndStatus(Long courseId,Integer lessonStatus) {
		return mapper.findByCourseIdAndStatus(courseId,lessonStatus);
	}

	@Transactional
	public void sublesson(Long lessonId, Long currentAccountId, Long currentChildId, MLesson mLesson) {
		if (mLesson != null) {
			mLesson.setLessonStatus(3);
			mLessonBiz.updateById(mLesson);
		} else {
			Lesson lesson = findById(lessonId);
			mLesson = new MLesson();
			mLesson.setAccountId(currentAccountId);
			mLesson.setChildId(currentChildId);
			mLesson.setCourseId(lesson.getCourseId());
			mLesson.setCourseName(lesson.getCourseName());
			mLesson.setEndTime(lesson.getEndTime());
			mLesson.setLessonId(lesson.getId());
			mLesson.setLessonName(lesson.getLessonName());
			mLesson.setLessonStatus(3);
			mLesson.setOrgId(lesson.getOrgId());
			mLesson.setOrgName(lesson.getOrgName());
			mLesson.setStartTime(lesson.getStartTime());
			mLesson.setTeacherId(lesson.getTeacherId());
			mLesson.setTeacherName(lesson.getTeacherName());
			mLessonBiz.insertSelective(mLesson);
		}
		MCourse mCourse = mCourseBiz.findByCourseIdAndChildId(mLesson.getCourseId(), currentChildId);
		if (mCourse != null) {
			mCourse.setRemainTimes(mCourse.getRemainTimes() - 1);
			mCourseBiz.updateSelectiveById(mCourse);
		}
	}

	public void nsublesson(MLesson mLesson, Long currentChildId) {
		mLesson.setLessonStatus(-1);
		mLessonBiz.updateById(mLesson);
		MCourse mCourse = mCourseBiz.findByCourseIdAndChildId(mLesson.getCourseId(), currentChildId);
		if (mCourse != null) {
			mCourse.setRemainTimes(mCourse.getRemainTimes() + 1);
		}
		mCourseBiz.updateSelectiveById(mCourse);
	}
	
	public List<LessonVO> findLessonByDateAndCourseId(Long courseId,String date){
		return mapper.findLessonByDateAndCourseId(courseId, date);
	}
	
	public LessonVO findLessonByLessonId(Long lessonId){
		return mapper.findLessonByLessonId(lessonId);
	}
}
