package com.github.wxiaoqi.security.api.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ClassName: Org <br/>
 * Function: 机构实体类. <br/>
 * date: 2017年10月26日 上午11:55:32 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Table(name = "igrowth_org")
public class Org implements Comparable<Org>, Serializable {
	 
	private static final long serialVersionUID = -1277535754105789811L;

	@Id
	private Long id;

	@Column(name = "org_latitude")
	private Double latitude;

	@Column(name = "org_longitude")
	private Double longitude;

	@Column(name = "org_address")
	private String orgAddress;

	@Column(name = "org_lable")
	private String orgLable;
	
	@JsonIgnore
	@Column(name = "course_lable")
	private String courseLable;

	@Column(name = "org_name")
	private String orgName;

	@Column(name = "org_pic")
	private String orgPic;
	
	@Column(name = "org_cover_img")
	private String orgCoverImg;

	@Column(name = "org_tel")
	private String orgTel;

	//关联课程分类 用英文,隔开
	@Column(name = "org_classify_id")
	private String orgClassifyId;
	
	@Transient
	private Long [] orgClassifyIds;

	@Column(name = "org_type")
	private Integer orgType;

	@Column(name = "org_url")
	private String orgUrl;

	@Column(name = "org_detail")
	private String orgDetail;

	@Column(name="base_user_id")
	private Integer baseUserId;
	
	@Column(name="org_qrcode_url")
	private String orgQrcodeUrl;

	@Transient
	private String orgClassifyName;

	@Transient
	private Double distance;

	@Transient
	private String[] orgLables;

	@Transient
	private String[] courseLables;

	@Transient
	private List<Banner> orgPics;
	
	@Transient
	private List<Activity> activities;
	
	@Transient
	private List<Course> course;
	
	@Transient
	private Integer fav;
	
	@Transient
	private String geoHash;

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
	 * @return  the orgCoverImg  
	 * @since   JDK 1.8  
	 */
	public String getOrgCoverImg() {
		return orgCoverImg;
	}

	/**  
	 * orgCoverImg.  
	 *  
	 * @param   orgCoverImg    the orgCoverImg to set  
	 * @since   JDK 1.8  
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

	public Double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
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

	@Override
	public int compareTo(Org o) {
		return distance.compareTo(o.getDistance());
	}

	/**
	 * @param orgLables
	 */
	public String[] getOrgLables() {
		if(StringUtils.isNotEmpty(orgLable)){
			return orgLable.split(",");
		}else{
			return new String[]{};
		}
		
	}

	public void setOrgLables(String[] orgLables) {
		this.orgLables = orgLables;
	}

	/**
	 * @param courseLables
	 */
	public String[] getCourseLables() {
		return courseLables;
	}

	public void setCourseLables(String[] courseLables) {
		this.courseLables = courseLables;
	}

	public String getOrgClassifyName() {
		return orgClassifyName;
	}

	public void setOrgClassifyName(String orgClassifyName) {
		this.orgClassifyName = orgClassifyName;
	}

	public List<Banner> getOrgPics() {
		return orgPics;
	}

	public void setOrgPics(List<Banner> orgPics) {
		this.orgPics = orgPics;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	public Integer getFav() {
		return fav;
	}

	public void setFav(Integer fav) {
		this.fav = fav;
	}
 
	public Long[] getOrgClassifyIds() {
		return orgClassifyIds;
	}
 
	public void setOrgClassifyIds(Long[] orgClassifyIds) {
		this.orgClassifyIds = orgClassifyIds;
	}

	public String getOrgQrcodeUrl() {
		return orgQrcodeUrl;
	}
 
	public void setOrgQrcodeUrl(String orgQrcodeUrl) {
		this.orgQrcodeUrl = orgQrcodeUrl;
	}

	public String getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

	@Override
	public String toString() {
		return "Org{" +
				"id=" + id +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", orgAddress='" + orgAddress + '\'' +
				", orgLable='" + orgLable + '\'' +
				", courseLable='" + courseLable + '\'' +
				", orgName='" + orgName + '\'' +
				", orgPic='" + orgPic + '\'' +
				", orgCoverImg='" + orgCoverImg + '\'' +
				", orgTel='" + orgTel + '\'' +
				", orgClassifyId='" + orgClassifyId + '\'' +
				", orgClassifyIds=" + Arrays.toString(orgClassifyIds) +
				", orgType=" + orgType +
				", orgUrl='" + orgUrl + '\'' +
				", orgDetail='" + orgDetail + '\'' +
				", baseUserId=" + baseUserId +
				", orgQrcodeUrl='" + orgQrcodeUrl + '\'' +
				", orgClassifyName='" + orgClassifyName + '\'' +
				", distance=" + distance +
				", orgLables=" + Arrays.toString(orgLables) +
				", courseLables=" + Arrays.toString(courseLables) +
				", orgPics=" + orgPics +
				", activities=" + activities +
				", course=" + course +
				", fav=" + fav +
				", geoHash='" + geoHash + '\'' +
				'}';
	}
}
