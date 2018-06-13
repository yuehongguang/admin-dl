package com.github.wxiaoqi.security.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Child;
import tk.mybatis.mapper.common.Mapper;

public interface ChildMapper extends Mapper<Child> {

	List<Child> findChildByAccountId(@Param("accountId") Long accountId);
}