package com.github.wxiaoqi.security.api.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "igrowth_comment")
public class Comment {
    @Id
    private Long id;

    @Column(name = "account_id")
    private Long accountId;//评论人Id  （parentCommentId>0时表示回复人Id）

    @Column(name = "parent_comment_id")
    private Long parentCommentId;//0表示评论，大于0表示回复的评论id。

    @Column(name = "article_id")
    private Long articleId;//文章Id

    private String content;//评论内容

    @Column(name = "comment_date")
    private Date commentDate;//评论日期

    private Integer state;//状态控制，0显示，1不显示

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

    /**
     * @return parent_comment_id
     */
    public Long getParentCommentId() {
        return parentCommentId;
    }

    /**
     * @param parentCommentId
     */
    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    /**
     * @return article_id
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * @param articleId
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return comment_date
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * @param commentDate
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }
}