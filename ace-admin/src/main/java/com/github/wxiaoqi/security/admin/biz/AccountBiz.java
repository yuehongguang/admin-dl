package com.github.wxiaoqi.security.admin.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.admin.mapper.AccountMapper;
import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.Child;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.CommUtils;

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
	
	public static final String HEAD_IMG = "http://static.chinacnid.com/media/file/b4e8a8de-cc6e-4d14-9c91-7015afd09c86.jpg";
	
	public Account findAccountById(Long Id) {
		return selectById(Id);
	}
	
	public Map<String, Object> register(String phone, String childName) {
		String msg = "";
		boolean result = false;
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = null;
		if (CommUtils.checkPhone(phone)) {
			account = findByAccountName(phone);
			if (account != null && account.getAccountStatus() == 0) {
				msg = "手机号码已注册.";
				account = null;
			} else {
				account = new Account();
				account.setAccountName(phone);
				account.setAccountStatus(-1);
				account.setAccountPassword("");
				account.setCellphone(phone);
				account.setCreateTime(new Date());
				mapper.insertAccountAndGetId(account);
				account = findByAccountName(phone);
				
				Child child = new Child();
				child.setChildBirth(new Date());
				child.setChildSex(1);
				child.setChildName(childName);
				child.setParentId(account.getId());
				child.setCreateTime(new Date());
				child.setChildNickname("尚未设置");
				child.setChildName("尚未设置");
				child.setChildPic(HEAD_IMG);
				childBiz.saveChild(child);
				
				child = childBiz.findChildByAccountId(account.getId()).get(0);
				account.setCurrentCid(child.getId());
				updateById(account);
				result = true;
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
	

	public Account findByAccountName(String accountName) {
		Account account = new Account();
		account.setCellphone(accountName);
		account = mapper.selectOne(account);
		return account;
	}
}
