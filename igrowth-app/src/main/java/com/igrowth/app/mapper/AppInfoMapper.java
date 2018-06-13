package com.igrowth.app.mapper;

import com.github.wxiaoqi.security.api.entity.AppInfo;

import tk.mybatis.mapper.common.Mapper;

public interface AppInfoMapper extends Mapper<AppInfo> {
	
	public AppInfo findLatestAppInfo();
}