package com.igrowth.app.vo;


import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.Article;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/10/2611:43
 *    desc    : 输入描述
 * </pre>
 */
public class ArticleVO extends Article {

    private Account account;
    private Integer totalComment;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }
}
