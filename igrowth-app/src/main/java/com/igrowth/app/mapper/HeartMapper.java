package com.igrowth.app.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Heart;

import tk.mybatis.mapper.common.Mapper;

public interface HeartMapper extends Mapper<Heart> {
	
	 public Integer selectSumHeartNumByChild(@Param("childId")Long childId);
	 
	 public List<Heart> getHeartByChildIdAndDate(@Param("childId")Long childId,
			 									 @Param("heartType")Integer heartType,
			 									 @Param("date")Date date);
}