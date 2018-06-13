package com.igrowth.app.biz;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.Feedback;
import com.github.wxiaoqi.security.api.entity.Heart;
import com.github.wxiaoqi.security.api.entity.Lesson;
import com.github.wxiaoqi.security.api.entity.MCourse;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.Query;
import com.igrowth.app.mapper.MLessonMapper;
import com.igrowth.app.vo.MCourseVO;

import tk.mybatis.mapper.entity.Example;

/**
 * ClassName: MLessonBiz <br/>
 * 我的课程 date: 2017年11月20日 下午7:30:20 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class MLessonBiz extends BaseBiz<MLessonMapper, MLesson> {

	@Autowired
	private OrgBiz orgBiz;
	
	@Autowired
	private LessonBiz lessonBiz;
	
	@Autowired
	private MCourseBiz mCourseBiz;
	
	@Autowired
	private FeedbackBiz feedbackBiz;
	
	@Autowired
	private HeartBiz heartBiz;

	@Autowired
	private GrowthRecordBiz growthRecordBiz;

	/**
	 * getByAccountId:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author dingshuyan
	 * @param limit
	 * @param page
	 * @param accountId
	 * @return
	 * @since JDK 1.8
	 */
	public Page<MLesson> getByAccountId(int limit, int page, int childId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("childId", childId);
		Page<MLesson> result = PageHelper.startPage(query.getPage(), query.getLimit());
		selectByExample(example,page,result);
		return result;
	}

	public List<MCourseVO> findMLessonByDate(Long childId, Map<String,Date> dateMap, Integer courseStatus) {
		List<Integer> courseStatuss = new ArrayList<Integer>();
		if(courseStatus==-1){
			courseStatuss.add(0);
			courseStatuss.add(1);
			courseStatuss.add(2);
			courseStatuss.add(3);
			courseStatuss.add(4);
		}else{
			courseStatuss.add(courseStatus);
		}
		List<MLesson> list = mapper.findMLessonByDate(childId, dateMap.get(DateUtil.DATE_NAME_ONE[0]), dateMap.get(DateUtil.DATE_NAME_ONE[6]), courseStatuss);
		Map<String, MCourseVO> map = new HashMap<String, MCourseVO>();
		List<MCourseVO> mvoList = new ArrayList<>();
		for (MLesson m : list) {
			String dateStr = DateUtil.formateDateToString(m.getStartTime(), DateUtil.DATE_FROMAT_PATTERN_ONE);
			String weekStr = DateUtil.dateToWeek(m.getStartTime());
			MCourseVO mCourseVO = null;
			if (map.keySet().contains(dateStr)) {
				mCourseVO = map.get(dateStr);
				mCourseVO.getList().add(m);
			} else {
				mCourseVO = new MCourseVO();
				mCourseVO.setDate(dateStr);
				mCourseVO.setWeek(weekStr);
				List<MLesson> mList = new ArrayList<MLesson>();
				mList.add(m);
				mCourseVO.setList(mList);
				map.put(dateStr, mCourseVO);
				mvoList.add(mCourseVO);
			}
		}
		
		for (String week : dateMap.keySet()) {
			String date = DateUtil.formateDateToString(dateMap.get(week), DateUtil.DATE_FROMAT_PATTERN_ONE);
			if(!map.containsKey(date)){
				MCourseVO mCourseVO = new MCourseVO();
				mCourseVO.setDate(date);
				mCourseVO.setWeek(week);
				mCourseVO.setList(new ArrayList<MLesson>());
				mvoList.add(mCourseVO);
			}
		}
		Collections.sort(mvoList);
		return mvoList;
	}

	public List<MLesson> findMLessonByParam(Integer orgId,Long childId, Date date){
		return mapper.findMLessonByParam(orgId,childId,date);
	}

	public MLesson findById(Long id) {
		MLesson mLesson = selectById(id);
		Org org = orgBiz.selectById(mLesson.getOrgId());
		if(org!=null){
			mLesson.setOrgName(org.getOrgName());
			mLesson.setCourseLables(orgBiz.getOrgCourseLablesReids(org));
			mLesson.setOrgAddress(org.getOrgAddress());
			mLesson.setOrgTel(org.getOrgTel());
		}
		Lesson lesson = lessonBiz.selectById(mLesson.getLessonId());
		if(lesson!=null){
			mLesson.setTeacherId(lesson.getTeacherId());
			mLesson.setTeacherName(lesson.getTeacherName());
		}
		Feedback feedBack = feedbackBiz.findByMlessonIdAndChildId(id,mLesson.getChildId());
		if(feedBack!=null){
			mLesson.setFeedBackId(String.valueOf(feedBack.getId()));
		}else{
			mLesson.setFeedBackId("");
		}
		return mLesson;
	}
	
	public void lelesson(Long mLessonId, Long currentAccountId, Long currentChildId) {
		MLesson mLesson = findById(mLessonId);
		mLesson.setLessonStatus(2);
		updateById(mLesson);
	}
	
	public MLesson sign(Long mLessonId, Long currentAccountId, Long currentChildId) {
		MLesson mLesson = findById(mLessonId);
		mLesson.setLessonStatus(1);
		updateById(mLesson);
		MCourse mCourse = mCourseBiz.findByCourseIdAndChildId(mLesson.getCourseId(),currentChildId);
		heartBiz.saveHeart(Heart.LESSON_HEART_NUM, 1, mCourse.getOrgName(), currentChildId);
		growthRecordBiz.createRecord(currentChildId,mLesson,mCourse.getCourseLables(),"0");
		return mLesson;
	}

	public MLesson selflesson(Long accountId, Long childId, String courseName, String lable, String date, int tlong, String note) throws ParseException {
		List<MCourse> list = mCourseBiz.findMcourseByCourseName(childId,courseName);
		if(list==null||list.isEmpty()){
			MCourse mCourse = new MCourse();
			mCourse.setAllTimes(0);
			mCourse.setChildId(childId);
			mCourse.setCourseName(courseName);
			mCourse.setCourseStatus(2);
			mCourse.setRemainTimes(0);
			mCourse.setCourseLables(lable);
			mCourse.setStartTime(new Date());
			mCourse.setEndTime(new Date());
			mCourseBiz.insert(mCourse);
		}
		MLesson mLesson = new MLesson();
		Date sDate = DateUtil.formateStringToDate(date,DateUtil.DATE_FROMAT_PATTERN_ONE);
		Calendar c  = Calendar.getInstance();
		c.setTime(sDate);
		c.add(Calendar.HOUR, tlong);
		Date eDate = c.getTime();
		mLesson.setAccountId(accountId);
		mLesson.setChildId(childId);
		mLesson.setCourseName(courseName);
		mLesson.setLessonName(courseName);
		mLesson.setLessonStatus(4);
		mLesson.setNote(note);
		mLesson.setStartTime(sDate);
		mLesson.setEndTime(eDate);
		mapper.insertMLessonAndGetId(mLesson);
		mLesson.setId(mapper.findMaxMlessonId());
		return mLesson;
	}
	
	public void updateMlesson(Long mlessonId, String courseName, String lable, String date, int tlong, String note) throws ParseException {
		MLesson mLesson = findById(mlessonId);
		Date sDate = DateUtil.formateStringToDate(date,DateUtil.DATE_FROMAT_PATTERN_ONE);
		Calendar c  = Calendar.getInstance();
		c.setTime(sDate);
		c.add(Calendar.HOUR, tlong);
		Date eDate = c.getTime();
		mLesson.setCourseName(courseName);
		mLesson.setLessonStatus(4);
		mLesson.setNote(note);
		mLesson.setStartTime(sDate);
		mLesson.setEndTime(eDate);
		mapper.updateByPrimaryKey(mLesson);
	}
	
	public List<Long> findMLessonIdByDateAndCourseId(Long courseId, Long childId, Date date) {
		return mapper.findMLessonIdByDateAndCourseId(courseId, childId, date);
	}

	
	public MLesson findByChildIdAndLessonId(Long childId, Long lessonId) {
		MLesson mLesson = new MLesson();
		mLesson.setLessonId(lessonId);
		mLesson.setChildId(childId);
		return mapper.selectOne(mLesson);
	}
}
