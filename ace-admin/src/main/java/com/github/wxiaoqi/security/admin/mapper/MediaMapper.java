package com.github.wxiaoqi.security.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.github.wxiaoqi.security.admin.entity.Media;
import tk.mybatis.mapper.common.Mapper;

public interface MediaMapper extends Mapper<Media> {

    /*int insertMediaAndGetId(@Param("mediaFileName") String mediaFileName,
                            @Param("mediaPath") String mediaPath,
                            @Param("mediaSize") String mediaSize,
                            @Param("mediaType") String mediaType,
                            @Param("mediaUrl") String mediaUrl,
                            @Param("thumbnail") String thumbnail);*/

    Long insertMediaAndGetId(Media media);

	Media selectByUrl(@Param("mediaUrl") String url);
}