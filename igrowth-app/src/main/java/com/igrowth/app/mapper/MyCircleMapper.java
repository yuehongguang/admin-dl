package com.igrowth.app.mapper;

import com.github.wxiaoqi.security.api.entity.MyCircle;
import com.igrowth.app.vo.MyCircleVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MyCircleMapper extends Mapper<MyCircle> {

    public List<MyCircleVO> findMyCircleByChildId(@Param("childId") Long childId);
}