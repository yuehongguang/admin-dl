package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.api.entity.Account;

import tk.mybatis.mapper.common.Mapper;

public interface AccountMapper extends Mapper<Account> {

	Long insertAccountAndGetId(Account account);

}