package com.github.wxiaoqi.security.api.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * ClassName: Article <br/>  
 * Function: 文章 <br/>  
 * date: 2017年10月26日 下午3:52:21 <br/>  
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Table(name = "igrowth_article")
public class Article {
    @Id
    private Long id;

    /**
     * 文章标题
     */
    @Column(name = "article_title")
    private String articleTitle;

    /**
     * 文章内容
     */
    @Column(name = "article_content")
    private String articleContent;

    /**
     * 点赞数量
     */
    @Transient
    private Integer upNum;

    /**
     * 点踩数量
     */
    @Transient
    private Integer downNum;

    /**
     * 文章配图
     */
    @Column(name = "article_pics")
    private String articlePics;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 状态  0显示，1不显示
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 可否点赞或踩
     */
    @Column(name = "can_up_down")
    private Integer canUpDown;

    /**
     * 圈子Id
     */
    @Column(name = "circle_id")
    private Long circleId;

    /**
     * 发帖人Id
     */
    @Column(name = "account_id")
    private Long accountId;

    /**
     * 用户点赞或踩
     * 0  点赞
     * 1  点踩
     * -1 既没点赞也没点踩
     */
    @Transient
    private Integer userSetUpOrDown;

    /**
     * 文章评论数
     */
    @Transient
    private Long commentNum;

    @Transient
    private String[] articlePicList = new String[20];

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
     * @return article_title
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * @param articleTitle
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * @return article_content_id
     */
    public String getArticleContent() {
        return articleContent;
    }

    /**
     * @param articleContent
     */
    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    /**
     * @return up_num
     */
    @Transient
    public Integer getUpNum() {
        return upNum;
    }

    /**
     * @param upNum
     */
    public void setUpNum(Integer upNum) {
        this.upNum = upNum;
    }

    /**
     * @return down_num
     */
    @Transient
    public Integer getDownNum() {
        return downNum;
    }

    /**
     * @param downNum
     */
    public void setDownNum(Integer downNum) {
        this.downNum = downNum;
    }

    /**
     * @return article_pics
     */
    @Transient
    public String getArticlePics() {
        return articlePics;
    }

    /**
     * @param articlePics
     */
    public void setArticlePics(String articlePics) {
        this.articlePics = articlePics;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return circle_id
     */
    public Long getCircleId() {
        return circleId;
    }

    /**
     * @param circleId
     */
    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    /**
     * @param updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
     * @param status
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return account_id
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getCanUpDown() {
        return canUpDown;
    }

    public void setCanUpDown(Integer canUpDown) {
        this.canUpDown = canUpDown;
    }

    @Transient
    public Integer getUserSetUpOrDown() {
        return userSetUpOrDown;
    }

    public void setUserSetUpOrDown(Integer userSetUpOrDown) {
        this.userSetUpOrDown = userSetUpOrDown;
    }

    @Transient
    public Long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    public String[] getArticlePicList() {
        if(StringUtils.isNotEmpty(articlePics)){
            String[] pcis = articlePics.split(",");
            return pcis;
        }else{
            return null;
        }
    }

    public void setArticlePicList(String[] articlePicList) {
        this.articlePicList = articlePicList;
    }
}