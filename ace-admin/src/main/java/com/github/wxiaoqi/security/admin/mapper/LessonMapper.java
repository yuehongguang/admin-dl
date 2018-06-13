package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.vo.LessonVO;
import com.github.wxiaoqi.security.api.entity.Lesson;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LessonMapper extends Mapper<Lesson> {
	public List<Lesson> findByCourseIdAndStatus(@Param("courseId") Long courseId, @Param("lessonStatus") Integer lessonStatus);
	List<Lesson> findByCourseId(Long courseId);

	public LessonVO findLessonByLessonId(@Param("lessonId") Long lessonId);
}