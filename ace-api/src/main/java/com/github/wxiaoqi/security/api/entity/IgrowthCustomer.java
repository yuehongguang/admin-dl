package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-12-11 11:35:56
 */
@Table(name = "igrowth_customer")
public class IgrowthCustomer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private Integer id;
	
	    //
    @Column(name = "customer_name")
    private String customerName;
	
	    //
    @Column(name = "cellphoe")
    private String cellphoe;
	
	    //
    @Column(name = "card_name")
    private String cardName;
	
	    //
    @Column(name = "card_id")
    private Integer cardId;
	
	    //
    @Column(name = "course_name")
    private String courseName;
	
	    //
    @Column(name = "course_id")
    private Integer courseId;
	
	    //
    @Column(name = "mlesson_id")
    private Integer mlessonId;
	
	    //
    @Column(name = "child_id")
    private Integer childId;
	
	    //
    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "org_id")
	private Integer orgId;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * 获取：
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * 设置：
	 */
	public void setCellphoe(String cellphoe) {
		this.cellphoe = cellphoe;
	}
	/**
	 * 获取：
	 */
	public String getCellphoe() {
		return cellphoe;
	}
	/**
	 * 设置：
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	/**
	 * 获取：
	 */
	public String getCardName() {
		return cardName;
	}
	/**
	 * 设置：
	 */
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	/**
	 * 获取：
	 */
	public Integer getCardId() {
		return cardId;
	}
	/**
	 * 设置：
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * 获取：
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * 设置：
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：
	 */
	public Integer getCourseId() {
		return courseId;
	}
	/**
	 * 设置：
	 */
	public void setMlessonId(Integer mlessonId) {
		this.mlessonId = mlessonId;
	}
	/**
	 * 获取：
	 */
	public Integer getMlessonId() {
		return mlessonId;
	}
	/**
	 * 设置：
	 */
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	/**
	 * 获取：
	 */
	public Integer getChildId() {
		return childId;
	}
	/**
	 * 设置：
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：
	 */
	public Integer getParentId() {
		return parentId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
}
