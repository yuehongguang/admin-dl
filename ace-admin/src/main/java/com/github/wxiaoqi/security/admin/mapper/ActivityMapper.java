package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.api.entity.Activity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-12-20 14:18:45
 */
public interface ActivityMapper extends Mapper<Activity> {

	Long findCountByOrgId(@Param("orgId")Long orgId);

	List<Activity> findByOrgIdAndPage(@Param("orgId")Long orgId, @Param("limit")int limit, @Param("page")int page,@Param("activityShort") String activityShort);
	
}
