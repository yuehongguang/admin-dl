package com.github.wxiaoqi.security.auth.util.user;

import java.io.Serializable;

public class PhoneAuthenticationRequest implements Serializable {

	private static final long serialVersionUID = -8445943548965154778L;

	private String phone;
	private String identifyCode;

	public PhoneAuthenticationRequest(String username, String password) {
		this.phone = username;
		this.identifyCode = password;
	}

	public PhoneAuthenticationRequest() {
	}

	/**
	 * phone.
	 * 
	 * @return the phone
	 * @since JDK 1.8
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * phone.
	 * 
	 * @param phone
	 *            the phone to set
	 * @since JDK 1.8
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * identifyCode.
	 * 
	 * @return the identifyCode
	 * @since JDK 1.8
	 */
	public String getIdentifyCode() {
		return identifyCode;
	}

	/**
	 * identifyCode.
	 * 
	 * @param identifyCode
	 *            the identifyCode to set
	 * @since JDK 1.8
	 */
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

}
