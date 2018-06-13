package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.mapper.MCourseMapper;
import com.github.wxiaoqi.security.admin.mapper.MLessonMapper;
import com.github.wxiaoqi.security.admin.vo.IgrowthMCourseVO;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.IgrowthCourseCard;
import com.github.wxiaoqi.security.api.entity.MCourse;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.groupingBy;


/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-12-11 18:43:50
 */
@Service
public class IgrowthMCourseBiz extends BaseBiz<MCourseMapper,MCourse> {

    @Autowired
    private MCourseMapper mCourseMapper;
    @Autowired
    private MLessonMapper mLessonMapper;
    @Autowired
    private IgrowthCourseCardBiz igrowthCourseCardBiz;
    /**
     * 查询当前机构下的客户信息
     * @param limit
     * @param page
     * @param sortColumn
     * @param orderBy
     * @param userId
     * @return
     */
    public Map<String,Object> findCourseByPage(int limit, int page, long orgId,int childId,String namePhone) {
        page = (page-1)*limit;
        Map<String,Object> resultPage = new HashMap<String,Object>();
        List<IgrowthMCourseVO> IgrowthMCourseVOList = new ArrayList<IgrowthMCourseVO>();
        String parentPhone = null;
        String childName = null;
        if(StringUtils.isNotEmpty(namePhone)){
            if(Pattern.compile("[0-9]*").matcher(namePhone).matches()){
                parentPhone = namePhone;
            }else{
                childName = namePhone;
            }
        }
        List<MCourse> mCourseList = mCourseMapper.selectByOrgIdGroupByChildId(limit,page,orgId,childId,parentPhone,childName);
        Long mCourseSum = mCourseMapper.selectByOrgIdGroupByChildIdCount(orgId,childId,parentPhone,childName);
        //根据childId分组
        Map<Long, List<MCourse>> mCourseInfo =mCourseList.stream().collect(groupingBy(MCourse::getChildId));
        for(Long id : mCourseInfo.keySet()){
            IgrowthMCourseVO IgrowthMCourseVO = new IgrowthMCourseVO();
            IgrowthMCourseVO.setChildId(id);
            List<MCourse> mList = mCourseMapper.selectByOrgIdAndChildId(orgId,id.intValue(),parentPhone,childName);
            //编辑页面补充请假次数和签到次数
            if(childId>0){
                for(MCourse course : mList){
                    MLesson lesson = new MLesson();
                    if(course.getCourseId()!=null){
                        lesson.setCourseId(course.getCourseId().longValue());
                        lesson.setOrgId(Long.valueOf(orgId));
                        lesson.setChildId(course.getChildId());
                        lesson.setLessonStatus(1);//已签到次数
                        int signInTimes = mLessonMapper.selectCount(lesson);
                        lesson.setLessonStatus(2);//请假次数
                        int leaveTimes = mLessonMapper.selectCount(lesson);
                        course.setLeaveTimes(leaveTimes);
                        course.setSignInTimes(signInTimes);
                    }
                }
            }
            if(mList.size()>0){
                IgrowthMCourseVO.setStartTime(mList.get(0).getStartTime());
                IgrowthMCourseVO.setChildName(mList.get(0).getChildName());
                IgrowthMCourseVO.setParentPhone(mList.get(0).getParentPhone());
                IgrowthMCourseVO.setMcourseList(mList);
                IgrowthMCourseVO.setCourseListSize(mList.size());
            }
            IgrowthMCourseVOList.add(IgrowthMCourseVO);
        }
        //按照时间排序
        IgrowthMCourseVOList.sort((IgrowthMCourseVO h1, IgrowthMCourseVO h2) -> h2.getStartTime().compareTo(h1.getStartTime()));

        //resultPage.put("total",mCourseInfo.keySet().size()>0?Long.valueOf(mCourseInfo.keySet().size()):0L);
        resultPage.put("total",mCourseSum!=null?mCourseSum:0L);
        resultPage.put("data",IgrowthMCourseVOList);
        return resultPage;
    }

    public List<MCourse> findByCourseId(@Param("courseId")Long courseId){
        return mCourseMapper.selectByCourseId(courseId);
    }

	public MCourse findByChildIdAndCourseId(Long childId, Long courseId) {
		MCourse mcCourse = new MCourse();
		mcCourse.setChildId(childId);
		mcCourse.setCourseId(courseId);
		return selectOne(mcCourse);
	}
	
	public MCourse findByChildIdAndCourseCardId(Long childId, Long cardId) {
		MCourse mcCourse = new MCourse();
		mcCourse.setChildId(childId);
		mcCourse.setCardId(cardId);
		return selectOne(mcCourse);
	}

	public void bulidMCourse(MCourse mc, Course c) {
		IgrowthCourseCard courseCard = igrowthCourseCardBiz.selectById(c.getCardId());
		MCourse mCourse = new MCourse();
		BeanUtils.copyProperties(c, mCourse);
		mCourse.setId(null);
		mCourse.setChildId(mc.getChildId());
		mCourse.setChildName(mc.getChildName());
		mCourse.setCourseId(c.getId());
		mCourse.setStartTime(new Date());
		mCourse.setEndTime(DateUtil.getDateAfter(mCourse.getStartTime(), courseCard.getValidDays()));
		mCourse.setCourseStatus(0);
		mCourse.setCourseLables(courseCard.getCourseLabel());
		mCourse.setAllTimes(c.getCourseNum());
		mCourse.setRemainTimes(courseCard.getCourseNum());
		mCourse.setOrgId(c.getOrgId().intValue());
		mCourse.setCardId(courseCard.getId());
		mCourse.setCourseLables(c.getCourseLables());
		mCourse.setParentPhone(mc.getParentPhone());
		mCourse.setCourseType(c.getCourseType());
		mCourse.setScheduleStartTime(c.getStartTime());
		mCourse.setScheduleEndTime(c.getEndTime());
		insertSelective(mCourse);
	}

    public MCourse findByCourseIdAndChildId(Long courseId, Long childId) {
        return mapper.findByCourseIdAndChildId(courseId,childId);
    }
}