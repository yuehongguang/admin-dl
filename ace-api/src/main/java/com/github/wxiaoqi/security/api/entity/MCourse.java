package com.github.wxiaoqi.security.api.entity;

import com.github.wxiaoqi.security.api.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "igrowth_m_course")
public class MCourse {
	@Id
	private Long id;

	@Column(name = "course_id")
	private Long courseId;

	/**
	 * 0可用 1不可用 2自学课程
	 */
	@Column(name = "course_status")
	private Integer courseStatus;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "course_subject")
	private String courseSubject;

	@Column(name = "remain_times")
	private Integer remainTimes;

	@Column(name = "all_times")
	private Integer allTimes;

	@Column(name = "start_time")
	private Date startTime;

	@Column(name = "end_time")
	private Date endTime;

	@Column(name = "org_name")
	private String orgName;

	@Column(name = "org_id")
	private Integer orgId;

	@Column(name = "child_id")
	private Long childId;

	@Column(name = "child_name")
	private String childName;

	@Column(name = "parent_phone")
	private String parentPhone;

	@Column(name = "card_id")
	private Long cardId;

	@Column(name = "card_name")
	private  String cardName;
	
	/**
	 * 0系统课程 1预约课程
	 */
	@Column(name = "course_type")
	private Integer courseType;
	
	@Column(name = "course_lables")
	private String courseLables;

	@Column(name = "schedule_start_time")
	private Date scheduleStartTime;

	@Column(name = "schedule_end_time")
	private Date scheduleEndTime;

	@Transient
	private int leaveTimes;//请假次数
	@Transient
	private int signInTimes;//签到次数
	@Transient
	private String startTimeString;
	@Transient
	private String endTimeString;

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
	 * @return course_status
	 */
	public Integer getCourseStatus() {
		return courseStatus;
	}

	/**
	 * @param courseStatus
	 */
	public void setCourseStatus(Integer courseStatus) {
		this.courseStatus = courseStatus;
	}

	/**
	 * @return course_name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * courseSubject.
	 * 
	 */
	public String getCourseSubject() {
		return courseSubject;
	}

	/**
	 * courseSubject.
	 */
	public void setCourseSubject(String courseSubject) {
		this.courseSubject = courseSubject;
	}

	/**
	 * @return remain_times
	 */
	public Integer getRemainTimes() {
		return remainTimes;
	}

	/**
	 * @param remainTimes
	 */
	public void setRemainTimes(Integer remainTimes) {
		this.remainTimes = remainTimes;
	}

	/**
	 * @return all_times
	 */
	public Integer getAllTimes() {
		return allTimes;
	}

	/**
	 * @param allTimes
	 */
	public void setAllTimes(Integer allTimes) {
		this.allTimes = allTimes;
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
	 * @return org_name
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return org_id
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}


	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public int getLeaveTimes() {
		return leaveTimes;
	}

	public void setLeaveTimes(int leaveTimes) {
		this.leaveTimes = leaveTimes;
	}

	public int getSignInTimes() {
		return signInTimes;
	}

	public void setSignInTimes(int signInTimes) {
		this.signInTimes = signInTimes;
	}
	
	public Integer getCourseType() {
		return courseType;
	}

	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}
 
	public String getCourseLables() {
		return courseLables;
	}

	public void setCourseLables(String courseLables) {
		this.courseLables = courseLables;
	}

	public String getStartTimeString() {
		if(startTime!=null){
			String startTimeString = DateUtil.formateDateToString(startTime,DateUtil.DATE_FROMAT_PATTERN_TWO);
			return startTimeString;
		}else{
			return null;
		}
	}

	public void setStartTimeString(String startTimeString) {
		if(StringUtils.isNotEmpty(startTimeString)){
			Date date = null;
			try {
				if (startTimeString.contains("Z")) {
					startTimeString = startTimeString.replace("Z", " UTC");
					date = DateUtil.formateStringToDate(startTimeString, "yyyy-MM-dd'T'HH:mm:ss.SSS Z");
				} else {
					date = DateUtil.formateStringToDate(startTimeString, DateUtil.DATE_FROMAT_PATTERN_TWO);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			this.setStartTime(date);
		}
	}

	public String getEndTimeString() {
		if(endTime!=null){
			String endTimeString = DateUtil.formateDateToString(endTime,DateUtil.DATE_FROMAT_PATTERN_TWO);
			return endTimeString;
		}else{
			return null;
		}
	}

	public void setEndTimeString(String endTimeString) {
		if(StringUtils.isNotEmpty(endTimeString)){
			Date date = null;
			try {
				if (endTimeString.contains("Z")) {
					endTimeString = endTimeString.replace("Z", " UTC");
					date = DateUtil.formateStringToDate(endTimeString, "yyyy-MM-dd'T'HH:mm:ss.SSS Z");
				} else {
					date = DateUtil.formateStringToDate(endTimeString, DateUtil.DATE_FROMAT_PATTERN_TWO);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			this.setEndTime(date);
		}
	}


	public Date getScheduleStartTime() {
		return scheduleStartTime;
	}

	public void setScheduleStartTime(Date scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}

	public Date getScheduleEndTime() {
		return scheduleEndTime;
	}

	public void setScheduleEndTime(Date scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}
}