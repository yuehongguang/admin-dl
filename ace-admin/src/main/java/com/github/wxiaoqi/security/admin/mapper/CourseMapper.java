package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.api.entity.Course;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CourseMapper extends Mapper<Course> {
	
	/**
     * 根据orgId查询机构的所有课程列表
     * @param userId
     * @return
     */
    public List<Course> selectCourseByOrgId (@Param("userId") Integer userId);

    public Long insertCourse(Course c);

	public List<Course> findCourseByTeacherId(@Param("teacherId")Long teacherId);

	public List<Course> selectActiveCourseByOrgId(@Param("orgId")Long orgId,@Param("courseType") Integer courseType);
	
	public List<Long> findOrgClassifyIdsByOrgId(@Param("orgId")Long orgId);
}