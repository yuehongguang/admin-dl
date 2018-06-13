package com.igrowth.app.biz;

import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.Comment;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.igrowth.app.mapper.CommentMapper;
import com.igrowth.app.vo.ArticleCommentVO;
import com.igrowth.app.vo.ArticleVO;
import com.igrowth.app.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/10/2314:34
 *    desc    : 评论Service
 * </pre>
 */
@Service
public class CommentBiz extends BaseBiz<CommentMapper, Comment> {
    @Autowired
    private ArticleBiz articleBiz;
    @Autowired
    private AccountBiz accountBiz;

    /**
     *
     * @methodName selectCommentByArticleId
     * @function  根据文章Id查询评论集合
     * @author lpf
     * @date 2017/10/23 17:50
     * @param [articleId]
     * @return java.util.List<com.github.wxiaoqi.security.api.entity.Comment>
     * @version JDK1.8
     */
    public ArticleCommentVO selectCommentByArticleId(Long articleId, int page, int limit){
        int pageStartNum = (page-1)*limit+1;
        int pageEndNum = page*limit;
        ArticleCommentVO articleCommentVO = new ArticleCommentVO();
        ArticleVO articleVO = articleBiz.findArticleById(articleId);

        /*查询父评论集合*/
        List<CommentVO> commentVOList =  mapper.findCommentByArticleIdAndParentCommentId(articleId,
                                                                                         0L,
                                                                                          pageStartNum,
                                                                                          pageEndNum);
        for(CommentVO comment : commentVOList){
            List<CommentVO> replys = new ArrayList<CommentVO>(); // 实例化回复的集合
            comment.setReplyComment(replys); // 设置评论的回复集合
            Long commentAccountId = comment.getAccountId(); // 获取评论的人的Id
            Account commentAccount = accountBiz.selectById(commentAccountId); // 通过评论人的Id获取评论人的信息
            if(commentAccount != null) {
                comment.setCommentAccount(commentAccount); // 设置评论的人的信息
            }
            buildReplyComment(comment, replys,pageStartNum,pageEndNum); // 构建评论与回复信息
        }
        //拼接view object
        articleCommentVO.setCommentVOList(commentVOList);
        articleCommentVO.setArticleVO(articleVO);
        return articleCommentVO;

    }


    /**
     * 构建评论与回复评论的关系
     * @param comment
     * @param offset
     * @param limit
     */
    private void buildReplyComment(CommentVO commentVO, List<CommentVO> replys,int pageStartNum,int pageEndNum){
        List<CommentVO> replyComments = mapper.findCommentByArticleIdAndParentCommentId(commentVO.getArticleId(),
                                                                                        commentVO.getId(),
                                                                                        pageStartNum,
                                                                                        pageEndNum); // 获取评论的所有回复
        replys.addAll(replyComments); // 把所有的回复添加到评论实例化的回复集合中
        for(CommentVO c : replyComments){ // 遍历回复中的回复
            Long replyAccountId = c.getAccountId(); // 获取回复人的id
            Account replyCustomer = accountBiz.selectById(replyAccountId); // 获取回复人信息
            if(replyCustomer != null) {

            }
            Account commentAccount = accountBiz.selectById(commentVO.getAccountId()); // 获取评论人的信息
            c.setCommentAccount(commentAccount); // 设置评论人的信息
            c.setReplyAccount(replyCustomer); // 设置回复人的信息
            buildReplyComment(c, replys,pageStartNum,pageEndNum); // 递归调用
        }
    }
}
