package com.github.wxiaoqi.security.api.entity;

import javax.persistence.*;

@Table(name = "bravo_sys_media")
public class Media {
    @Id
    private Long id;

    @Column(name = "media_file_name")
    private String mediaFileName;//等同于微信返回的mediaId,如果存储在本地,就是文件名

    @Column(name = "media_name")
    private String mediaName;

    @Column(name = "media_path")
    private String mediaPath;//如果上传到本地服务器时使用,存储路径

    @Column(name = "media_size")
    private Long mediaSize;//文件大小,目前为预留字段

    @Column(name = "media_type")
    private Integer mediaType;

    @Column(name = "media_url")
    private String mediaUrl;//完整访问url,默认为oss访问地址

    private String thumbnail;//缩略图

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return media_file_name
     */
    public String getMediaFileName() {
        return mediaFileName;
    }

    /**
     * @param mediaFileName
     */
    public void setMediaFileName(String mediaFileName) {
        this.mediaFileName = mediaFileName;
    }

    /**
     * @return media_name
     */
    public String getMediaName() {
        return mediaName;
    }

    /**
     * @param mediaName
     */
    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    /**
     * @return media_path
     */
    public String getMediaPath() {
        return mediaPath;
    }

    /**
     * @param mediaPath
     */
    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    /**
     * @return media_size
     */
    public Long getMediaSize() {
        return mediaSize;
    }

    /**
     * @param mediaSize
     */
    public void setMediaSize(Long mediaSize) {
        this.mediaSize = mediaSize;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * @return media_url
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * @param mediaUrl
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    /**
     * @return thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}