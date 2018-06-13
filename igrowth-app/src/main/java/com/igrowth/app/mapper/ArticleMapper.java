package com.igrowth.app.mapper;

import com.github.wxiaoqi.security.api.entity.Article;
import com.igrowth.app.vo.ArticleVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleMapper extends Mapper<Article> {


    /**
     *
     * @methodName findAllArticle
     * @function  根据状态查询文章（显示/不显示）
     * @author lpf
     * @date 2017/10/26 11:51
     * @param [status]
     * @return java.util.List<com.igrowth.app.vo.ArticleVO>
     * @version JDK1.8
     */
    public List<ArticleVO> findArticleByPage(@Param("limit") int limit,@Param("page") int page,@Param("status") int status,@Param("circleId") Long circleId);

    /**
     *
     * @param articleId
     * @return
     */
    public ArticleVO findArticleById(@Param("articleId") Long articleId);

    /**
     * @param status
     * @return  
     * @since JDK 1.8
     */
	public Integer selectArticleCount(@Param("circleId") Long circleId,@Param("status") int status);
}