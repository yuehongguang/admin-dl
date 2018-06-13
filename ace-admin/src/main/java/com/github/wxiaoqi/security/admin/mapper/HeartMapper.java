package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.api.entity.Heart;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface HeartMapper extends Mapper<Heart> {

    public Integer selectSumHeartNumByChild(@Param("childId")Long childId);

    public List<Heart> getHeartByChildIdAndDate(@Param("childId")Long childId,
                                                @Param("heartType")Integer heartType,
                                                @Param("date")Date date);
}