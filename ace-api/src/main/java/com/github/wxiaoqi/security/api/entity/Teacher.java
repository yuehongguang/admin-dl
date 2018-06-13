package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * ClassName: Teacher <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2017年11月21日 上午9:56:45 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Table(name = "igrowth_teacher")
public class Teacher {
	
    @Id
    private Long id;

    @Column(name = "teacher_name")
    private String teacherName;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "create_time")
    private Date createTime;
    
    @Column(name = "user_id")
    private Long userId;

	/**  
	 * id.  
	 *  
	 * @return  the id  
	 * @since   JDK 1.8  
	 */
	public Long getId() {
		return id;
	}

	/**  
	 * id.  
	 *  
	 * @param   id    the id to set  
	 * @since   JDK 1.8  
	 */
	public void setId(Long id) {
		this.id = id;
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

	/**  
	 * createTime.  
	 *  
	 * @return  the createTime  
	 * @since   JDK 1.8  
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * createTime.  
	 *  
	 * @param   createTime    the createTime to set  
	 * @since   JDK 1.8  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**  
	 * userId.  
	 *  
	 * @return  the userId  
	 * @since   JDK 1.8  
	 */
	public Long getUserId() {
		return userId;
	}

	/**  
	 * userId.  
	 *  
	 * @param   userId    the userId to set  
	 * @since   JDK 1.8  
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
  
}