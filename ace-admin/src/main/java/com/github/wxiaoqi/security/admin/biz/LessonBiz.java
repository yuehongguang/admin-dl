package com.github.wxiaoqi.security.admin.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.admin.mapper.LessonMapper;
import com.github.wxiaoqi.security.admin.vo.LessonVO;
import com.github.wxiaoqi.security.api.entity.Lesson;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.api.vo.OrgClassifyVo;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;

/**
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-12-08 17:05:56
 */
@Service
public class LessonBiz extends BaseBiz<LessonMapper,Lesson> {
	
	@Autowired
	private MLessonBiz mLessonBiz;
	
	public List<Lesson> findByCourseIdAndStatus(Long courseId,Integer lessonStatus) {
		return mapper.findByCourseIdAndStatus(courseId,lessonStatus);
	}
	public List<OrgClassifyVo> findByCourseId(Long courseId) {
		List<Lesson> list = mapper.findByCourseId(courseId);
		List<OrgClassifyVo> rList = new ArrayList<OrgClassifyVo>();
		for(Lesson l : list){
			OrgClassifyVo orgClassifyVo = new OrgClassifyVo(l.getId(),bulidDate(l.getStartTime(), l.getEndTime()),null);
			Integer c0 = mLessonBiz.selectEndCommentMlessonCountByLessonId(l.getId(), 0);
			Integer c1 = mLessonBiz.selectEndCommentMlessonCountByLessonId(l.getId(), 1);
			if(c0>=1){
				orgClassifyVo.setDisabled(false);
			}else{
				orgClassifyVo.setDisabled(true);
			}
			if(c1>=1){
				orgClassifyVo.setCdisabled(false);
			}else{
				orgClassifyVo.setCdisabled(true);
			}
			rList.add(orgClassifyVo);
		}
		return rList;
	}
	
	private String bulidDate(Date startTime, Date endTime){
		String d = DateUtil.formatDateByPattern(startTime, DateUtil.DATE_FROMAT_PATTERN_ONE);
		String d1 = DateUtil.formatDateByPattern(startTime, DateUtil.DATE_FROMAT_PATTERN_FIVE).split(" ")[1];
		String d2 = DateUtil.formatDateByPattern(endTime, DateUtil.DATE_FROMAT_PATTERN_FIVE).split(" ")[1];
		return d + " " + d1 + "-" + d2;
	}

	public LessonVO findLessonByLessonId(Long lessonId){
		return mapper.findLessonByLessonId(lessonId);
	}

	public Lesson findById(Long id){
		return selectById(id);
	}

	public void sublesson(Long lessonId, Long currentAccountId, Long currentChildId, MLesson mLesson) {
		if(mLesson!=null){
			mLesson.setLessonStatus(3);
			mLessonBiz.updateById(mLesson);
		}else{
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

	}
}