package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.api.entity.MCourse;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MCourseMapper extends Mapper<MCourse> {
	public List<MCourse> selectByOrgIdGroupByChildId(@Param("limit") int limit,
													@Param("page") int page,
													@Param("orgId") long orgId,
													@Param("childId") int childId,
													@Param("parentPhone")String parentPhone,
													@Param("childName") String childName);

	public Long selectByOrgIdGroupByChildIdCount( @Param("orgId") long orgId,
													 @Param("childId") int childId,
													 @Param("parentPhone")String parentPhone,
													 @Param("childName") String childName);

	public List<MCourse> selectByOrgIdAndChildId( @Param("orgId") long orgId,
												  @Param("childId") int childId,
												  @Param("parentPhone")String parentPhone,
												  @Param("childName") String childName);

	public List<MCourse> selectByCourseId(@Param("courseId")Long courseId);

	public MCourse findByCourseIdAndChildId(@Param("courseId")Long courseId, @Param("childId") Long childId);
}