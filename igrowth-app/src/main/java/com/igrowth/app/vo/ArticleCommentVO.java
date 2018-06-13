package com.igrowth.app.vo;

import java.util.List;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/810:14
 *    desc    : 输入描述
 * </pre>
 */
public class ArticleCommentVO {

    private ArticleVO articleVO;
    private List<CommentVO> commentVOList;

    public ArticleVO getArticleVO() {
        return articleVO;
    }

    public void setArticleVO(ArticleVO articleVO) {
        this.articleVO = articleVO;
    }

    public List<CommentVO> getCommentVOList() {
        return commentVOList;
    }

    public void setCommentVOList(List<CommentVO> commentVOList) {
        this.commentVOList = commentVOList;
    }
}
