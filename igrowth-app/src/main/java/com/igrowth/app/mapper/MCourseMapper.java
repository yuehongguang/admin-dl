package com.igrowth.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.MCourse;

import tk.mybatis.mapper.common.Mapper;

public interface MCourseMapper extends Mapper<MCourse> {
	
	public List<MCourse> findByCourseName(@Param("courseName") String courseName);

	public MCourse findByCourseIdAndChildId(@Param("courseId")Long courseId, @Param("childId") Long childId);
}