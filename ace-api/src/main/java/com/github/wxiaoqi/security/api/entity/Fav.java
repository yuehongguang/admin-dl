package com.github.wxiaoqi.security.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "igrowth_fav")
public class Fav {
	@Id
	private Long id;
	/**
	 * 1、机构 2、课程
	 */
	@Column(name = "fav_type")
	private Integer favType;

	@Column(name = "model_id")
	private Long modelId;

	@Column(name = "account_id")
	private Long accountId;

	@Column(name = "fav_time")
	private Date favTime;

	@Transient
	private Org org;

	@Transient
	private Course course;

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
	 * @return fav_type
	 */
	public Integer getFavType() {
		return favType;
	}

	/**
	 * @param favType
	 */
	public void setFavType(Integer favType) {
		this.favType = favType;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
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
	 * @return fav_time
	 */
	public Date getFavTime() {
		return favTime;
	}

	/**
	 * @param favTime
	 */
	public void setFavTime(Date favTime) {
		this.favTime = favTime;
	}

	/**
	 * org.
	 * 
	 * @return the org
	 * @since JDK 1.8
	 */
	public Org getOrg() {
		return org;
	}

	/**
	 * org.
	 * 
	 * @param org
	 *            the org to set
	 * @since JDK 1.8
	 */
	public void setOrg(Org org) {
		this.org = org;
	}

	/**
	 * course.
	 * 
	 * @return the course
	 * @since JDK 1.8
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * course.
	 * 
	 * @param course
	 *            the course to set
	 * @since JDK 1.8
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

}