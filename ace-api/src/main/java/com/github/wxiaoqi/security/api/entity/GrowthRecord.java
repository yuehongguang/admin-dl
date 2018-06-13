package com.github.wxiaoqi.security.api.entity;

import com.github.wxiaoqi.security.api.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "igrowth_record")
public class GrowthRecord {
    @Id
    private Long id;
    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    @Transient
    private String createTimeString;
    /**
     * 里程碑图片
     */
    @Column(name = "picture_url")
    private String pictureUrl;
    @Transient
    private String[] pictureArray = new String[10];
    /**
     * 里程碑标题
     */
    @Column(name = "marker_title")
    private String markerTitle;
    /**
     * 课程Id
     */
    @Column(name = "course_id")
    private Long courseId;
    /**
     * 课程名称
     */
    @Column(name = "course_name")
    private String courseName;
    /**
     * 孩子Id
     */
    @Column(name = "child_id")
    private Long childId;

    /**
     * 0  普通点
     * 1  里程碑点
     */
    @Column(name = "point_type")
    private Integer pointType;
    /**
     * 课表id
     */
    @Column(name = "mlesson_id")
    private Long mlessonId;
    /**
     * 课表名称
     */
    @Column(name = "mlesson_name")
    private String mlessonName;
    /**
     * 学习小时
     */
    @Column(name = "studay_hours")
    private Double studayHours;
    /**
     * 跳转url
     */
    @Column(name = "redirect_url")
    private String redirectUrl;

    @Column(name = "label")
    private String label;

    @Transient
    private List<GrowthRecord> recordList = new ArrayList<GrowthRecord>();

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return picture_url
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * @param pictureUrl
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    /**
     * @return marker_title
     */
    public String getMarkerTitle() {
        return markerTitle;
    }

    /**
     * @param markerTitle
     */
    public void setMarkerTitle(String markerTitle) {
        this.markerTitle = markerTitle;
    }

    /**
     * @return course_id
     */
    public Long getCourseId() {
        return courseId;
    }

    /**
     * @param courseId
     */
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    /**
     * @return child_id
     */
    public Long getChildId() {
        return childId;
    }

    /**
     * @param childId
     */
    public void setChildId(Long childId) {
        this.childId = childId;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getPointType() {
        return pointType;
    }

    public void setPointType(Integer pointType) {
        this.pointType = pointType;
    }

    public Long getMlessonId() {
        return mlessonId;
    }

    public void setMlessonId(Long mlessonId) {
        this.mlessonId = mlessonId;
    }

    public String getMlessonName() {
        return mlessonName;
    }

    public void setMlessonName(String mlessonName) {
        this.mlessonName = mlessonName;
    }

    public Double getStudayHours() {
        return studayHours;
    }

    public void setStudayHours(Double studayHours) {
        this.studayHours = studayHours;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String[] getPictureArray() {
        String[] array = null;
        if(StringUtils.isNotEmpty(this.pictureUrl)){
             array = this.pictureUrl.split(",");
        }
        return array;
    }

    public void setPictureArray(String[] pictureArray) {
        this.pictureArray = pictureArray;
    }

    public List<GrowthRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<GrowthRecord> recordList) {
        this.recordList = recordList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCreateTimeString() {
        if(createTime!=null){
            String createTimeString = DateUtil.formateDateToString(createTime,DateUtil.DATE_FROMAT_PATTERN_ONE);
            return createTimeString;
        }else{
            return null;
        }
    }

    public void setCreateTimeString(String createTimeString) {
        if(StringUtils.isNotEmpty(createTimeString)){
            try{

                Date date = DateUtil.formateStringToDate(createTimeString,DateUtil.DATE_FROMAT_PATTERN_ONE);
                this.setCreateTime(date);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.createTimeString = createTimeString;
    }

}