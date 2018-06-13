package com.igrowth.app.vo;

import java.util.Date;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/2210:36
 *    desc    : 展示里程碑信息
 * </pre>
 */
public class MarkerInfoVO {
    private Long childId;
    private Long courseId;
    private String courseName;
    private String childNickname;
    private String childPic;
    private String markerTitle;
    private String pictureUrl;
    private String[] picArray = new String[10];
    private Date createTime;

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public String getChildNickname() {
        return childNickname;
    }

    public void setChildNickname(String childNickname) {
        this.childNickname = childNickname;
    }

    public String getChildPic() {
        return childPic;
    }

    public void setChildPic(String childPic) {
        this.childPic = childPic;
    }

    public String getMarkerTitle() {
        return markerTitle;
    }

    public void setMarkerTitle(String markerTitle) {
        this.markerTitle = markerTitle;
    }

    public String[] getPicArray() {
        String[] array =  this.pictureUrl.split(",");
        return array;
    }

    public void setPicArray(String[] picArray) {
        this.picArray = picArray;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
