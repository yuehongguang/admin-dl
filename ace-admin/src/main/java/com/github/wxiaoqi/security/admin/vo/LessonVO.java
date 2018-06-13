package com.github.wxiaoqi.security.admin.vo;

import javax.persistence.Transient;
import java.util.Date;

public class LessonVO {
	
    private Long id;
    
    private Long orgId;
    
    private String orgName;
    
    private String lessonName;

    private Long courseId;

    private String courseName;

    private Integer teacherId;

    private String teacherName;

    private String homeWork;

    private Date startTime;

    private Date endTime;
    
    @Transient
    private Integer subnum;

    @Transient
    private Date startDate;//上课日期
    @Transient
    private String courseTime;//上课时间
    @Transient
    private Integer lessonSubStatus;//当前课程预约状态 0可预约 1已预约 2已约满
    
    /**
     * 0未开始 1结束
     */
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

	public Integer getLessonSubStatus() {
		return lessonSubStatus;
	}
 
	public void setLessonSubStatus(Integer lessonSubStatus) {
		this.lessonSubStatus = lessonSubStatus;
	}

	public Integer getSubnum() {
		return subnum;
	}

	public void setSubnum(Integer subnum) {
		this.subnum = subnum;
	}
}