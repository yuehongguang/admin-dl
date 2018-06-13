package com.igrowth.app.mapper;

import com.github.wxiaoqi.security.api.entity.OrgClassify;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrgClassifyMapper extends Mapper<OrgClassify> {

    List<OrgClassify> getParent();

    List<OrgClassify> getList(Long pid);

}