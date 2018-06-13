package com.github.wxiaoqi.security.api.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
public class OrgVo {
	private Long id;

	private Double latitude;

	private Double longitude;

	private String orgAddress;

	private String orgLable;

	private String courseLable;

	private String orgName;

	private String orgPic;

	private String orgCoverImg;

	private String orgTel;

	private String orgClassifyId;

	private Integer orgType;

	private String orgUrl;

	private String orgDetail;

	private Integer baseUserId;

	private String orgQrcodeUrl;

	private Long otId;

	private Integer oTStatus;

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
	 * @return latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return org_address
	 */
	public String getOrgAddress() {
		return orgAddress;
	}

	/**
	 * @param orgAddress
	 */
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	/**
	 * @return org_lable
	 */
	public String getOrgLable() {
		return orgLable;
	}

	/**
	 * @param orgLable
	 */
	public void setOrgLable(String orgLable) {
		this.orgLable = orgLable;
	}

	/**
	 * courseLable.
	 */
	public String getCourseLable() {
		return courseLable;
	}

	/**
	 * courseLable.
	 */
	public void setCourseLable(String courseLable) {
		this.courseLable = courseLable;
	}

	/**
	 * @return org_name
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return org_pic
	 */
	public String getOrgPic() {
		return orgPic;
	}

	/**
	 * @param orgPic
	 */
	public void setOrgPic(String orgPic) {
		this.orgPic = orgPic;
	}

	/**
	 * orgCoverImg.
	 * 
	 * @return the orgCoverImg
	 * @since JDK 1.8
	 */
	public String getOrgCoverImg() {
		return orgCoverImg;
	}

	/**
	 * orgCoverImg.
	 * 
	 * @param orgCoverImg
	 *            the orgCoverImg to set
	 * @since JDK 1.8
	 */
	public void setOrgCoverImg(String orgCoverImg) {
		this.orgCoverImg = orgCoverImg;
	}

	/**
	 * @return org_tel
	 */
	public String getOrgTel() {
		return orgTel;
	}

	/**
	 * @param orgTel
	 */
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}

	/**
	 * @return org_classify_id
	 */
	public String getOrgClassifyId() {
		return orgClassifyId;
	}

	/**
	 * @param orgClassifyId
	 */
	public void setOrgClassifyId(String orgClassifyId) {
		this.orgClassifyId = orgClassifyId;
	}

	public Integer getOrgType() {
		return orgType;
	}

	/**
	 * @param orgType
	 */
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public String getOrgUrl() {
		return orgUrl;
	}

	/**
	 * @param ogrUrl
	 */
	public void setOrgUrl(String orgUrl) {
		this.orgUrl = orgUrl;
	}

	public String getOrgDetail() {
		return orgDetail;
	}

	/**
	 * @param orgDetail
	 */
	public void setOrgDetail(String orgDetail) {
		this.orgDetail = orgDetail;
	}

	public Integer getBaseUserId() {
		return baseUserId;
	}

	public void setBaseUserId(Integer baseUserId) {
		this.baseUserId = baseUserId;
	}

	public String[] getOrgLables() {
		if (StringUtils.isNotEmpty(orgLable)) {
			return orgLable.split(",");
		} else {
			return new String[] {};
		}
	}

	public String getOrgQrcodeUrl() {
		return orgQrcodeUrl;
	}

	public void setOrgQrcodeUrl(String orgQrcodeUrl) {
		this.orgQrcodeUrl = orgQrcodeUrl;
	}

	public Long getOtId() {
		return otId;
	}

	public void setOtId(Long otId) {
		this.otId = otId;
	}

	public Integer getoTStatus() {
		return oTStatus;
	}

	public void setoTStatus(Integer oTStatus) {
		this.oTStatus = oTStatus;
	}

}
