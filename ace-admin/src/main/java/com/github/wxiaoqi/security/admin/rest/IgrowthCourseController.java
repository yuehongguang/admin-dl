package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.*;
import com.github.wxiaoqi.security.admin.entity.OrgClassifyBiz;
import com.github.wxiaoqi.security.api.entity.*;
import com.github.wxiaoqi.security.api.vo.ChildVo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("igrowthCourse")
public class IgrowthCourseController extends BaseController<CourseBiz, Course> {
	
	@Autowired
	private CourseBiz courseBiz;
	
	@Autowired
	private TeacherBiz teacherBiz;
	
	@Autowired
	private MLessonBiz mLessonBiz;

	@Autowired
	private OrgBiz orgBiz;

	@Autowired
	private LessonBiz lessonBiz;
	
	@Autowired
	private IgrowthCourseCardBiz igcardBiz;

	@Autowired
	private OrgClassifyBiz orgClassifyBiz;
	
	@Autowired
	private ChildBiz childBiz;
	
	@Autowired
	private IgrowthMCourseBiz igrowthMCourseBiz;

	@Override
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Course> list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		Teacher t = new Teacher();
		t.setUserId(Long.valueOf(getCurrentUserId()));
		Teacher teacher = teacherBiz.selectOne(t);
		return courseBiz.selectByTeacherId(query,Long.valueOf(teacher.getId()));
	}

	/**
	 * 课程表管理首页
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/orgPage", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Course> listToOrg(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		Org org = new Org();
		org.setBaseUserId(Integer.valueOf(getCurrentUserId()));
		org = orgBiz.selectOne(org);
		return courseBiz.selectByOrgId(query,org.getId());
	}

	/**
	 * 课程表编辑回显
	 * @param courseId
	 * @return
	 */
	@GetMapping(value="/lesson/{courseId}")
	public Map<String,Object> getCourseAndLessonById(@PathVariable Long courseId){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Boolean isSuccess = Boolean.FALSE;
		try{
			Course course = courseBiz.selectById(courseId);
			Long orgClassifyId = course.getOrgClassifyId();
			OrgClassify orgClassify = orgClassifyBiz.findById(orgClassifyId);
			List<Long> orgClassifyIds = new ArrayList<Long>();
			if (orgClassify.getPid() != null) {
				orgClassifyIds.add(orgClassify.getPid());
				List<OrgClassify> list = orgClassifyBiz.getByPid(orgClassifyId);
				for (OrgClassify oc : list) {
					orgClassifyIds.add(oc.getId());
				}
			} else {
				orgClassifyIds.add(orgClassify.getId());
			}
			course.setOrgClassifyIds((Long[]) orgClassifyIds.toArray(new Long[orgClassifyIds.size()]));
			resultMap.put("course",course);
			Lesson lesson = new Lesson();
			lesson.setCourseId(course.getId());
			Boolean canUpdate = igrowthMCourseBiz.findByCourseId(courseId).isEmpty();
			List<Lesson> lessonList = lessonBiz.selectList(lesson);
			for(Lesson l: lessonList){
				l.setCanUpdate(canUpdate);
			}
			resultMap.put("lessons",lessonList);
			isSuccess = Boolean.TRUE;
		}catch(Exception e){
			e.printStackTrace();
		}
		resultMap.put("isSuccess",isSuccess);
		return resultMap;
	}

    @RequestMapping(value = "/feedback",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<MLesson> addFeedBack(@RequestBody MLesson mlesson){
    	MLesson originM = mLessonBiz.selectById(mlesson.getId());
    	mLessonBiz.addFeedBack(mlesson,originM);
        return new ObjectRestResponse<MLesson>().rel(true);
    }

    @RequestMapping(value = "/chilids/{lessonId}", method = RequestMethod.GET)
    @ResponseBody
    public List<ChildVo> chilids(@PathVariable Long lessonId) {
    	Integer commentStatus = 0;
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("limit", 100);
    	params.put("page", 0);
    	Query query = new Query(params);
    	Map<String, Object> childVos = mLessonBiz.selectByLessonIdAndCommentStatus(lessonId,commentStatus,query);
        return (List<ChildVo>) childVos.get("list");
    }
    
	@RequestMapping(value = "/mlssonpage/{lessonId}", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<ChildVo> mlssonpage(@PathVariable Long lessonId,@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		query.setPage(query.getPage()-1);
		Integer commentStatus = 1;
		Map<String, Object> result = mLessonBiz.selectByLessonIdAndCommentStatus(lessonId,commentStatus,query);
		int total = (Integer)result.get("total");
		List<ChildVo> list = (List)result.get("list");
		return new TableResultResponse<ChildVo>(total, list);
	}
	
    @RequestMapping(value = "/feedback/{mlessonId}",method = RequestMethod.GET)
    @ResponseBody
    public MLesson getFeedBack(@PathVariable Long mlessonId){
    	MLesson originM = mLessonBiz.selectById(mlessonId);
    	Child child = childBiz.selectById(originM.getChildId());
    	originM.setChildName(child.getChildName());
        return originM;
    }

	@RequestMapping(value = "/orgcourses/{courseId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Course> orgCourseList(@PathVariable Long courseId) {
		String userId = getCurrentUserId();
		Org org = new Org();
		org.setBaseUserId(Integer.parseInt(userId));
		org = orgBiz.selectOne(org);
		return courseBiz.selectCourseByOrgId(org.getId().intValue());
	}

	/**
	 * 生成课程和课表
	 * @return
	 */
	@PostMapping(value="/orgcourses/lessons/save")
	@Transactional
	public Map<String,Object> orgCourseLessonSave(@RequestBody Map<String,Object> params){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Boolean isSuccess = Boolean.FALSE;
		String message = null;
		String userId = getCurrentUserId();
		Org org = new Org();
		org.setBaseUserId(Integer.valueOf(userId));
		org = orgBiz.selectOne(org);
		if(params!=null) {
			Integer courseId = null;
			if(params.get("id")!=null){
				courseId = Integer.valueOf(params.get("id").toString());
			}
			Integer courseType = Integer.valueOf(params.get("courseType").toString());
			String courseName = (String) params.get("courseName");
			Integer courseMaxStudent = Integer.valueOf(params.get("courseMaxStudent").toString());
			String courseContent = (String) params.get("courseContent");
			Long cardId = Long.valueOf(params.get("cardId").toString());
			String cardName = (String)params.get("cardName");
			Integer courseNum = Integer.valueOf(params.get("courseNum").toString());
			//String courseLabels = (String)params.get("courseLabels");
			Integer teacherId=null;
			if(params.get("teacherId")!=null){
				teacherId = Integer.valueOf(params.get("teacherId").toString());
			}
			String teacherName = (String)params.get("teacherName");
			String startTimeString = (String)params.get("startTime");
			String endTimeString =(String)params.get("endTime");
			Integer orgClassifyId =Integer.valueOf(params.get("orgClassifyId").toString());
			List<Map<String,Object>> lessons = (List)params.get("lessonList");

			//创建课程
			Course course = new Course();
			course.setCourseMaxStudent(courseMaxStudent);
			course.setCourseType(courseType);
			course.setCourseName(courseName);
			course.setCourseContent(courseContent);
			course.setCardId(cardId);
			course.setCardName(cardName);
			course.setCourseStatus(0);
			OrgClassify ocf = orgClassifyBiz.findById(Long.valueOf(orgClassifyId));
			course.setOrgClassifyId(Long.valueOf(orgClassifyId));
			course.setCourseLables(ocf.getClassifyName());
			if(teacherId!=null){
				course.setTeacherId(teacherId.longValue());
				course.setTeacherName(teacherName);
			}
			course.setOrgId(org.getId());
			course.setOrgName(org.getOrgName());
			course.setCourseNum(courseNum);
			try{
				course.setStartTime(DateUtil.formateStringToDate(startTimeString,DateUtil.DATE_FROMAT_PATTERN_ONE));
				course.setEndTime(DateUtil.formateStringToDate(endTimeString,DateUtil.DATE_FROMAT_PATTERN_ONE));
			}catch(Exception e){
				e.printStackTrace();
			}

			if(courseId!=null&&courseId>0){//更新
				course.setId(courseId.longValue());
				courseBiz.updateSelectiveById(course);
				//更新课表
				updateLesson(lessons,course,org);

			}else{//新增
				course = courseBiz.insertCourse(course);
				//创建课表
				createLesson(lessons,course,org);
				IgrowthCourseCard igCard = igcardBiz.selectById(course.getCardId());
				igCard.setBindStatus(1);
				igcardBiz.updateSelectiveById(igCard);
				
			}

			//标签更新
			orgBiz.updateOrgClassifyIds(org.getId());

			isSuccess = Boolean.TRUE;
		}
		resultMap.put("isSuccess",isSuccess);

    	return resultMap;
	}

	private void createLesson(List<Map<String,Object>> lessons,Course course,Org org){
		List<Lesson> lessonList = new ArrayList<Lesson>();
		if(lessons.size()>0){
			for(Map<String,Object> map : lessons){
				Date startTime = null;
				Date endTime = null;
				String teacherNameLesson = (String)map.get("teacherName");
				Integer teacherIdLesson = (Integer)map.get("teacherId");
				try {
					String startDate = (String)map.get("startDate");
					String  courseTime = (String)map.get("courseTime");
					String[] timePeriod = courseTime.split("--");
					String timeStartString = startDate+" "+timePeriod[0];
					String timeEndString = startDate+" "+timePeriod[1];
					startTime = DateUtil.formateStringToDate(timeStartString,DateUtil.DATE_FROMAT_PATTERN_TWO);
					endTime = DateUtil.formateStringToDate(timeEndString,DateUtil.DATE_FROMAT_PATTERN_TWO);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Lesson lesson = new Lesson();
				lesson.setStartTime(startTime);
				lesson.setEndTime(endTime);
				lesson.setTeacherId(teacherIdLesson);
				lesson.setTeacherName(teacherNameLesson);
				lesson.setOrgId(org.getId());
				lesson.setOrgName(org.getOrgName());
				lesson.setLessonStatus(0);
				lesson.setCourseId(course.getId());
				lesson.setCourseName(course.getCourseName());
				lessonList.add(lesson);

			}
			//根据开始时间排序
			lessonList.sort((Lesson h1, Lesson h2) -> h1.getStartTime().compareTo(h2.getStartTime()));
			for(int i=0;i<lessonList.size();i++){
				int j=i+1;
				lessonList.get(i).setLessonName(course.getCourseName()+"第"+j+"节课");
				lessonBiz.insertSelective(lessonList.get(i));
			}

		}
	}

	private void updateLesson(List<Map<String,Object>> lessons,Course course,Org org){
		deleteLesson(course);
		createLesson(lessons,course,org);
	}

	private void deleteLesson(Course course){
		Lesson lesson = new Lesson();
		lesson.setCourseId(course.getId());
		List<Lesson> lessons = lessonBiz.selectList(lesson);
		for(Lesson lesson1 : lessons){
			lessonBiz.delete(lesson1);
		}
	}

	/**
	 * 删除课程和课表
	 * @param id
	 */
	@DeleteMapping(value="/orgcourses/delete/{id}")
	@Transactional
	public Map<String,Object> deleteCourseLesson(@PathVariable Long id){
		Map<String,Object> result = new HashMap<String,Object>();
		Boolean isSuccess = Boolean.FALSE;
		MCourse mCourse = new MCourse();
		mCourse.setCourseId(id);
		List<MCourse> mCourseList = igrowthMCourseBiz.selectList(mCourse);
		if(mCourseList!=null&&mCourseList.size()>0){
			result.put("isSuccess",isSuccess);
			result.put("message","该课程已使用，请勿删除！");
			return result;
		}
		Course course = courseBiz.selectById(id);
		IgrowthCourseCard igCard = igcardBiz.selectById(course.getCardId());
		igCard.setBindStatus(0);
		igcardBiz.updateSelectiveById(igCard);
		deleteLesson(course);
		courseBiz.delete(course);
		//标签更新
		Org o = new Org();
		o.setBaseUserId(Integer.parseInt(getCurrentUserId()));
		o = orgBiz.selectOne(o);
		orgBiz.updateOrgClassifyIds(o.getId());
		isSuccess = Boolean.TRUE;
		result.put("isSuccess",isSuccess);
		return result;
	}
	
	/**
	 *当前课程卡能否可修改
	 * @author dingshuyan  
	 * @param courseId
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/canmodify/{courseId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Boolean> canModify(@PathVariable Long courseId) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("result", igrowthMCourseBiz.findByCourseId(courseId).isEmpty());
		return result;
	}
}