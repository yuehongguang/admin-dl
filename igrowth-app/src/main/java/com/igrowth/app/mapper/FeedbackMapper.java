package com.igrowth.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Feedback;
import tk.mybatis.mapper.common.Mapper;

public interface FeedbackMapper extends Mapper<Feedback> {

	Feedback findByMlessonIdAndChildId(@Param("mLessonId") Long mLessonId,@Param("childId") Long childId);
}