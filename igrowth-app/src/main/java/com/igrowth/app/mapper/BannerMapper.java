package com.igrowth.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Banner;

import tk.mybatis.mapper.common.Mapper;

public interface BannerMapper extends Mapper<Banner> {

	List<Banner> selectByBannertype(@Param("typeName") String typeName);
}