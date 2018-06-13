package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.*;
import com.github.wxiaoqi.security.admin.vo.LessonVO;
import com.github.wxiaoqi.security.admin.vo.MLessonVo;
import com.github.wxiaoqi.security.api.entity.*;
import com.github.wxiaoqi.security.common.msg.AppResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("igrowthMLesson")
public class IgrowthMLessonController extends BaseController<MLessonBiz,MLesson> {

    @Autowired
    private MLessonBiz mLessonBiz;
    @Autowired
    private ChildBiz childBiz;
    @Autowired
    private CourseBiz courseBiz;
    @Autowired
    private LessonBiz lessonBiz;
    @Autowired
    private OrgBiz orgBiz;
    @Autowired
    private IgrowthMCourseBiz mCourseBiz;
    /**
     * 根据当前机构查询课程信息
     * @param limit
     * @param page
     * @param sortColumn
     * @param orderBy
     * @return
     */
    @GetMapping("/lessons")
    public TableResultResponse<MLessonVo> findCardByPage(@RequestParam(defaultValue = "10") int limit,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "startTime") String sortColumn,
                                                         @RequestParam(defaultValue = "asc") String orderBy,
                                                         @RequestParam(defaultValue = "") String name) {
        String userId = getCurrentUserId();
        if(StringUtils.isNotEmpty(userId)){
            Org org = new Org();
            org.setBaseUserId(Integer.parseInt(userId));
            org = orgBiz.selectOne(org);
            Map<String,Object> result = mLessonBiz.selectByMlessions(limit,page,org.getId(),0,name);
            int total = (Integer)result.get("total");
            List<MLessonVo> list = (List)result.get("list");
            return new TableResultResponse<MLessonVo>(total, list);
        }else{
            return new TableResultResponse<MLessonVo>();
        }

    }

    /**
     * 修改我的课程状态
     * @param id
     * @param status
     * @return
     */

    @PutMapping("/status/{id}")
    public Map<String,Object> updateStatus(@PathVariable(value = "id") Long id,
                                           @RequestParam(value="status",defaultValue = "0") Integer status) {
        Map<String,Object> result = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        if(status!=0){
            try {
                mLessonBiz.updateStatus(id,status);
                isSuccess = Boolean.TRUE;
            } catch (Exception e) {
                isSuccess = Boolean.FALSE;
                e.printStackTrace();
            }
        }

        result.put("isSuccess", isSuccess);
        return result;
    }

    /**
     * 机构预约课程
     * @param entity
     * @return
     */
    @RequestMapping(value = "appointment",method = RequestMethod.POST)
    @ResponseBody
    public  AppResponse<Object> addMLesson(@RequestBody MLesson entity){

        try{
            MLesson mlesson = mLessonBiz.findByChildIdAndLessonId(entity.getChildId(),entity.getLessonId());
            MCourse mCourse = mCourseBiz.findByChildIdAndCourseId(entity.getChildId(),entity.getCourseId());
            if(mCourse.getRemainTimes()<1){
                return new AppResponse<Object>(200, "约课失败，当前课程卡已经约完.", false);
            }

            if(mlesson!=null&&mlesson.getLessonStatus()!=-1){
                return new  AppResponse<Object>(200, "当前课程已经预约，请不要重复预约.", false);
            }
            Lesson lesson = lessonBiz.selectById(entity.getLessonId());
            if(lesson.getEndTime().before(new Date())){
                return new  AppResponse<Object>(200, "当前课程已结束，暂不能预约.", false);
            }
            if(lesson.getStartTime().before(new Date())){
                return new  AppResponse<Object>(200, "当前课程已开课，暂不能预约.", false);
            }
            Course course = courseBiz.selectById(lesson.getCourseId());
            LessonVO lv = lessonBiz.findLessonByLessonId(entity.getLessonId());
            if (lv.getSubnum() >= course.getCourseMaxStudent()) {
                return new AppResponse<Object>(200, "约课失败，当前课程已约满.", false);
            }

            //插入约课记录
            Org org = new Org();
            org.setBaseUserId(Integer.parseInt(getCurrentUserId()));
            org = orgBiz.selectOne(org);
            Child cl = childBiz.selectById(entity.getChildId());
            Course cs = courseBiz.selectById(entity.getCourseId());
            entity.setAccountId(cl.getParentId());
            entity.setCourseName(lesson.getCourseName());
            entity.setCourseLables(cs.getCourseLable());
            entity.setLessonName(lesson.getLessonName());
            entity.setEndTime(lesson.getEndTime());
            entity.setStartTime(lesson.getStartTime());
            entity.setTeacherName(lesson.getTeacherName());
            entity.setOrgId(org.getId());
            entity.setOrgAddress(org.getOrgAddress());
            entity.setOrgName(org.getOrgName());
            entity.setOrgTel(org.getOrgTel());
            entity.setLessonStatus(3);
            baseBiz.insertSelective(entity);
            //预约减少课时数
            if(mCourse!=null){
                mCourse.setRemainTimes(mCourse.getRemainTimes()-1);
                mCourseBiz.updateSelectiveById(mCourse);
            }
            //lessonBiz.sublesson(entity.getLessonId(), entity.getAccountId(), entity.getChildId(), mlesson);
            return new AppResponse<Object>(200, "约课成功.", true);
        }catch(Exception e){
            e.printStackTrace();
            return new AppResponse<Object>(200, "fail", false);
        }



        /*String userId = getCurrentUserId();
        Org org = new Org();
        org.setBaseUserId(Integer.parseInt(userId));
        org = orgBiz.selectOne(org);
        Lesson le = lessonBiz.selectById(entity.getLessonId());
        Child cl = childBiz.selectById(entity.getChildId());
        Course cs = courseBiz.selectById(entity.getCourseId());
        entity.setAccountId(cl.getParentId());
        entity.setCourseName(le.getCourseName());
        entity.setCourseLables(cs.getCourseLable());
        entity.setLessonName(le.getLessonName());
        entity.setEndTime(le.getEndTime());
        entity.setStartTime(le.getStartTime());
        entity.setTeacherName(le.getTeacherName());
        entity.setOrgId(org.getId());
        entity.setOrgAddress(org.getOrgAddress());
        entity.setOrgName(org.getOrgName());
        entity.setOrgTel(org.getOrgTel());
        entity.setLessonStatus(0);
        baseBiz.insertSelective(entity);
        return new ObjectRestResponse<MLesson>().rel(true);*/
    }

}