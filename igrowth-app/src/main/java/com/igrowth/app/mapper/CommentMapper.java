package com.igrowth.app.mapper;

import com.github.wxiaoqi.security.api.entity.Comment;
import com.igrowth.app.vo.CommentVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommentMapper extends Mapper<Comment> {

    List<CommentVO> findCommentByArticleIdAndParentCommentId(@Param("articleId") Long articleId,
                                                             @Param("parentCommentId") Long parentCommentId,
                                                             @Param("pageStartNum") int pageStartNum,
                                                             @Param("pageEndNum") int pageEndNum);
}