package com.github.wxiaoqi.security.api.vo;

import java.util.Date;

/**
 * ClassName: Teacher <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年11月21日 上午9:56:45 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
public class TeacherVo {

	private Long id;

	private String teacherName;

	private String phone;

	private Date createTime;

	private Long userId;

	private Integer oTStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getoTStatus() {
		return oTStatus;
	}

	public void setoTStatus(Integer oTStatus) {
		this.oTStatus = oTStatus;
	}

}