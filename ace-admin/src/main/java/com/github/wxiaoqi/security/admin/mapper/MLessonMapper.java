package com.github.wxiaoqi.security.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.admin.vo.MLessonVo;
import com.github.wxiaoqi.security.api.entity.MLesson;
import com.github.wxiaoqi.security.api.vo.ChildVo;

import tk.mybatis.mapper.common.Mapper;

public interface MLessonMapper extends Mapper<MLesson> {

	List<ChildVo> selectByLessonIdAndCommentStatus(@Param("limit") int limit,@Param("page") int page,@Param("lessonId") Long lessonId, @Param("commentStatus") Integer commentStatus);

	List<MLessonVo> selectByOrgIdAndLessonStatus(@Param("limit") int limit,@Param("page") int page,@Param("orgId") Long orgId, @Param("lessonStatus") Integer lessonStatus, @Param("childName") String childName);

	void updateStatus(@Param("id") Long id,@Param("lessonStatus") Integer lessonStatus);
	
	Integer selectByLessonIdAndCommentStatusCount(@Param("lessonId") Long lessonId, @Param("commentStatus") Integer commentStatus);

	List<MLesson> selectEndMlesson();
	
	List<MLesson> selectEndCommentMlessonByLessonId(@Param("lessonId") Long lessonId, @Param("commentStatus") Integer commentStatus);

	Integer selectByOrgIdAndLessonStatusCount(@Param("orgId") Long lessonId, @Param("lessonStatus") Integer lessonStatus, @Param("childName") String childName);

	Integer selectEndCommentMlessonCountByLessonId(@Param("lessonId") Long lessonId, @Param("commentStatus") Integer commentStatus);
	
	Integer findSumGroupByChild(@Param("courseId") Long courseId);
}