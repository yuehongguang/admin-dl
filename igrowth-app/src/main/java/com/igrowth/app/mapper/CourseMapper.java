package com.igrowth.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Course;

import tk.mybatis.mapper.common.Mapper;

public interface CourseMapper extends Mapper<Course> {
	
	/**
     * 根据orgId查询机构的所有课程列表
     * @param userId
     * @return
     */
    public List<Course> selectCourseByOrgId (@Param("orgId") Long orgId);
    
    /**
     * 查询所有课程标签
     * @param userId
     * @return
     */
    public List<String> getAllCourseLables();

    /**
     * 查询当前机构下所有课程标签
     */
	public List<String> findCourseLablesByOrgId(@Param("orgId") Long orgId);

	  /**
     * 查询当前机构下所有课程标签id
     */
	public List<Long> findOrgClassifyIdsByOrgId(Long orgId);
    
}