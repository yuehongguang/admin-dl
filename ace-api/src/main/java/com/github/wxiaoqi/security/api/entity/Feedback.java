package com.github.wxiaoqi.security.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
 * ClassName: Feedback <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2017年10月27日 下午2:31:35 <br/>  
 *  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8  
 */
@Table(name = "igrowth_feedback")
public class Feedback {
    @Id
    private Long id;
    
    /**
     * 机构id
     */
    @Column(name = "org_id")
    private Long orgId;
    
    /**
     * 我的课程id
     */
    @Column(name = "m_lesson_id")
    private Long mLessonId;
    
    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 反馈标题
     */
    @Column(name = "feedback_title")
    private String feedbackTitle;

    /**
     * 反馈内容
     */
    @Column(name = "feedback_content")
    private String feedbackContent;

    /**
     * 反馈状态 0未回复 1已回复
     */
    @Column(name = "feedback_status")
    private Integer feedbackStatus;
    
    /**
     * 反馈类型 1、系统问题 2、课程问题 3、学习问题  4、反馈机构
     */
    @Column(name = "feedback_type")
    private Integer feedbackType;

    /**
     * 反馈时间
     */
    @Column(name = "feedback_time")
    private Date feedbackTime;

    /**
     * 回复
     */
    @Column(name = "feedback_relpy")
    private String feedbackRelpy;

    /**
     * 账户id
     */
    @Column(name = "account_id")
    private Long accountId;
    
    /**
     * 孩子id
     */
    @Column(name = "child_id")
    private Long childId;
    
    /**
     * 孩子姓名
     */
    @Column(name = "child_name")
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
     * @return feedback_title
     */
    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    /**
     * @param feedbackTitle
     */
    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    /**
     * @return feedback_content
     */
    public String getFeedbackContent() {
        return feedbackContent;
    }

    /**
     * @param feedbackContent
     */
    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    /**
     * @return feedback_status
     */
    public Integer getFeedbackStatus() {
        return feedbackStatus;
    }
    
    
    /**
     * @return feedback_type
     */
    public Integer getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(Integer feedbackType) {
		this.feedbackType = feedbackType;
	}

	/**
     * @param feedbackStatus
     */
    public void setFeedbackStatus(Integer feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    /**
     * @return feedback_time
     */
    public Date getFeedbackTime() {
        return feedbackTime;
    }

    /**
     * @param feedbackTime
     */
    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    /**
     * @return feedback_relpy
     */
    public String getFeedbackRelpy() {
        return feedbackRelpy;
    }

    /**
     * @param feedbackRelpy
     */
    public void setFeedbackRelpy(String feedbackRelpy) {
        this.feedbackRelpy = feedbackRelpy;
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
	 * mLessonId.  
	 */
	public Long getmLessonId() {
		return mLessonId;
	}

	/**  
	 * mLessonId.  
	 */
	public void setmLessonId(Long mLessonId) {
		this.mLessonId = mLessonId;
	}

	/**  
	 * childId.  
	 *  
	 * @return  the childId  
	 * @since   JDK 1.8  
	 */
	public Long getChildId() {
		return childId;
	}

	/**  
	 * childId.  
	 *  
	 * @param   childId    the childId to set  
	 * @since   JDK 1.8  
	 */
	public void setChildId(Long childId) {
		this.childId = childId;
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

	/**  
	 * phone.  
	 *  
	 * @return  the phone  
	 * @since   JDK 1.8  
	 */
	public String getPhone() {
		return phone;
	}

	/**  
	 * phone.  
	 *  
	 * @param   phone    the phone to set  
	 * @since   JDK 1.8  
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Feedback(String feedbackContent, String feedbackRelpy) {
		this.feedbackContent = feedbackContent;
		this.feedbackRelpy = feedbackRelpy;
	}

	public Feedback() {
	}
}