package com.github.wxiaoqi.security.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Feedback;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-12-19 15:39:18
 */
public interface FeedbackMapper extends Mapper<Feedback> {

	List<Feedback> findByOrgIdAndPage(@Param("orgId") Long orgId, @Param("limit") int limit, @Param("page") int page, @Param("name") String name);

	Long findCountByOrgId(@Param("orgId") Long orgId, @Param("name") String name);

}
