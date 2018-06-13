package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "igrowth_app_info")
public class AppInfo {
	@Id
	private Long id;
	
	@Column(name = "app_version")
	private String appVersion;

	@Column(name = "app_company_info")
	private String appCompanyInfo;

	@Column(name = "app_contact_us")
	private String appContactUs;

	@Column(name = "app_copyright_info")
	private String appCopyrightInfo;
	
	@Column(name = "app_privacy_info")
	private String appPrivacyInfo;
	
	@Column(name = "app_whats_new")
	private String appWhatsNew;

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
	 * appVersion.  
	 *  
	 * @return  the appVersion  
	 * @since   JDK 1.8  
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/**  
	 * appVersion.  
	 *  
	 * @param   appVersion    the appVersion to set  
	 * @since   JDK 1.8  
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/**  
	 * appCompanyInfo.  
	 *  
	 * @return  the appCompanyInfo  
	 * @since   JDK 1.8  
	 */
	public String getAppCompanyInfo() {
		return appCompanyInfo;
	}

	/**  
	 * appCompanyInfo.  
	 *  
	 * @param   appCompanyInfo    the appCompanyInfo to set  
	 * @since   JDK 1.8  
	 */
	public void setAppCompanyInfo(String appCompanyInfo) {
		this.appCompanyInfo = appCompanyInfo;
	}

	/**  
	 * appContactUs.  
	 *  
	 * @return  the appContactUs  
	 * @since   JDK 1.8  
	 */
	public String getAppContactUs() {
		return appContactUs;
	}

	/**  
	 * appContactUs.  
	 *  
	 * @param   appContactUs    the appContactUs to set  
	 * @since   JDK 1.8  
	 */
	public void setAppContactUs(String appContactUs) {
		this.appContactUs = appContactUs;
	}

	/**  
	 * appCopyrightInfo.  
	 *  
	 * @return  the appCopyrightInfo  
	 * @since   JDK 1.8  
	 */
	public String getAppCopyrightInfo() {
		return appCopyrightInfo;
	}
	
	/**  
	 * appPrivacyInfo.  
	 *  
	 * @return  the appPrivacyInfo  
	 * @since   JDK 1.8  
	 */
	public String getAppPrivacyInfo() {
		return appPrivacyInfo;
	}

	/**  
	 * appPrivacyInfo.  
	 *  
	 * @param   appPrivacyInfo    the appPrivacyInfo to set  
	 * @since   JDK 1.8  
	 */
	public void setAppPrivacyInfo(String appPrivacyInfo) {
		this.appPrivacyInfo = appPrivacyInfo;
	}

	/**  
	 * appCopyrightInfo.  
	 *  
	 * @param   appCopyrightInfo    the appCopyrightInfo to set  
	 * @since   JDK 1.8  
	 */
	public void setAppCopyrightInfo(String appCopyrightInfo) {
		this.appCopyrightInfo = appCopyrightInfo;
	}

	/**  
	 * appWhatsNew.  
	 *  
	 * @return  the appWhatsNew  
	 * @since   JDK 1.8  
	 */
	public String getAppWhatsNew() {
		return appWhatsNew;
	}

	/**  
	 * appWhatsNew.  
	 *  
	 * @param   appWhatsNew    the appWhatsNew to set  
	 * @since   JDK 1.8  
	 */
	public void setAppWhatsNew(String appWhatsNew) {
		this.appWhatsNew = appWhatsNew;
	}		
	
}