package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "igrowth_m_circle")
public class MyCircle {

    @Id
    private Long id;

    /**
     * 0 关注
     * 1 取消关注
     */
    private Integer isfocus;

    @Column(name = "circle_id")
    private Long circleId;

    @Column(name = "child_id")
    private Long childId;

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
     * @return circle_id
     */
    public Long getCircleId() {
        return circleId;
    }

    /**
     * @param circleId
     */
    public void setCircleId(Long circleId) {
        this.circleId = circleId;
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
}