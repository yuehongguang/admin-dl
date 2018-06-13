package com.github.wxiaoqi.security.admin.mapper;

import java.util.List;

import com.github.wxiaoqi.security.api.entity.OrgClassify;
import tk.mybatis.mapper.common.Mapper;

public interface OrgClassifyMapper extends Mapper<OrgClassify> {

	List<OrgClassify> getByPid(Long pId);

	List<OrgClassify> getParent();

	List<OrgClassify> getList(Long pid);
}