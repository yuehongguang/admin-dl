package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "igrowth_circle")
public class Circle {
    @Id
    private Long id;

    @Column(name = "create_time")
    private Date createTime;//创建时间

    @Column(name = "circle_name")
    private String circleName;//圈子名称

    private Integer status;//状态（预留）

    @Column(name = "circle_icon")
    private String circleIcon;//圈子图标

    private Integer isfocus;//是否关注

    @Column(name = "circle_background")
    private String circleBackground;//圈子背景图

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
     * @return circle_name
     */
    public String getCircleName() {
        return circleName;
    }

    /**
     * @param circleName
     */
    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return circleIcon
     */
    public String getCircleIcon() {
        return circleIcon;
    }

    /**
     * @param circleicon
     */
    public void setCircleIcon(String circleIcon) {
        this.circleIcon = circleIcon;
    }

    /**
     * @return isfocus
     */
    public Integer getIsfocus() {
        return isfocus;
    }

    /**
     * @param isfocus
     */
    public void setIsfocus(Integer isfocus) {
        this.isfocus = isfocus;
    }

    /**
     * @return circleBackground
     */
    public String getCircleBackground() {
        return circleBackground;
    }

    /**
     * @param circlebackground
     */
    public void setCircleBackground(String circleBackground) {
        this.circleBackground = circleBackground;
    }
}