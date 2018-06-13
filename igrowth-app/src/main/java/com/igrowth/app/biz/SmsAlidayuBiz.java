package com.igrowth.app.biz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Component
public class SmsAlidayuBiz {

	@Value("${sms.appKey:23294811}")
	String smsAppKey;

	@Value("${sms.appSecret:3c339d431ecdbccdb68c6a28ab7964c5}")
	String smsAppSecret;

	@Value("${sms.appName:兑悦平台}")
	String smsAppName;

	// 使用https地址
	// https://eco.taobao.com/router/rest
	// http://gw.api.taobao.com/router/rest
	@Value("${sms.serviceUrl:http://gw.api.taobao.com/router/rest}")
	String smsServiceUrl;

	private AlibabaAliqinFcSmsNumSendResponse sendSms(String cellPhone,
			String smsSignName, String smsTemplateCode, String smsPrarm)
			throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(smsServiceUrl, smsAppKey,
				smsAppSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(smsSignName);
		req.setSmsParamString(smsPrarm);
		req.setRecNum(cellPhone);
		req.setSmsTemplateCode(smsTemplateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
		return rsp;
	}

	private String getSmsParam(SmsSignName type, String code) {
		String smsParam = "";
		switch (type) {
		case MODIFICATION_VERIFY:
			smsParam = "{\"code\":\"" + code + "\",\"product\":\"" + smsAppName
					+ "\"}";
			break;
		case LOGIN_VERIFY:
			smsParam = "{\"code\":\"" + code + "\",\"product\":\"" + smsAppName
					+ "\"}";
			break;
		case REGISTER_VERIFY:
			smsParam = "{\"code\":\"" + code + "\",\"product\":\"" + smsAppName
					+ "\"}";
			break;
		case IDENTITY_VERIFY:
			smsParam = "{\"code\":\"" + code + "\",\"product\":\"" + smsAppName
					+ "\"}";
			break;
		default:
			break;
		}
		return smsParam;
	}

	private String getActivitySmsParam(String code, String activityName) {
		return "{\"code\":\"" + code + "\",\"product\":\"" + smsAppName
				+ "\",\"item\":\"" + activityName + "\"}";
	}
	private String getActivitySmsParam(String code, String activityName,
			String smsAppName) {
		return "{\"code\":\"" + code + "\",\"product\":\"" + smsAppName
				+ "\",\"item\":\"" + activityName + "\"}";
	}
	private String getSmsSignName(SmsSignName type) {
		String smsSignName = "";
		switch (type) {
		case ACTIVITY_VERIFY:
			smsSignName = "活动验证";
			break;
		case MODIFICATION_VERIFY:
			smsSignName = "变更验证";
			break;
		case LOGIN_VERIFY:
			smsSignName = "登录验证";
			break;
		case REGISTER_VERIFY:
			smsSignName = "注册验证";
			break;
		case IDENTITY_VERIFY:
			smsSignName = "身份验证";
			break;
		default:
			break;
		}
		return smsSignName;
	}

	private String getSmsTemplateCode(SmsSignName type) {
		String smsParam = "";
		switch (type) {
		case ACTIVITY_VERIFY:
			smsParam = "SMS_3991075";
			break;
		case MODIFICATION_VERIFY:
			smsParam = "SMS_3991073";
			break;
		case LOGIN_VERIFY:
			smsParam = "SMS_3991078";
			break;
		case REGISTER_VERIFY:
			smsParam = "SMS_3991076";
			break;
		case IDENTITY_VERIFY:
			smsParam = "SMS_3991079";
			break;
		default:
			break;
		}
		return smsParam;
	}

	/**
	 * 验证码${code}，您正在参加${product}的${item}活动，请确认系本人申请。
	 * 
	 * @param cellPhone
	 * @param code
	 * @param activityName
	 * @return
	 * @throws ApiException
	 */
	public boolean sendActivityVerifySms(String cellPhone, String code,
			String activityName) throws ApiException {
		return sendSms(cellPhone, getSmsSignName(SmsSignName.ACTIVITY_VERIFY),
				getSmsTemplateCode(SmsSignName.ACTIVITY_VERIFY),
				getActivitySmsParam(code, activityName)).isSuccess();
	}

	/**
	 * 验证码${code}，您正在参加${product}的${item}活动，请确认系本人申请。
	/**
	 * @param cellPhone
	 * @param code
	 * @param activityName	活动名
	 * @param smsAppName	指定的短信应用名称
	 * @return
	 * @throws ApiException
	 */
	public boolean sendActivityVerifySms(String cellPhone, String code,
			String activityName, String smsAppName) throws ApiException {
		return sendSms(cellPhone, getSmsSignName(SmsSignName.ACTIVITY_VERIFY),
				getSmsTemplateCode(SmsSignName.ACTIVITY_VERIFY),
				getActivitySmsParam(code, activityName, smsAppName)).isSuccess();
	}
	
	/**
	 * 验证码${code}，您正在尝试变更${product}重要信息，请妥善保管账户信息。
	 * 
	 * @param cellPhone
	 * @param code
	 * @return
	 * @throws ApiException
	 */
	public boolean sendModificationVerifySms(String cellPhone, String code)
			throws ApiException {
		return sendSms(cellPhone,
				getSmsSignName(SmsSignName.MODIFICATION_VERIFY),
				getSmsTemplateCode(SmsSignName.MODIFICATION_VERIFY),
				getSmsParam(SmsSignName.MODIFICATION_VERIFY, code)).isSuccess();
	}

	/**
	 * 验证码${code}，您正在登录${product}，若非本人操作，请勿泄露。
	 * 
	 * @param cellPhone
	 * @param code
	 * @return
	 * @throws ApiException
	 */
	public boolean sendLoginVerifySMS(String cellPhone, String code)
			throws ApiException {
		return sendSms(cellPhone, getSmsSignName(SmsSignName.LOGIN_VERIFY),
				getSmsTemplateCode(SmsSignName.LOGIN_VERIFY),
				getSmsParam(SmsSignName.LOGIN_VERIFY, code)).isSuccess();
	}

	/**
	 * 验证码${code}，您正在注册成为${product}用户，感谢您的支持！
	 * 
	 * @param cellPhone
	 * @param code
	 * @return
	 * @throws ApiException
	 */
	public boolean sendRegisterVerify(String cellPhone, String code)
			throws ApiException {
		return sendSms(cellPhone, getSmsSignName(SmsSignName.REGISTER_VERIFY),
				getSmsTemplateCode(SmsSignName.REGISTER_VERIFY),
				getSmsParam(SmsSignName.REGISTER_VERIFY, code)).isSuccess();
	}

	/**
	 * 验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！
	 * 
	 * @param cellPhone
	 * @param code
	 * @return
	 * @throws ApiException
	 */
	public boolean sendIdentityVerify(String cellPhone, String code)
			throws ApiException {
		return sendSms(cellPhone, getSmsSignName(SmsSignName.IDENTITY_VERIFY),
				getSmsTemplateCode(SmsSignName.IDENTITY_VERIFY),
				getSmsParam(SmsSignName.IDENTITY_VERIFY, code)).isSuccess();
	}
}

enum SmsSignName {
	ACTIVITY_VERIFY, MODIFICATION_VERIFY, LOGIN_VERIFY, REGISTER_VERIFY, IDENTITY_VERIFY
}
