package com.github.wxiaoqi.security.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "igrowth_heart")
public class Heart {
	
	public static Integer LESSON_HEART_NUM = 10; //上课爱心数量

	public static Integer SHARE_HEART_NUM = 2; //分享爱心数量 每天最多5次
	
    @Id
    private Integer id;

    @Column(name = "heart_num")
    private Integer heartNum;

    @Column(name = "heart_name")
    private String heartName;
    
    /**
     * 0系统爱心 2到机构签到爱心
     */
    @Column(name = "heart_type")
    private Integer heartType;

    @Column(name = "heart_time")
    private Date heartTime;

    @Column(name = "child_id")
    private Long childId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return heart_num
     */
    public Integer getHeartNum() {
        return heartNum;
    }

    /**
     * @param heartNum
     */
    public void setHeartNum(Integer heartNum) {
        this.heartNum = heartNum;
    }

    /**
     * @return heart_name
     */
    public String getHeartName() {
        return heartName;
    }

    /**
     * @param heartName
     */
    public void setHeartName(String heartName) {
        this.heartName = heartName;
    }
    
    /**  
	 * heartType.  
	 */
	public Integer getHeartType() {
		return heartType;
	}

	/**  
	 * heartType.  
	 *  
	 * @param   heartType    the heartType to set  
	 * @since   JDK 1.8  
	 */
	public void setHeartType(Integer heartType) {
		this.heartType = heartType;
	}

	/**
     * @return heart_time
     */
    public Date getHeartTime() {
        return heartTime;
    }

    /**
     * @param heartTime
     */
    public void setHeartTime(Date heartTime) {
        this.heartTime = heartTime;
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