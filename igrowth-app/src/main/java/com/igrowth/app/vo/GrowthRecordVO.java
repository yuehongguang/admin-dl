package com.igrowth.app.vo;

import com.github.wxiaoqi.security.api.entity.GrowthRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/2116:54
 *    desc    : 输入描述
 * </pre>
 */
public class GrowthRecordVO extends GrowthRecord {
    /**
     * 课程总数
     */
    private Integer coureSum;
    /**
     * 总学习时间（小时）
     */
    private Double studyHoursSum;

    private List<SumObjVO> labelSumInfo;

    /**
     * 某个日期包含多个课程
     */
    private List<Map<String,Object>> dayCoursesList = new ArrayList<Map<String,Object>>();

    public Integer getCoureSum() {
        return coureSum;
    }

    public void setCoureSum(Integer coureSum) {
        this.coureSum = coureSum;
    }

    public List<Map<String,Object>> getDayCoursesList() {
        return dayCoursesList;
    }

    public void setDayCoursesList(List<Map<String,Object>> dayCoursesList) {
        this.dayCoursesList = dayCoursesList;
    }

    public List<SumObjVO> getLabelSumInfo() {
        return labelSumInfo;
    }

    public void setLabelSumInfo(List<SumObjVO> labelSumInfo) {
        this.labelSumInfo = labelSumInfo;
    }

    public Double getStudyHoursSum() {
        return studyHoursSum;
    }

    public void setStudyHoursSum(Double studyHoursSum) {
        this.studyHoursSum = studyHoursSum;
    }
}
