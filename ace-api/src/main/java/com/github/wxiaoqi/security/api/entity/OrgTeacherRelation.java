package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "igrowth_org_teacher_relation")
public class OrgTeacherRelation {

	@Id
	private Long id;

	@Column(name = "teacher_id")
	private Long teacherId;

	@Column(name = "org_id")
	private Long orgId;

	/**
	 * 0邀请关系 1同意关系 -1拒绝关系
	 */
	@Column(name = "o_t_status")
	private Integer oTStatus;

	/**
	 * teacherId.
	 * 
	 * @return the teacherId
	 * @since JDK 1.8
	 */
	public Long getTeacherId() {
		return teacherId;
	}

	/**
	 * teacherId.
	 * 
	 * @param teacherId
	 *            the teacherId to set
	 * @since JDK 1.8
	 */
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	/**
	 * orgId.
	 * 
	 * @return the orgId
	 * @since JDK 1.8
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * orgId.
	 * 
	 * @param orgId
	 *            the orgId to set
	 * @since JDK 1.8
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * oTStatus.
	 * 
	 * @return the oTStatus
	 * @since JDK 1.8
	 */
	public Integer getoTStatus() {
		return oTStatus;
	}

	/**
	 * oTStatus.
	 * 
	 * @param oTStatus
	 *            the oTStatus to set
	 * @since JDK 1.8
	 */
	public void setoTStatus(Integer oTStatus) {
		this.oTStatus = oTStatus;
	}

	/**
	 * id.
	 * 
	 * @return the id
	 * @since JDK 1.8
	 */
	public Long getId() {
		return id;
	}

	/**
	 * id.
	 * 
	 * @param id
	 *            the id to set
	 * @since JDK 1.8
	 */
	public void setId(Long id) {
		this.id = id;
	}
}