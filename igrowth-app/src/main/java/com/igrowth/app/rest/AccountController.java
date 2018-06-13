package com.igrowth.app.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.Child;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.constant.CommonConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.igrowth.app.biz.AccountBiz;
import com.igrowth.app.biz.ChildBiz;
import com.igrowth.app.biz.FeedbackBiz;
import com.igrowth.app.biz.SmsAlidayuBiz;
import com.taobao.api.ApiException;

/**
 * ClassName: AccountController <br/>
 * 我的接口 date: 2017年11月14日 下午5:32:08 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Controller
@RequestMapping("account")
public class AccountController extends BaseController{

	@Autowired
	private AccountBiz accountBiz;
	
	@Autowired
	private SmsAlidayuBiz msgService;
	
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Autowired
	private FeedbackBiz feedbackBiz;
	
	@Autowired
	private ChildBiz childBiz;

	/**
	 * getMyInfo:(这里用一句话描述这个方法的作用). <br/>
	 * 我的接口
	 * 
	 * @author dingshuyan
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public Account getMyInfo() {
		Long accountId = getCurrentAccountId();
		return accountBiz.findAccountById(accountId);
	}
	
	
	/**
	 * 发送手机验证码
	 * @author dingshuyan  
	 * @param cellPhone
	 * @param req
	 * @param rsp
	 * @throws IOException  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@RequestMapping(value="/sendRandom/{cellPhone}",method = RequestMethod.GET)
	@ResponseBody
	public ObjectRestResponse sendRandom(@PathVariable("cellPhone") String cellPhone) throws IOException {
		String randomkey = String.format(CommonConstants.RANDOMKEY, cellPhone);
		ObjectRestResponse result =  new ObjectRestResponse<>();
		int random = (int) ((Math.random() * 9 + 1) * 100000);
		try {
			msgService.sendLoginVerifySMS(cellPhone, String.valueOf(random));
			ValueOperations<String, String> ops = redisTemplate.opsForValue();
			ops.set(randomkey, String.valueOf(random));
			result.setRel(true);
			result.setMessage("短信发送成功");
		} catch (ApiException e) {
			result.setRel(false);
			result.setMessage("短信发送失败");
		}
		return result;
	}
	
	/**
	 * 用户注册
	 * @author dingshuyan  
	 * @return  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@RequestMapping(value = "/register", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> register(@RequestParam String phone,
										@RequestParam String identifyCode, 
										@RequestParam String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> map = accountBiz.register(phone,identifyCode,password);
		if(!(Boolean)map.get("result")){
			resultMap.put("result", false);
			resultMap.put("msg", map.get("msg"));
		}else{
			resultMap.put("result", true);
			resultMap.put("msg", map.get("msg"));
			resultMap.put("account", map.get("account"));
			resultMap.put("token", map.get("token"));
		}
		return resultMap;
	}
	
	/**
	 * 修改家长信息接口
	 * @author dingshuyan  
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/parent", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> parent(@RequestParam(defaultValue = "") String accountPic,
									  @RequestParam(defaultValue = "") String accountRole, 
									  @RequestParam(defaultValue = "") String accountName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = accountBiz.createParent(getCurrentAccountId(),accountPic,accountRole,accountName);
		if(result){
			resultMap.put("result", true);
			resultMap.put("msg", "创建成功.");
		}else{
			resultMap.put("result", false);
			resultMap.put("msg", "创建家长信息失败.");
		}
		return resultMap;
	}
	
	/**
	 * 忘记密码
	 * @author dingshuyan  
	 * @return  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@RequestMapping(value = "/changepwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changePwd(@RequestParam String password,
										 @RequestParam String phone) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = accountBiz.changePwd(phone,password);
		if(result){
			resultMap.put("result", true);
			resultMap.put("msg", "修改密码成功.");
		}else{
			resultMap.put("result", false);
			resultMap.put("msg", "修改密码失败,请稍后重试.");
		}
		return resultMap;
	}
	
	/**
	 * 校验短信验证码
	 * @author dingshuyan  
	 * @return  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@RequestMapping(value = "/checkcode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkcode(@RequestParam String phone,
									     @RequestParam String identifyCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = accountBiz.identifyCode(phone,identifyCode);
		if(result){
			resultMap.put("result", true);
			resultMap.put("msg", "验证码正确.");
		}else{
			resultMap.put("result", false);
			resultMap.put("msg", "验证码错误.");
		}
		return resultMap;
	}
	
	/**
	 * 用户反馈
	 * @author dingshuyan  
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/feedback", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> feedback(@RequestParam(defaultValue = "") String phone,
									    @RequestParam(defaultValue = "") String name,
									    @RequestParam(defaultValue = "0") Long orgId,
									    @RequestParam(defaultValue = "0") Long mLessonId,
									    @RequestParam String content) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			if(StringUtils.isEmpty(phone)){
				Account account = accountBiz.findAccountById(getCurrentAccountId());
				phone = account.getCellphone();
			}
			if(StringUtils.isEmpty(name)){
				Child child = childBiz.selectById(getCurrentChildId());
				name = child.getChildName();
			}
			boolean result = feedbackBiz.createFeedback(getCurrentChildId(), getCurrentAccountId(), content, content, phone, name, orgId, mLessonId);
			if(result){
				resultMap.put("result", true);
				resultMap.put("msg", "用户反馈成功.");
			}else{
				resultMap.put("result", false);
				resultMap.put("msg", "用户反馈失败,已存在对该课程的反馈.");
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("msg", "用户反馈失败.");
		}
		return resultMap;
	}
	
	/**
	  * 获取当前家长信息
	  * @author dingshuyan  
	  * @param id
	  * @return  
	  * @since JDK 1.8
	 */
	@RequestMapping(value = "curaccount", method = RequestMethod.GET)
	@ResponseBody
	public Account curaccount(){
		return accountBiz.findAccountById(getCurrentAccountId());
	}
	
	
	/**
	 * newphone:新手机号 
	 * @author dingshuyan  
	 * @param newphone
	 * @param identifyCode
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/newphone", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> newphone(@RequestParam String newphone,
									    @RequestParam String identifyCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = accountBiz.identifyCode(newphone,identifyCode);
		if(result){
			Account account = accountBiz.findByAccountName(newphone);
			if (account != null) {
				resultMap.put("result", false);
				resultMap.put("msg", "该手机号已注册.");
			}else{
				account = accountBiz.findAccountById(getCurrentAccountId());
				account.setCellphone(newphone);
				accountBiz.updateById(account);
				resultMap.put("result", true);
				resultMap.put("msg", "修改手机号成功.");
			}
		}else{
			resultMap.put("result", false);
			resultMap.put("msg", "验证码错误.");
		}
		return resultMap;
	}
	
	
	/**
	 * 修改密码
	 * @author dingshuyan  
	 * @param newphone
	 * @param identifyCode
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/changepassword", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> changepassword(@RequestParam String newPassword) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Account account = accountBiz.findAccountById(getCurrentAccountId());
		accountBiz.changePassword(account,newPassword);
		resultMap.put("result", true);
		resultMap.put("msg", "修改密码成功.");
		return resultMap;
	}
 
}
