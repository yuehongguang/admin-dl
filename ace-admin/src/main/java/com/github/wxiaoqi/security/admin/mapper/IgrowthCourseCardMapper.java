package com.github.wxiaoqi.security.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.IgrowthCourseCard;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-11-29 16:13:04
 */
public interface IgrowthCourseCardMapper extends Mapper<IgrowthCourseCard> {
	public List<IgrowthCourseCard> selectActiveCourseByOrgId(@Param("orgId") Long orgId);
}
