package com.igrowth.app.vo;

import java.util.Date;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/2116:54
 *    desc    : 输入描述
 * </pre>
 */
public class SumObjVO {

    /**
     * recordId
     */
    private Long id;//成长轨迹id
    /**
     * 标签
     */
    private String label;
    /**
     * 总学习时间（小时）
     */
    private Double labelHoursSum;

    /**
     * mlessonId
     */
    private Long mlessonId;

    /**
     * 该标签最后一节课的结束时间
     */
    private Date endTime;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getLabelHoursSum() {
        return labelHoursSum;
    }

    public void setLabelHoursSum(Double labelHoursSum) {
        this.labelHoursSum = labelHoursSum;
    }

    public Long getMlessonId() {
        return mlessonId;
    }

    public void setMlessonId(Long mlessonId) {
        this.mlessonId = mlessonId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
