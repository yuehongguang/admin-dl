package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.mapper.GrowthRecordMapper;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.GrowthRecord;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
