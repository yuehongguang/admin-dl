package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.api.entity.Teacher;
import com.github.wxiaoqi.security.api.vo.TeacherVo;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TeacherMapper extends Mapper<Teacher> {
	
	public List<TeacherVo> findTeacherByOrgIdAndPage(@Param("orgId")long orgId, @Param("limit")int limit, @Param("page")int page,@Param("teacherName")String teacherName,@Param("phone")String phone);

	public Long findTeacherCountByOrgId(@Param("orgId")long orgId,@Param("teacherName")String teacherName,@Param("phone")String phone);

	public Teacher findTeacherByPhone(@Param("phone")String phone);

	public List<Teacher> findTeacherByOrgId(@Param("orgId")long orgId);

	public Teacher findTeacherById(@Param("teacherId") long teacherId);
}