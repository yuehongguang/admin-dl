package com.igrowth.app.vo;


import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.Article;
import com.github.wxiaoqi.security.api.entity.Comment;

import java.util.List;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/10/2315:12
 *    desc    : 输入描述
 * </pre>
 */
public class CommentVO extends Comment {

    private Article article;

    /*回复信息*/
    private List<CommentVO> replyComment; // 评论回复信息
    private Account commentAccount;// 评论者信息
    private Account replyAccount; // 回复评论的人

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    public List<CommentVO> getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(List<CommentVO> replyComment) {
        this.replyComment = replyComment;
    }

    public Account getCommentAccount() {
        return commentAccount;
    }

    public void setCommentAccount(Account commentAccount) {
        this.commentAccount = commentAccount;
    }

    public Account getReplyAccount() {
        return replyAccount;
    }

    public void setReplyAccount(Account replyAccount) {
        this.replyAccount = replyAccount;
    }



}
