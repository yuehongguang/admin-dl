package com.github.wxiaoqi.security.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.github.wxiaoqi.security.api.util.DateUtil;


/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-11-29 16:13:04
 */
@Table(name = "igrowth_course_card")
public class IgrowthCourseCard implements Serializable {
	private static final long serialVersionUID = 1L;
	
	    //
    @Id
    private Long id;
	
	    //
    @Column(name = "card_name")
    private String cardName;
	
	    //
    @Column(name = "course_num")
    private Integer courseNum;
	
	    //
    @Column(name = "valid_time_start")
    private Date validTimeStart;
	@Transient
    private String validTimeStartString;

	    //
    @Column(name = "valid_time_end")
    private Date validTimeEnd;
	@Transient
    private String validTimeEndString;
	
	    //
    @Column(name = "price")
    private Double price;
	
	    //
    @Column(name = "inventory")
    private Integer inventory;
	
	    //
    @Column(name = "course_id")
    private Long courseId;
	
	    //
    @Column(name = "status")
    private Integer status;

    @Column(name = "org_id")
	private Integer orgId;

    @Column(name = "org_name")
	private String orgName;

    @Column(name = "course_label")
	private String courseLabel;

    @Column(name = "org_classify_id")
	private Long orgClassifyId;
    
    //课程卡绑定状态 0未绑定 1已绑定
    @Column(name = "bind_status")
    private Integer bindStatus;

    @Column(name = "valid_days")
	private Integer validDays;

	@Transient
	private Long [] orgClassifyIds;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
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
	public void setCourseNum(Integer courseNum) {
		this.courseNum = courseNum;
	}
	/**
	 * 获取：
	 */
	public Integer getCourseNum() {
		return courseNum;
	}
	/**
	 * 设置：
	 */
	public void setValidTimeStart(Date validTimeStart) {
		this.validTimeStart = validTimeStart;
	}
	/**
	 * 获取：
	 */
	public Date getValidTimeStart() {
		return validTimeStart;
	}
	/**
	 * 设置：
	 */
	public void setValidTimeEnd(Date validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
	}
	/**
	 * 获取：
	 */
	public Date getValidTimeEnd() {
		return validTimeEnd;
	}
	/**
	 * 设置：
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * 获取：
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 设置：
	 */
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	/**
	 * 获取：
	 */
	public Integer getInventory() {
		return inventory;
	}
	/**
	 * 设置：
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：
	 */
	public Long getCourseId() {
		return courseId;
	}
	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}

	public String getValidTimeStartString() {
		if(validTimeStart!=null){
			String validTimeStartString = DateUtil.formateDateToString(validTimeStart,DateUtil.DATE_FROMAT_PATTERN_FIVE);
			return validTimeStartString;
		}else{
			return null;
		}

	}

	public void setValidTimeStartString(String validTimeStartString) {
		if(StringUtils.isNotEmpty(validTimeStartString)){
			try{

				validTimeStartString = validTimeStartString.replace("Z", " UTC");
				Date date = DateUtils.parseDate(validTimeStartString,"yyyy-MM-dd'T'HH:mm:ss.SSS Z");
				this.setValidTimeStart(date);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public String getValidTimeEndString() {
		if(validTimeEnd!=null){
			String validTimeEndString = DateUtil.formateDateToString(validTimeEnd,DateUtil.DATE_FROMAT_PATTERN_FIVE);
			return validTimeEndString;
		}else{
			return null;
		}

	}

	public void setValidTimeEndString(String validTimeEndString) {
		if(StringUtils.isNotEmpty(validTimeEndString)){
			try{

				validTimeEndString = validTimeEndString.replace("Z", " UTC");
				Date date = DateUtils.parseDate(validTimeEndString,"yyyy-MM-dd'T'HH:mm:ss.SSS Z");
				this.setValidTimeEnd(date);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCourseLabel() {
		return courseLabel;
	}

	public void setCourseLabel(String courseLabel) {
		this.courseLabel = courseLabel;
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
 
	public Integer getBindStatus() {
		return bindStatus;
	}
 
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}
}
