package com.igrowth.app.vo;

import com.github.wxiaoqi.security.api.entity.Circle;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/10/239:15
 *    desc    : 拼接圈子详情数据（包含多个动态）
 * </pre>
 */
public class CircleArticles {

    private Circle circle;
    private Object  articlePage;

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Object getArticlePage() {
        return articlePage;
    }

    public void setArticlePage(Object articlePage) {
        this.articlePage = articlePage;
    }
}
