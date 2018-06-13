package com.github.wxiaoqi.security.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.github.wxiaoqi.security.api.util.DateUtil;

/**  
 * ClassName: MLesson <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2017年11月22日 下午5:34:53 <br/>  
 *  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8  
 */
@Table(name = "igrowth_m_lesson")
public class MLesson {
    @Id
    private Long id;
    
    @Column(name = "account_id")
    private Long accountId;
    
    @Column(name = "child_id")
    private Long childId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "lesson_id")
    private Long lessonId;
    
    /**
     * 教师点评状态 0未点评 1已点评
     */
    @Column(name = "comment_status")
    private Integer commentStatus;
    
    @Column(name = "lesson_name")
    private String lessonName;
  
    @Column(name = "lesson_audio")
    private String lessonAudio;

    @Column(name = "lesson_video")
    private String lessonVideo;

    @Column(name = "lesson_behavior")
    private String lessonBehavior;

    @Column(name = "lesson_imgs")
    private String lessonImgs;
    
    @Column(name = "lesson_point")
    private String lessonPoint;
    
    @Column(name = "lesson_homework")
    private String lessonHomework;
    
    /**-1已经取消预约课程 0 未签到  1 已签到 2 请假  3预  4自选课程 */
    @Column(name = "lesson_status")
    private Integer lessonStatus;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "org_id")
    private Long orgId;
    
    @Column(name = "org_name")
    private String orgName;
    
    @Column(name = "note")
    private String note;
    
    /**
     * 详情附加字段
     */
	@Transient
	private String[] courseLables;
	
	@Transient
	private String orgTel;
	
	@Transient
    private Integer teacherId;
    
	@Transient
    private String teacherName;
	
	@Transient
	private String[] lessonImg;
	
	@Transient
	private String orgAddress;
	
    @Transient
    private String feedBackId;
    
    @Transient
	private int tlong;
    
    @Transient
    private String childName;
	
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
     * @return account_id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    /**  
	 * childId.  
	 */
	public Long getChildId() {
		return childId;
	}

	/**  
	 * childId.  
	 */
	public void setChildId(Long childId) {
		this.childId = childId;
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
     * @return lesson_id
     */
    public Long getLessonId() {
        return lessonId;
    }

    /**
     * @param lessonId
     */
    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    /**  
	 * lessonName.  
	 *  
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
     * @return lesson_audio
     */
    public String getLessonAudio() {
        return lessonAudio;
    }

    /**
     * @param lessonAudio
     */
    public void setLessonAudio(String lessonAudio) {
        this.lessonAudio = lessonAudio;
    }

    /**
     * @return lesson_video
     */
    public String getLessonVideo() {
        return lessonVideo;
    }

    /**
     * @param lessonVideo
     */
    public void setLessonVideo(String lessonVideo) {
        this.lessonVideo = lessonVideo;
    }

    /**
     * @return lesson_behavior
     */
    public String getLessonBehavior() {
        return lessonBehavior;
    }

    /**
     * @param lessonBehavior
     */
    public void setLessonBehavior(String lessonBehavior) {
        this.lessonBehavior = lessonBehavior;
    }

    /**
     * @return lesson_imgs
     */
    public String getLessonImgs() {
        return lessonImgs;
    }

    /**
     * @param lessonImgs
     */
    public void setLessonImgs(String lessonImgs) {
        this.lessonImgs = lessonImgs;
    }

    /**
     * @return lesson_satatus
     */
    public Integer getLessonStatus() {
        return lessonStatus;
    }

    /**
     * @param lessonStatus
     */
    public void setLessonStatus(Integer lessonStatus) {
        this.lessonStatus = lessonStatus;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
 
	public String getOrgName() {
		return orgName;
	}
 
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**  
	 * courseLables.  
	 *  
	 * @return  the courseLables  
	 * @since   JDK 1.8  
	 */
	public String[] getCourseLables() {
		return courseLables;
	}

	/**  
	 * courseLables.  
	 *  
	 * @param   courseLables    the courseLables to set  
	 * @since   JDK 1.8  
	 */
	public void setCourseLables(String[] courseLables) {
		this.courseLables = courseLables;
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
	 * teacherId.  
	 *  
	 * @return  the teacherId  
	 * @since   JDK 1.8  
	 */
	public Integer getTeacherId() {
		return teacherId;
	}

	/**  
	 * teacherId.  
	 *  
	 * @param   teacherId    the teacherId to set  
	 * @since   JDK 1.8  
	 */
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	/**  
	 * teacherName.  
	 *  
	 * @return  the teacherName  
	 * @since   JDK 1.8  
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**  
	 * teacherName.  
	 *  
	 * @param   teacherName    the teacherName to set  
	 * @since   JDK 1.8  
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**  
	 * lessonPoint.  
	 *  
	 * @return  the lessonPoint  
	 * @since   JDK 1.8  
	 */
	public String getLessonPoint() {
		return lessonPoint;
	}

	/**  
	 * lessonPoint.  
	 *  
	 * @param   lessonPoint    the lessonPoint to set  
	 * @since   JDK 1.8  
	 */
	public void setLessonPoint(String lessonPoint) {
		this.lessonPoint = lessonPoint;
	}

	/**  
	 * lessonHomework.  
	 *  
	 * @return  the lessonHomework  
	 * @since   JDK 1.8  
	 */
	public String getLessonHomework() {
		return lessonHomework;
	}

	/**  
	 * lessonHomework.  
	 *  
	 * @param   lessonHomework    the lessonHomework to set  
	 * @since   JDK 1.8  
	 */
	public void setLessonHomework(String lessonHomework) {
		this.lessonHomework = lessonHomework;
	}

	/**  
	 * lessonImg.  
	 *  
	 * @return  the lessonImg  
	 * @since   JDK 1.8  
	 */
	public String[] getLessonImg() {
		if(StringUtils.isNotEmpty(lessonImgs)){
			return lessonImgs.split(",");
		}else{
			return new String[]{};
		}
		
	}

	/**  
	 * lessonImg.  
	 *  
	 * @param   lessonImg    the lessonImg to set  
	 * @since   JDK 1.8  
	 */
	public void setLessonImg(String[] lessonImg) {
		this.lessonImg = lessonImg;
	}

	/**  
	 * orgAddress.  
	 *  
	 * @return  the orgAddress  
	 * @since   JDK 1.8  
	 */
	public String getOrgAddress() {
		return orgAddress;
	}

	/**  
	 * orgAddress.  
	 *  
	 * @param   orgAddress    the orgAddress to set  
	 * @since   JDK 1.8  
	 */
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	/**  
	 * lessonNote.  
	 *  
	 * @return  the lessonNote  
	 * @since   JDK 1.8  
	 */
	public String getNote() {
		return note;
	}

	/**  
	 * lessonNote.  
	 *  
	 * @param   lessonNote    the lessonNote to set  
	 * @since   JDK 1.8  
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**  
	 * feedBack.  
	 *  
	 * @return  the feedBack  
	 * @since   JDK 1.8  
	 */
	public String getFeedBackId() {
		return feedBackId;
	}

	/**  
	 * feedBack.  
	 *  
	 * @param   feedBack    the feedBack to set  
	 * @since   JDK 1.8  
	 */
	public void setFeedBackId(String feedBackId) {
		this.feedBackId = feedBackId;
	}

	/**  
	 * commentStatus.  
	 *  
	 * @return  the commentStatus  
	 * @since   JDK 1.8  
	 */
	public Integer getCommentStatus() {
		return commentStatus;
	}

	/**  
	 * commentStatus.  
	 *  
	 * @param   commentStatus    the commentStatus to set  
	 * @since   JDK 1.8  
	 */
	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}

	/**  
	 * tlong.  
	 *  
	 * @return  the tlong  
	 * @since   JDK 1.8  
	 */
	public int getTlong() {
		return DateUtil.hoursBetween(startTime, endTime);
	}

	/**  
	 * tlong.  
	 *  
	 * @param   tlong    the tlong to set  
	 * @since   JDK 1.8  
	 */
	public void setTlong(int tlong) {
		this.tlong = tlong;
	}

	/**  
	 * childName.  
	 *  
	 * @return  the childName  
	 * @since   JDK 1.8  
	 */
	public String getChildName() {
		return childName;
	}

	/**  
	 * childName.  
	 *  
	 * @param   childName    the childName to set  
	 * @since   JDK 1.8  
	 */
	public void setChildName(String childName) {
		this.childName = childName;
	}
}