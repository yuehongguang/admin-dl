package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.entity.Media;
import com.github.wxiaoqi.security.admin.mapper.MLessonMapper;
import com.github.wxiaoqi.security.admin.vo.MLessonVo;
import com.github.wxiaoqi.security.api.entity.*;
import com.github.wxiaoqi.security.api.vo.ChildVo;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MLessonBiz <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2018年1月2日 上午10:47:23 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Service
public class MLessonBiz extends BaseBiz<MLessonMapper,MLesson> {
	
	@Autowired
	private MediaAdminBiz mediaAdminBiz;
	
	@Autowired
	private LessonBiz lessonBiz;
	
	@Autowired
	private IgrowthMCourseBiz igrowthMCourseBiz;

	@Autowired
	private HeartBiz heartBiz;

	@Autowired
	private GrowthRecordBiz growthRecordBiz;

	public static List<String> audioList = new ArrayList<String>();
	
	static{
		audioList.add("MP3");
		audioList.add("WMA");
		audioList.add("MIDI");
		audioList.add("AAC");
		audioList.add("M4A");
	}

	public Map<String,Object> selectByLessonIdAndCommentStatus(Long lessonId, Integer commentStatus, Query query) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<ChildVo> list =  mapper.selectByLessonIdAndCommentStatus(query.getLimit(),query.getPage(),lessonId,commentStatus);
		Integer total = mapper.selectByLessonIdAndCommentStatusCount(lessonId, commentStatus);
		if(total==null){
			total = 0;
		}
		result.put("list",list);
		result.put("total",total);
		return result;
	}

	public Map<String,Object> selectByMlessions(int limit,int page,Long orgId, Integer lessonStatus, String childName){
		page = (page-1)*limit;
		Map<String,Object> result = new HashMap<String,Object>();
		List<MLessonVo> list = mapper.selectByOrgIdAndLessonStatus(limit,page,orgId,lessonStatus,childName);
		Integer total = mapper.selectByOrgIdAndLessonStatusCount(orgId,lessonStatus,childName);
		if(total==null){
			total = 0;
		}
		result.put("list",list);
		result.put("total",total);
		return result;
	}

	public void updateStatus(Long id,Integer lessonStatus){
		MLesson mLesson = selectById(id);
		mLesson.setLessonStatus(lessonStatus);
		mapper.updateByPrimaryKey(mLesson);
		if(lessonStatus==1){//签到
			MLesson m = selectById(id);
			mLesson.setLessonStatus(1);
			updateById(mLesson);
			MCourse mCourse = igrowthMCourseBiz.findByChildIdAndCourseId(m.getChildId(), m.getCourseId());
			heartBiz.saveHeart(Heart.LESSON_HEART_NUM, 1, mCourse.getOrgName(), m.getChildId());
			growthRecordBiz.createRecord(m.getChildId(),mLesson,mCourse.getCourseLables(),"0");
		}else if(lessonStatus==-1){//取消预约 课时数+1
			MCourse mCourse2 = igrowthMCourseBiz.findByChildIdAndCourseId(mLesson.getChildId(), mLesson.getCourseId());
			if(mCourse2!=null){
				mCourse2.setRemainTimes(mCourse2.getRemainTimes()+1);
				igrowthMCourseBiz.updateSelectiveById(mCourse2);
			}
		}
	}

	public void addFeedBack(MLesson mlesson, MLesson originM) {
		originM.setLessonBehavior(mlesson.getLessonBehavior());
		originM.setLessonImgs(mlesson.getLessonImgs());
		originM.setCommentStatus(1);
		originM.setLessonHomework(mlesson.getLessonHomework());
		originM.setLessonPoint(mlesson.getLessonPoint());
		if(StringUtils.isNotEmpty(mlesson.getLessonVideo())){
			Media media = mediaAdminBiz.selectByUrl(mlesson.getLessonVideo());
			String fileType = media.getMediaFileName().split("\\.")[1];
			if(audioList.contains(fileType.toUpperCase())){
				originM.setLessonAudio(mlesson.getLessonVideo());
			}else{
				originM.setLessonVideo(mlesson.getLessonVideo());
			}
		}
		updateById(originM);
	}

	public Map<String,Object> bulidMlesson(Course c, Child child) {
		Map<String,Object> result = new HashMap<String,Object>();
		if (0 == c.getCourseType()) {// 不可预约
			List<Lesson> lessonList = lessonBiz.findByCourseIdAndStatus(c.getId(), 0);
			if(lessonList.size()>0){
				MLesson mlesson = new MLesson();
				mlesson.setCourseId(c.getId());
				Integer studentNum = mapper.findSumGroupByChild(c.getId());
				if (studentNum >= c.getCourseMaxStudent()) {
					result.put("isSuccess",Boolean.FALSE);
					result.put("message","约课失败，当前课程已约满");
					return result;
				}
			}
			for (Lesson l : lessonList) {
				MLesson mlesson = new MLesson();
				mlesson.setAccountId(child.getParentId());
				mlesson.setChildId(child.getId());
				mlesson.setCommentStatus(0);
				mlesson.setCourseId(c.getId());
				mlesson.setCourseName(c.getCourseName());
				mlesson.setEndTime(l.getEndTime());
				mlesson.setLessonAudio("");
				mlesson.setLessonBehavior("");
				mlesson.setLessonHomework("");
				mlesson.setLessonId(l.getId());
				mlesson.setLessonName(l.getLessonName());
				mlesson.setLessonPoint("");
				mlesson.setLessonStatus(0);
				mlesson.setLessonVideo("");
				mlesson.setNote("");
				mlesson.setOrgId(c.getOrgId());
				mlesson.setOrgName(c.getOrgName());
				mlesson.setStartTime(l.getStartTime());
				mlesson.setTeacherId(l.getTeacherId());
				mlesson.setTeacherName(l.getTeacherName());
				insertSelective(mlesson);
			}
		}
		result.put("isSuccess",Boolean.TRUE);
		return result;
	}

	public List<MLesson> selectEndMlesson() {
		return mapper.selectEndMlesson();
	}
	
	public List<MLesson> selectEndCommentMlessonByLessonId(Long lessonId,Integer commentStatus){
		return mapper.selectEndCommentMlessonByLessonId(lessonId,commentStatus);
	}

	public MLesson findByChildIdAndLessonId(Long childId, Long lessonId) {
		MLesson mLesson = new MLesson();
		mLesson.setLessonId(lessonId);
		mLesson.setChildId(childId);
		return mapper.selectOne(mLesson);
	}
	
	public Integer selectEndCommentMlessonCountByLessonId(Long lessonId,Integer commentStatus){
		return mapper.selectEndCommentMlessonCountByLessonId(lessonId, commentStatus);
	}
	
}