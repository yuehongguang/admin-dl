package com.igrowth.app.mapper;


import com.github.wxiaoqi.security.api.entity.Media;

import tk.mybatis.mapper.common.Mapper;

public interface MediaMapper extends Mapper<Media> {

    /*int insertMediaAndGetId(@Param("mediaFileName") String mediaFileName,
                            @Param("mediaPath") String mediaPath,
                            @Param("mediaSize") String mediaSize,
                            @Param("mediaType") String mediaType,
                            @Param("mediaUrl") String mediaUrl,
                            @Param("thumbnail") String thumbnail);*/

    Long insertMediaAndGetId(Media media);
}