package com.github.wxiaoqi.security.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.wxiaoqi.security.api.vo.OrgClassifyVo;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Table(name = "igrowth_course")
public class Course {
	@Id
	private Long id;

	@Column(name = "course_content")
	private String courseContent;

	@Column(name = "course_date")
	private String courseDate;

	@JsonIgnore
	@Column(name = "course_lables")
	private String courseLables;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "course_num")
	private Integer courseNum;
	
	/**
	 * 0系统课程 1预约课程
	 */
	@Column(name = "course_type")
	private Integer courseType;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "end_time")
	private Date endTime;
	
	@JsonIgnore
	@Column(name = "course_tabs")
	private String courseTabs;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "org_name")
	private String orgName;
	
	@Column(name = "course_status")
	private Integer courseStatus;

	
	/**
	 * 课程介绍
	 */
	@Column(name = "course_itro")
	private String courseItro;

	@Column(name = "card_id")
	private Long cardId;

	@Column(name = "card_name")
	private String cardName;

	@Column(name = "teacher_id")
    private Long teacherId;
    
	@Column(name = "teacher_name")
    private String teacherName;
	
	@Column(name = "course_max_student")
	private Integer courseMaxStudent;

	@Column(name = "org_classify_id")
	private Long orgClassifyId;

	@Transient
	private Long [] orgClassifyIds;
	
	@Transient
	private String[] courseLable;
	
	@Transient
	private String[] courseTab;
	
	@Transient
	private String[] lessons;

	@Transient
	private Integer fav;
	
	@Transient
	private String orgTel;
	
	@Transient
	private List<OrgClassifyVo> lessonJson;


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
	 * @return course_content
	 */
	public String getCourseContent() {
		return courseContent;
	}

	/**
	 * @param courseContent
	 */
	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	/**
	 * @return course_date
	 */
	public String getCourseDate() {
		return courseDate;
	}

	/**
	 * @param courseDate
	 */
	public void setCourseDate(String courseDate) {
		this.courseDate = courseDate;
	}

	/**
	 * @return course_lables
	 */
	public String getCourseLables() {
		return courseLables;
	}

	/**
	 * @param courseLables
	 */
	public void setCourseLables(String courseLables) {
		this.courseLables = courseLables;
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
	 * @return course_num
	 */
	public Integer getCourseNum() {
		return courseNum;
	}

	/**
	 * @param courseNum
	 */
	public void setCourseNum(Integer courseNum) {
		this.courseNum = courseNum;
	}

	/**  
	 * courseType.  
	 *  
	 * @return  the courseType  
	 * @since   JDK 1.8  
	 */
	public Integer getCourseType() {
		return courseType;
	}

	/**  
	 * courseType.  
	 *  
	 * @param   courseType    the courseType to set  
	 * @since   JDK 1.8  
	 */
	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}

	/**  
	 * startTime.  
	 */
	public Date getStartTime() {

		return startTime;
	}

	/**  
	 * startTime.  
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**  
	 * endTime.  
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**  
	 * endTime.  
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return org_id
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String[] getCourseLable() {
		if(StringUtils.isNotEmpty(courseLables)){
			return courseLables.split(",");
		}
		return new String[]{""};
	}

	public void setCourseLable(String[] courseLable) {
		this.courseLable = courseLable;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCourseTabs() {
		return courseTabs;
	}

	public void setCourseTabs(String courseTabs) {
		this.courseTabs = courseTabs;
	}
	
	/**  
	 * lessons.  
	 *  
	 * @return  the lessons  
	 * @since   JDK 1.8  
	 */
	public String[] getLessons() {
		return lessons;
	}

	/**  
	 * lessons.  
	 *  
	 * @param   lessons    the lessons to set  
	 * @since   JDK 1.8  
	 */
	public void setLessons(String[] lessons) {
		this.lessons = lessons;
	}

	public String[] getCourseTab() {
		if(StringUtils.isNotEmpty(courseTabs)){
			return courseTabs.split(",");
		}else{
			return null;
		}
	}

	public void setCourseTab(String[] courseTab) {
		this.courseTab = courseTab;
	}

	/**  
	 * fav.  
	 *  
	 * @return  the fav  
	 * @since   JDK 1.8  
	 */
	public Integer getFav() {
		return fav;
	}

	/**  
	 * fav.  
	 *  
	 * @param   fav    the fav to set  
	 * @since   JDK 1.8  
	 */
	public void setFav(Integer fav) {
		this.fav = fav;
	}

	/**  
	 * orgTel.  
	 *  
	 * @return  the orgTel  
	 * @since   JDK 1.8  
	 */
	public String getOrgTel() {
		return orgTel;
	}

	/**  
	 * orgTel.  
	 *  
	 * @param   orgTel    the orgTel to set  
	 * @since   JDK 1.8  
	 */
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}

	/**  
	 * courseItro.  
	 *  
	 * @return  the courseItro  
	 * @since   JDK 1.8  
	 */
	public String getCourseItro() {
		return courseItro;
	}

	/**  
	 * courseItro.  
	 *  
	 * @param   courseItro    the courseItro to set  
	 * @since   JDK 1.8  
	 */
	public void setCourseItro(String courseItro) {
		this.courseItro = courseItro;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	/**
	 * teacherId.  
	 *  
	 * @return  the teacherId  
	 * @since   JDK 1.8  
	 */
	public Long getTeacherId() {
		return teacherId;
	}

	/**  
	 * teacherId.  
	 *  
	 * @param   teacherId    the teacherId to set  
	 * @since   JDK 1.8  
	 */
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	/**  
	 * teacherName.  
	 * @return  the teacherName  
	 * @since   JDK 1.8  
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**  
	 * teacherName.  
	 * @param   teacherName    the teacherName to set  
	 * @since   JDK 1.8  
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**  
	 * courseStatus.  
	 *  
	 * @return  the courseStatus  
	 * @since   JDK 1.8  
	 */
	public Integer getCourseStatus() {
		return courseStatus;
	}

	/**  
	 * courseStatus.  
	 *  
	 * @param   courseStatus    the courseStatus to set  
	 * @since   JDK 1.8  
	 */
	public void setCourseStatus(Integer courseStatus) {
		this.courseStatus = courseStatus;
	}

	/**  
	 * lessonJson.  
	 *  
	 * @return  the lessonJson  
	 * @since   JDK 1.8  
	 */
	public List<OrgClassifyVo> getLessonJson() {
		return lessonJson;
	}

	/**  
	 * lessonJson.  
	 *  
	 * @param   lessonJson    the lessonJson to set  
	 * @since   JDK 1.8  
	 */
	public void setLessonJson(List<OrgClassifyVo> lessonJson) {
		this.lessonJson = lessonJson;
	}

	/**  
	 * courseMaxStudent.  
	 *  
	 * @return  the courseMaxStudent  
	 * @since   JDK 1.8  
	 */
	public Integer getCourseMaxStudent() {
		return courseMaxStudent;
	}

	/**  
	 * courseMaxStudent.  
	 *  
	 * @param   courseMaxStudent    the courseMaxStudent to set  
	 * @since   JDK 1.8  
	 */
	public void setCourseMaxStudent(Integer courseMaxStudent) {
		this.courseMaxStudent = courseMaxStudent;
	}

	public Long getOrgClassifyId() {
		return orgClassifyId;
	}

	public void setOrgClassifyId(Long orgClassifyId) {
		this.orgClassifyId = orgClassifyId;
	}

	public Long[] getOrgClassifyIds() {
		return orgClassifyIds;
	}

	public void setOrgClassifyIds(Long[] orgClassifyIds) {
		this.orgClassifyIds = orgClassifyIds;
	}
}