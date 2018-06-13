package com.igrowth.app.mapper;

import java.util.List;

import com.github.wxiaoqi.security.api.entity.OrgClassify;
import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Org;

import tk.mybatis.mapper.common.Mapper;

public interface OrgMapper extends Mapper<Org> {

	List<String> getOrgCourseLables(@Param("orgId") Long orgId);
}