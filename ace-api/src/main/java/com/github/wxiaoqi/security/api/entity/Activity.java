package com.github.wxiaoqi.security.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * ClassName: Activity <br/>
 * Function: 机构活动 <br/>
 * date: 2017年10月26日 下午3:50:51 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Table(name = "igrowth_activity")
public class Activity {
	@Id
	private Integer id;

	/**
	 * 活动名称
	 */
	@Column(name = "activity_name")
	private String activityName;

	/**
	 * 活动详情
	 */
	@Column(name = "activity_detail")
	private String activityDetail;

	/**
	 * 活动简写
	 */
	@Column(name = "activity_short")
	private String activityShort;

	/**
	 * 活动状态
	 * 0 进行中 1关闭状态
	 */
	@Column(name = "activity_status")
	private Integer activityStatus;

	/**
	 * 活动开始时间
	 */
	@Column(name = "start_time")
	private Date startTime;
	
	@Transient
	private String startTimeStr;

 
	@Column(name = "end_time")
	private Date endTime;
	
	@Transient
	private String endTimeStr;
	
	/**
	 * 活动创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 活动url
	 */
	@Column(name = "activity_url")
	private String activityUrl;

	/**
	 * 活动名称机构id
	 */
	@Column(name = "org_id")
	private Long orgId;
	
	/**
	 * 适用人群
	 */
	@Column(name = "for_people")
	private String forPeople;
	

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
	 * @return activity_name
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @param activityName
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return activity_detail
	 */
	public String getActivityDetail() {
		return activityDetail;
	}

	/**
	 * @param activityDetail
	 */
	public void setActivityDetail(String activityDetail) {
		this.activityDetail = activityDetail;
	}

	/**
	 * @return activity_short
	 */
	public String getActivityShort() {
		return activityShort;
	}

	/**
	 * @param activityShort
	 */
	public void setActivityShort(String activityShort) {
		this.activityShort = activityShort;
	}

	/**
	 * @return activity_status
	 */
	public Integer getActivityStatus() {
		return activityStatus;
	}

	/**
	 * @param activityStatus
	 */
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}

	/**
	 * @return start_time
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return end_time
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * activityUrl.
	 * 
	 */
	public String getActivityUrl() {
		return activityUrl;
	}

	/**
	 * activityUrl.
	 */
	public void setActivityUrl(String activityUrl) {
		this.activityUrl = activityUrl;
	}

	/**  
	 * forPeople.  
	 *  
	 * @return  the forPeople  
	 * @since   JDK 1.8  
	 */
	public String getForPeople() {
		return forPeople;
	}

	/**  
	 * forPeople.  
	 *  
	 * @param   forPeople    the forPeople to set  
	 * @since   JDK 1.8  
	 */
	public void setForPeople(String forPeople) {
		this.forPeople = forPeople;
	}

	/**  
	 * startTimeStr.  
	 *  
	 * @return  the startTimeStr  
	 * @since   JDK 1.8  
	 */
	public String getStartTimeStr() {
		return startTimeStr;
	}

	/**  
	 * startTimeStr.  
	 *  
	 * @param   startTimeStr    the startTimeStr to set  
	 * @since   JDK 1.8  
	 */
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	/**  
	 * endTimeTimeStr.  
	 *  
	 * @return  the endTimeTimeStr  
	 * @since   JDK 1.8  
	 */
	public String getEndTimeStr() {
		return endTimeStr;
	}

	/**  
	 * endTimeTimeStr.  
	 *  
	 * @param   endTimeTimeStr    the endTimeTimeStr to set  
	 * @since   JDK 1.8  
	 */
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}