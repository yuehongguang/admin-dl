package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.CourseBiz;
import com.github.wxiaoqi.security.admin.biz.LessonBiz;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.Lesson;
import com.github.wxiaoqi.security.api.vo.OrgClassifyVo;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("lesson")
public class LessonController extends BaseController<CourseBiz,Course> {
	
	@Autowired
	private LessonBiz lessonBiz;

    @RequestMapping(value = "/lesson/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public List<OrgClassifyVo> getLesson(@PathVariable Long courseId){
    	List<OrgClassifyVo> lsit = lessonBiz.findByCourseId(courseId);
		return lsit;
    }

    /**
     * 生成预览课表（不存储）
     * @param params
     * @return
     */
    @PostMapping(value="/lessons/preview")
    public Map<String,Object> createLesson(@RequestBody Map<String, Object> params){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        Boolean isSuccess = Boolean.FALSE;
        String message = null;
        List<Lesson> lessonList = new ArrayList<Lesson>();
        if(params!=null){
            String teacherName = (String)params.get("teacherName");
            Integer teacherId = (Integer)params.get("teacherId");
            String lessonStartTime = (String)params.get("lessonStartTime");
            String lessonEndTime = (String)params.get("lessonEndTime");
            /**
             * roleType:
             * 0: 每天
             * 1： 隔天
             * 2： 每周
             */
            Integer roleType = (Integer)params.get("roleType");
            List weekList = (List)params.get("weekList");
            String startTime = (String)params.get("startTime");
            String endTime = (String)params.get("endTime");
            Date dateStart = null;
            Date dateEnd = null;
            Date lessonStart = null;
            Date lessonEnd = null;
            String courseTime = null;
            try{
                 dateStart = transferDateFromString(startTime);
                 dateEnd = transferDateFromString(endTime);
                lessonStart = transferDateFromString(lessonStartTime);
                lessonEnd = transferDateFromString(lessonEndTime);
                //拼接课表的上下课区间
                String timeStart = DateUtil.formateDateToString(lessonStart,"HH:mm:ss");
                String timeEnd = DateUtil.formateDateToString(lessonEnd,"HH:mm:ss");
                courseTime = timeStart+"--"+timeEnd;
            }catch (Exception e){
                e.printStackTrace();
                message = "日期格式错误";
            }

            DateTime jodaStart = new DateTime(dateStart);
            DateTime jodaEnd = new DateTime(dateEnd);
            Duration duration = new Duration(jodaStart, jodaEnd);
            //获取课程日期之间的天数
            long days = duration.getStandardDays()+1;
            if(roleType!=null) {
                int type = Integer.valueOf(roleType);
                DateTime tempDate = null;
                for(Long i = 0L; i < days; i++){
                    if(type==1){
                        tempDate = jodaStart.plusDays(i.intValue()*2);
                        if(tempDate.isAfter(jodaEnd)){
                            break;
                        }
                    }else{
                        tempDate = jodaStart.plusDays(i.intValue());
                    }
                    Lesson les = createLesson(courseTime, tempDate,type,weekList);
                    les.setTeacherName(teacherName);
                    les.setTeacherId(teacherId);
                    if(les.getCourseTime()!=null){
                        lessonList.add(les);
                    }
                }
                isSuccess = Boolean.TRUE;
                resultMap.put("isSuccess",isSuccess);
                resultMap.put("lessonList",lessonList);
            }else{
                message = "请选择排课类型";
            }
        }
        resultMap.put("message",message);
        return resultMap;
    }

    private Date transferDateFromString(String dateTime){
        Date date = null;
        if(dateTime.contains("Z")){
            dateTime=dateTime.replace("Z", " UTC");
            try{
                date = DateUtil.formateStringToDate(dateTime,"yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                date = DateUtil.formateStringToDate(dateTime,DateUtil.DATE_FROMAT_PATTERN_TWO);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return date;
    }
    private Lesson createLesson(String courseTime,DateTime dateTime,Integer type,List weekList) {
        Lesson les = new Lesson();
        if(0==type||1==type){//每天和隔天
            Date d0 = dateTime.toDate();
            les.setStartDate(d0);
            les.setCourseTime(courseTime);
        }else if(2==type){//每周
            for (Object week : weekList) {
                int wek = Integer.valueOf(week.toString());
                if ( wek == dateTime.getDayOfWeek()) {
                    Date d = dateTime.toDate();
                    les.setStartDate(d);
                    les.setCourseTime(courseTime);
                    return les;
                }
            }
        }
        return les;

    }

    @RequestMapping(value = "/unlesson/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Lesson> getUnLesson(@PathVariable Long courseId){
        List<Lesson> lsit = lessonBiz.findByCourseIdAndStatus(courseId,0);
        return lsit;
    }
}