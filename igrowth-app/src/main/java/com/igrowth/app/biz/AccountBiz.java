package com.igrowth.app.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.Child;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.CommUtils;
import com.github.wxiaoqi.security.common.util.jwt.JWTInfo;
import com.igrowth.app.mapper.AccountMapper;
import com.igrowth.app.utils.JwtTokenUtil;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/10/2315:33
 *    desc    : 输入描述
 * </pre>
 */
@Service
public class AccountBiz extends BaseBiz<AccountMapper, Account> {
	
	@Autowired
	private ChildBiz childBiz;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	 
	@Autowired
	private IdentifyCodeBiz iu;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public static final String HEAD_IMG = "http://static.chinacnid.com/media/file/b4e8a8de-cc6e-4d14-9c91-7015afd09c86.jpg";

	public Account findAccountById(Long Id) {
		Account account = mapper.queryById(Id);
		return account;
	}

	public Account findByAccountName(String accountName) {
		Account account = new Account();
		account.setCellphone(accountName);
		account = mapper.selectOne(account);
		return account;
	}

	public Map<String, Object> register(String phone, String identifyCode, String password) {
		String msg = "";
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = null;
		if (CommUtils.checkPhone(phone)) {
			account = findByAccountName(phone);
			if (account != null) {
				if(account.getAccountStatus() == 0){
					msg = "手机号码已注册.";
					account = null;
				}else{
					if (!iu.identifyCode(phone, identifyCode)) {
						msg = "手机验证码错误.";
						account = null;
					}else{
						String token = registerAndGetToken(account, phone, password,true);
						map.put("token", token);
						result = true;
					}
				}
			} else {
				if (!iu.identifyCode(phone, identifyCode)) {
					msg = "手机验证码错误.";
					account = null;
				} else {
					account = new Account();
					String token = registerAndGetToken(account, phone, password,false);
					map.put("token", token);
					result = true;
				}
			}
		} else {
			msg = "手机号码格式错误.";
			account = null;
		}
		map.put("msg", msg);
		map.put("account", account);
		map.put("result", result);
		return map;
	}
	
	
	private String registerAndGetToken(Account account, String phone, String password, boolean hasRegister) {
		account.setAccountName(phone.substring(0, 3)+"***"+phone.substring(7, 11));
		account.setAccountStatus(0);
		account.setAccountPassword(encoder.encode(password));
		account.setCellphone(phone);
		account.setCreateTime(new Date());
		if(hasRegister){
			mapper.updateByPrimaryKey(account);
		}else{
			mapper.insertAccountAndGetId(account);
			account = findByAccountName(phone);
			Child child = new Child();
			child.setChildBirth(new Date());
			child.setChildSex(1);
			child.setChildName("尚未设置");
			child.setChildNickname("尚未设置");
			child.setParentId(account.getId());
			child.setChildPic(HEAD_IMG);
			child.setCreateTime(new Date());
			childBiz.saveChild(child);
			child = childBiz.findChildByAccountId(account.getId()).get(0);
			account.setCurrentCid(child.getId());
			updateById(account);
		}
		
		String token = "";
		try {
			token = jwtTokenUtil.generateToken(new JWTInfo(account.getCellphone(), account.getId() + "", account.getAccountName()));
		} catch (Exception e) {
		}
		return token;
	}

	public boolean changePwd(String phone, String password) {
		try{
			Account account = findByCellphone(phone);
			if(account==null){
				return false;
			}
			account.setAccountPassword(encoder.encode(password));
			updateById(account);
			return true;
		}catch (Exception e){
			return false;
		}
		
	}
	
	private Account findByCellphone(String phone) {
		Account account = new Account();
		account.setCellphone(phone);
		account = mapper.selectOne(account);
		return account;
	}

	public boolean identifyCode(String phone,String identifyCode) {
		return iu.identifyCode(phone, identifyCode);
	}

	public boolean createParent(Long currentAccountId, String accountPic, String accountRole, String accountName) {
		try{
			Account account  = findAccountById(currentAccountId);
			if(StringUtils.isNotEmpty(accountName)){
				account.setAccountName(accountName);
			}
			if(StringUtils.isNotEmpty(accountRole)){
				account.setAccountRole(accountRole);
			}
			if(StringUtils.isNotEmpty(accountPic)){
				account.setAccountPic(accountPic);
			}
			updateById(account);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

	public void changePassword(Account account, String newPassword) {
		account.setAccountPassword(encoder.encode(newPassword));
		updateById(account);
	}
}
