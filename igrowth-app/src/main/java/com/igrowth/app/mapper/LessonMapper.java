package com.igrowth.app.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Lesson;
import com.igrowth.app.vo.LessonVO;

import tk.mybatis.mapper.common.Mapper;

public interface LessonMapper extends Mapper<Lesson> {

	public List<Lesson> findByCourseId(@Param("courseId") Long courseId);
	public List<Lesson> findByCourseIdAndStatus(@Param("courseId") Long courseId,@Param("lessonStatus") Integer lessonStatus);
	public List<Lesson> findMLessonByCourseIdAndDate(@Param("courseId") Long courseId,@Param("date") Date date);
	public List<LessonVO> findLessonByDateAndCourseId(@Param("courseId") Long courseId,@Param("date") String date);
	public LessonVO findLessonByLessonId(@Param("lessonId") Long lessonId);
}