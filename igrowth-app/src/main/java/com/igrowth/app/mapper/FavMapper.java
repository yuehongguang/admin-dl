package com.igrowth.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Fav;

import tk.mybatis.mapper.common.Mapper;

public interface FavMapper extends Mapper<Fav> {
	
	public Fav findFavByAccountIdAndOrgId(@Param("accountId") Long accountId,@Param("orgId") Long orgId,@Param("favType")Integer favType);

}