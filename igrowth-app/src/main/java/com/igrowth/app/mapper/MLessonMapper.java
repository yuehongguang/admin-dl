package com.igrowth.app.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.MLesson;

import tk.mybatis.mapper.common.Mapper;

public interface MLessonMapper extends Mapper<MLesson> {
	
	public List<MLesson> findMLessonByDate(@Param("childId") Long childId, Date monday, Date sunnday,@Param("courseStatuss") List<Integer> courseStatuss);

	public List<MLesson> findMLessonByParam(@Param("orgId") int orgId,@Param("childId") Long childId,Date date);
	
	public List<Long> findMLessonIdByDateAndCourseId(@Param("courseId") Long courseId, @Param("childId") Long childId, @Param("date")Date date);
	
	public Long insertMLessonAndGetId(MLesson mLesson);
	
	public Long findMaxMlessonId();
}