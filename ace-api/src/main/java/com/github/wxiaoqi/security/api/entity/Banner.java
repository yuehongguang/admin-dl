package com.github.wxiaoqi.security.api.entity;

import javax.persistence.*;

@Table(name = "igrowth_banner")
public class Banner {
    @Id
    private Long id;

    private String bannertype;

    private Long bannerorder;

    private String imgurl;

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
     * @return bannertype
     */
    public String getBannertype() {
        return bannertype;
    }

    /**
     * @param bannertype
     */
    public void setBannertype(String bannertype) {
        this.bannertype = bannertype;
    }

    /**
     * @return bannerorder
     */
    public Long getBannerorder() {
        return bannerorder;
    }

    /**
     * @param bannerorder
     */
    public void setBannerorder(Long bannerorder) {
        this.bannerorder = bannerorder;
    }

    /**
     * @return imgurl
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * @param imgurl
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}