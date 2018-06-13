package com.github.wxiaoqi.security.api.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "igrowth_lesson")
public class Lesson {
    @Id
    private Long id;
    
    @Column(name = "org_id")
    private Long orgId;
    
    @Column(name = "org_name")
    private String orgName;
    
    @Column(name = "lesson_name")
    private String lessonName;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "teacher_name")
    private String teacherName;

    @Column(name = "home_work")
    private String homeWork;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Transient
    private Date startDate;//上课日期
    @Transient
    private String courseTime;//上课时间
    @Transient
    private Boolean canUpdate;
    
    /**
     * 0未开始 1结束
     */
    @Column(name = "lesson_status")
    private Integer lessonStatus;
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
	 * lessonName.  
	 */
	public String getLessonName() {
		return lessonName;
	}

	/**  
	 * lessonName.  
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	
	/**  
	 * orgId.  
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**  
	 * orgId.  
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**  
	 * orgName.  
	 */
	public String getOrgName() {
		return orgName;
	}

	/**  
	 * orgName.  
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
     * @return teacher_id
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * @param teacherId
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * @return teacher_name
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * @param teacherName
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * @return home_work
     */
    public String getHomeWork() {
        return homeWork;
    }

    /**
     * @param homeWork
     */
    public void setHomeWork(String homeWork) {
        this.homeWork = homeWork;
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

    public Integer getLessonStatus() {
        return lessonStatus;
    }

    public void setLessonStatus(Integer lessonStatus) {
        this.lessonStatus = lessonStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }
}