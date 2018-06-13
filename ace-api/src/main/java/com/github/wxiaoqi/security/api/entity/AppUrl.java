package com.github.wxiaoqi.security.api.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "igrowth_app_url")
public class AppUrl implements Serializable{
    @Id
    private Long id;

    private String url;

    @Column(name = "need_login")
    private Integer needLogin;

    @Column(name = "url_code")
    private String urlCode;


    @Column(name = "method")
    private String method;
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
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return need_login
     */
    public Integer getNeedLogin() {
        return needLogin;
    }

    /**
     * @param needLogin
     */
    public void setNeedLogin(Integer needLogin) {
        this.needLogin = needLogin;
    }

    /**
     * @return url_code
     */
    public String getUrlCode() {
        return urlCode;
    }

    /**
     * @param urlCode
     */
    public void setUrlCode(String urlCode) {
        this.urlCode = urlCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}