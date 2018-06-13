package com.igrowth.app.biz;

import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.GrowthRecord;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.igrowth.app.mapper.GrowthRecordMapper;
import com.igrowth.app.vo.GrowthRecordVO;
import com.igrowth.app.vo.MarkerInfoVO;
import com.igrowth.app.vo.SumObjVO;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/2116:44
 *    desc    : 输入描述
 * </pre>
 */
@Service
public class GrowthRecordBiz extends BaseBiz<GrowthRecordMapper, GrowthRecord> {

    @Autowired
    private CourseBiz courseBiz;

    @Autowired
    private MLessonBiz mLessonBiz;


    /**
     * 根据日期和孩子Id查询该星期的课程信息
     * @param date
     * @param childId
     * @return
     */
    public GrowthRecordVO findGrowthRecordByDate(Long childId,Date date) {
        GrowthRecordVO growthRecordVO = new GrowthRecordVO();

        //统计label信息
        List<SumObjVO> sumObjVOList = sumLabelInfo(childId);
        growthRecordVO.setLabelSumInfo(sumObjVOList);
        growthRecordVO.setCoureSum(sumObjVOList.size());
        growthRecordVO.setStudyHoursSum(mapper.findStudyHoursSumByChildId(childId));


        List<Map<String,Object>> dayCoursesList = new ArrayList<Map<String,Object>>();
        Map<String,Date> dateMap = DateUtil.getMondyToSundayByNow(date,DateUtil.DATE_NAME_TWO);
        List<GrowthRecord> records = this.mapper.findGrowthRecordByDate(childId,dateMap.get("Mon"),dateMap.get("Sun"));
         for(String week : dateMap.keySet()){
            Map<String,Object> growthRecordMap = new HashMap<String,Object>();
            List<Object> dayList = new ArrayList<Object>();
            List<GrowthRecord> growthRecords = new ArrayList<GrowthRecord>();
            for(GrowthRecord growthRecord : records){
                if (DateUtils.isSameDay(dateMap.get(week),growthRecord.getCreateTime())){
                    /*Course c = courseBiz.selectById(growthRecord.getCourseId());
                    growthRecord.setCoureName(c.getCourseName());*/
                    growthRecords.add(growthRecord);
                }
            }
            /**对于同一天同一课程出现多个点的数据封装处理**/
            Map<String, List<GrowthRecord>> countCourseOneDay =growthRecords.stream().collect(groupingBy(GrowthRecord::getLabel));
            for(String label : countCourseOneDay.keySet()){
                List<GrowthRecord> list = countCourseOneDay.get(label);
                if(list.size()>1){
                    GrowthRecord newObj = new GrowthRecord();
                    newObj.setPointType(0);//普通点
                    newObj.setCourseName(list.get(0).getCourseName());
                    newObj.setCourseId(list.get(0).getCourseId());
                    newObj.setLabel(list.get(0).getLabel());
                    double sumHours = list.stream().mapToDouble(GrowthRecord::getStudayHours).sum();
                    newObj.setStudayHours(sumHours);
                    Long minId = list.stream().mapToLong(GrowthRecord::getId).min().getAsLong();
                    newObj.setId(minId);
                    newObj.setRecordList(list);
                    List<GrowthRecord> filterList = growthRecords.stream().filter(a -> !a.getLabel().equals(label)).collect(Collectors.toList());
                    filterList.add(newObj);
                    growthRecords = filterList;

                }else if(list.size()==1){
                    GrowthRecord newObj = new GrowthRecord();
                    newObj.setPointType(list.get(0).getPointType());
                    newObj.setId(list.get(0).getId());
                    newObj.setStudayHours(list.get(0).getStudayHours());
                    newObj.setLabel(list.get(0).getLabel());
                    newObj.setRecordList(list);
                    growthRecords.remove(list.get(0));
                    growthRecords.add(newObj);
                }
            }
            if(growthRecords.size()>1){
                growthRecords.sort((GrowthRecord h1, GrowthRecord h2) -> h1.getId().compareTo(h2.getId()));
                //growthRecords.sort(Comparator.comparingLong(GrowthRecord::getId));
            }
            growthRecordMap.put("week", week);
            growthRecordMap.put("date", DateUtil.formateDateToString(dateMap.get(week),"yyyy/MM/dd"));
            growthRecordMap.put("info",growthRecords);
            dayCoursesList.add(growthRecordMap);
            //dayCoursesMap.put(DateUtil.formateDateToString(day,"MM/dd"),growthRecords);
        }

        growthRecordVO.setDayCoursesList(dayCoursesList);
        return growthRecordVO;
    }

   public MarkerInfoVO findMarkinfoByParam(Long childId,Long courseId,Date date){
        MarkerInfoVO markerInfoVO = this.mapper.findMarkinfoByParam(childId,courseId,date);
        return markerInfoVO;
   }
   
   public void createRecord(Long childId, MLesson mLesson,String lable, String redirectUrl) {
		GrowthRecord growthRecord = new GrowthRecord();
		growthRecord.setChildId(childId);
		if(mLesson.getCourseId()!=null){
			Course c = courseBiz.selectById(mLesson.getCourseId());
			growthRecord.setLabel(c.getCourseLable()[0]);
		}else{
            growthRecord.setLabel(lable);
        }
		growthRecord.setCourseId(mLesson.getCourseId());
		growthRecord.setCourseName(mLesson.getCourseName());
		growthRecord.setCreateTime(mLesson.getEndTime());
		growthRecord.setMarkerTitle(mLesson.getCourseName());
		growthRecord.setMlessonId(mLesson.getId());
		growthRecord.setMlessonName(mLesson.getLessonName());
		growthRecord.setRedirectUrl(redirectUrl);
		growthRecord.setPointType(0);
		growthRecord.setStudayHours((double) DateUtil.getDateSpace(mLesson.getStartTime(), mLesson.getEndTime()) / 3600);
		insert(growthRecord);
	}

	public List<SumObjVO> sumLabelInfo(Long childId){
       List<SumObjVO> list = mapper.sumLabelInfo(childId);
       for(SumObjVO info : list){
           GrowthRecord record = this.selectById(info.getId());
           if(record.getMlessonId()!=null){
               MLesson mLesson = mLessonBiz.selectById(info.getMlessonId());
               info.setEndTime(mLesson.getEndTime());
           }else{
               info.setEndTime(record.getCreateTime());
           }
       }

       return list;
    }
}
