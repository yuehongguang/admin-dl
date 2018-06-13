package com.github.wxiaoqi.security.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.api.vo.OrgVo;

import tk.mybatis.mapper.common.Mapper;

public interface OrgMapper extends Mapper<Org> {

	List<Org> selectQrCodeUrlEmpty();

	List<OrgVo> findOrgByTeacherIdAndPage(@Param("teacherId")Long teacherId, @Param("limit")int limit, @Param("page")int page);

	Long findOrgByTeacherId(@Param("teacherId")Long teacherId);

}