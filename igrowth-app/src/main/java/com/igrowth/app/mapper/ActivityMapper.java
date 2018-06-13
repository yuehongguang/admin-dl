package com.igrowth.app.mapper;

import com.github.wxiaoqi.security.api.entity.Activity;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ActivityMapper extends Mapper<Activity> {
	/**
     * 根据orgId查询机构下所有的活动
     * @param userId
     * @return
     */
    public List<Activity> selectActivityByOrgId (@Param("orgId") Long orgId);
}