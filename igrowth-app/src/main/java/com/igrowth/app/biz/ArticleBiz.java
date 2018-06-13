package com.igrowth.app.biz;

import com.github.wxiaoqi.security.api.entity.Article;
import com.github.wxiaoqi.security.api.entity.Circle;
import com.github.wxiaoqi.security.api.entity.Comment;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.mapper.ArticleMapper;
import com.igrowth.app.utils.Constants;
import com.igrowth.app.vo.ArticleVO;
import com.igrowth.app.vo.CircleArticles;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleBiz extends BaseBiz<ArticleMapper, Article> {

	@Autowired
	private CircleBiz circleBiz;
	@Autowired
	private RedisBiz redisBiz;
	@Autowired
	private CommentBiz commentBiz;

	/**
	 * 
	 * findArticleByPage:(根据分页条件查询文章). <br/>  
	 *  
	 * @author lpf  
	 * @param limit
	 * @param page
	 * @return  
	 * @since JDK 1.8
	 */
	public Map<String,Object> findArticleByPage(int limit, int page, int status) {
		Map<String,Object> result = new HashMap<String,Object>();
		page = (page-1)*limit;
		List<ArticleVO> list = mapper.findArticleByPage(limit,page,status,null);
		Integer total = mapper.selectArticleCount(null,status);
		addArticleUpOrDownAndCommentsNum(list);
		result.put("list",list);
		result.put("total",total);
		return result;
	}

	/**
	 * 根据圈子查询文章
	 * @param circleId
	 * @param limit
	 * @param page
	 * @param status
	 * @return
	 */
	public CircleArticles findArticlePageByCircleId(Long circleId,int limit, int page, int status) {
		Integer total = mapper.selectArticleCount(circleId,status);
		page = (page-1)*limit;
		List<ArticleVO> list = mapper.findArticleByPage(limit,page,status,circleId);
		TableResultResponse<ArticleVO> articlePage = new TableResultResponse<ArticleVO>(total, list);
		CircleArticles circleArticles = new CircleArticles();
		Circle circle = circleBiz.selectById(circleId);
		addArticleUpOrDownAndCommentsNum(list);
		circleArticles.setArticlePage(articlePage);
		circleArticles.setCircle(circle);
		return circleArticles;
	}


	private List<? extends Article> addArticleUpOrDownAndCommentsNum(List<? extends Article> articles){
		for(Article article : articles){
			/*从缓存中读取文章点赞数*/
			String timesUp = redisBiz.readArticleAllTimesInRedis(Constants.IG_ARTICLE_UP_ALL, article.getId().toString());
			if(StringUtils.isNotEmpty(timesUp)){
				article.setUpNum(Integer.valueOf(timesUp));
			}else{
				article.setUpNum(0);
			}
			/*从缓存中读取文章点踩数*/
			String timesDown = redisBiz.readArticleAllTimesInRedis(Constants.IG_ARTICLE_DOWN_ALL, article.getId().toString());
			if(StringUtils.isNotEmpty(timesDown)){
				article.setDownNum(Integer.valueOf(timesDown));
			}else{
				article.setDownNum(0);
			}

			/*从缓存中读取用户点赞或踩  0表示点赞,1表示点踩，-1表示没点*/
			String userId = getCurrentUserId();
			String userTimesUp = redisBiz.readAccessTimesInRedis(Constants.IG_ARTICLE_UP, article.getId().toString(),userId);
			String userTimesDown = redisBiz.readAccessTimesInRedis(Constants.IG_ARTICLE_DOWN, article.getId().toString(),userId);
			article.setUserSetUpOrDown("1".equals(userTimesUp)?0:("1".equals(userTimesDown)?1:-1));

			/*查询该文章的评论数量*/
			Comment comment = new Comment();
			comment.setParentCommentId(0L);
			comment.setArticleId(article.getId());
			article.setCommentNum(commentBiz.selectCount(comment));
		}
		return articles;
	}

	/**
	 * 查询文章对象并插入点赞点踩和评论数等信息
	 * @param articleId
	 * @return
	 */
	public ArticleVO findArticleById(Long articleId){
		ArticleVO articleVO = mapper.findArticleById(articleId);
		List<ArticleVO> articleVOList = new ArrayList<ArticleVO>();
		if(articleVO!=null){
			articleVOList.add(articleVO);
			return (ArticleVO)addArticleUpOrDownAndCommentsNum(articleVOList).get(0);
		}else{
			return null;
		}
	}
}
