package com.igrowth.app.rest;

import com.github.wxiaoqi.security.api.entity.Article;
import com.github.wxiaoqi.security.api.entity.Comment;
import com.github.wxiaoqi.security.api.entity.Message;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.igrowth.app.biz.ArticleBiz;
import com.igrowth.app.biz.CommentBiz;
import com.igrowth.app.biz.MessageBiz;
import com.igrowth.app.vo.ArticleCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/10/2314:33
 *    desc    : 评论类API
 * </pre>
 */

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Value("${url.localhost}")
    String localhost;

    @Autowired
    private CommentBiz commentBiz;
    @Autowired
    private ArticleBiz articleBiz;
    @Autowired
    private MessageBiz messageBiz;

    /**
     *
     * @methodName findCommentById
     * @function  根据Id查询评论
     * @author lpf
     * @date 2017/10/23 14:46
     * @param [id]
     * @return com.github.wxiaoqi.security.api.entity.Comment
     * @version JDK1.8
     */
    @GetMapping("/comments/{id}")
    public Comment findCommentById(@PathVariable Long id) {
        return commentBiz.selectById(id);
    }

    /**
     *
     * @methodName findCommentByArticleId
     * @function  根据文章Id查询评论
     * @author lpf
     * @date 2017/10/23 17:57
     * @param [articleId, limit, page]
     * @return java.util.List<com.github.wxiaoqi.security.api.entity.Comment>
     * @version JDK1.8
     */
    @IgnoreClientToken
    @GetMapping("/comments/article/{articleId}")
    public ArticleCommentVO findCommentByArticleId(@PathVariable Long articleId,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int limit){
        return commentBiz.selectCommentByArticleId(articleId,page,limit);
    }


    /**
     *
     * @methodName createComment
     * @function   新增一条评论记录
     * @author lpf
     * @date 2017/10/24 15:25
     * @param [comment]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @version JDK1.8
     */
    @PostMapping("/comments")
    public Map<String,Object> createComment(@RequestParam Long articleId,
			 								@RequestParam String content){
        Map<String,Object> resultMap = new HashMap<>();
        Boolean isSuccess = Boolean.FALSE;
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setContent(content);
        comment.setAccountId(getCurrentAccountId());
        comment.setParentCommentId(0L);
        comment.setCommentDate(new Date());
        try {
            commentBiz.insertSelective(comment);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = Boolean.FALSE;
        }

        //向message表新增一条消息记录
        Message message = new Message();
        message.setMessageContent(content);
        message.setMessageTitle("您的文章有一条新的评论");
        message.setMessageTime(new Date());
        message.setMessageType(1);
        message.setMessageStatus(0);
        message.setMessageUrl(articleId.toString());
        Article article = articleBiz.selectById(articleId);
        message.setAccountId(article.getAccountId());
        messageBiz.insertSelective(message);

        resultMap.put("isSuccess",isSuccess);
        return resultMap;
    }

    /**
     *
     * @methodName createCommentReply
     * @function   新增一条回复记录
     * @author lpf
     * @date 2017/10/24 15:25
     * @param [comment]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @version JDK1.8
     */
    @PostMapping(value="/comments/reply",consumes = "application/json")
    public Map<String,Object> createCommentReply(@RequestBody Comment comment){
        Map<String,Object> resultMap = new HashMap<>();
        Boolean isSuccess = Boolean.FALSE;
        try {
            comment.setAccountId(getCurrentAccountId());
            comment.setCommentDate(new Date());
            commentBiz.insertSelective(comment);
            isSuccess = Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = Boolean.FALSE;
        }

        //向message表新增一条消息记录
        Long articleId = comment.getArticleId();
        Message message = new Message();
        message.setMessageContent(comment.getContent());
        message.setMessageTitle("您的评论有一条新的回复");
        message.setMessageTime(new Date());
        message.setMessageType(1);
        message.setMessageStatus(0);
        message.setMessageUrl(articleId.toString());
        Comment comment1 = commentBiz.selectById(comment.getParentCommentId());
        message.setAccountId(comment1.getAccountId());
        messageBiz.insertSelective(message);

        resultMap.put("isSuccess",isSuccess);
        return resultMap;
    }
}
