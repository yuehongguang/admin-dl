package com.igrowth.app.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxiaoqi.security.api.entity.Article;
import com.github.wxiaoqi.security.api.entity.MyCircle;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.ArticleBiz;
import com.igrowth.app.biz.MyCircleBiz;
import com.igrowth.app.biz.RedisBiz;
import com.igrowth.app.utils.Constants;
import com.igrowth.app.vo.ArticleVO;
import com.igrowth.app.vo.CircleArticles;


/**
 * 
 * ClassName: ArticleController <br/>  
 * Function: 动态（文章）api类. <br/>  
 * date: 2017年10月20日 下午1:54:09 <br/>  
 *  
 * @author lpf  
 * @version   
 * @since JDK 1.8
 */

@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {
	
	@Autowired
	private ArticleBiz articleBiz;

	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	private RedisBiz redisBiz;

	@Autowired
	private MyCircleBiz myCircleBiz;



	/**
	 * 
	 * findArticleByPage:(根据分页条件筛选文章). <br/>  
	 *  
	 * @author lpf  
	 * @param limit
	 * @param page
	 * @return  TableResultResponse<Article>
	 * @since JDK 1.8
	 */

	@IgnoreClientToken
	@GetMapping("/articles")
	public TableResultResponse<ArticleVO> findArticleByPage(@RequestParam(defaultValue = "10") int limit,
															@RequestParam(defaultValue = "1") int page,
															@RequestParam(defaultValue = "0") int status) {
		Map<String,Object> result = articleBiz.findArticleByPage(limit, page,status);
		int total = (Integer)result.get("total");
		List<ArticleVO> list = (List)result.get("list");
		return new TableResultResponse<ArticleVO>(total, list);
	}
	
	
	/**
	 * 
	 * findArticleById:(根据Id查询动态（文章）实体). <br/>  
	 *  
	 * @author lpf  
	 * @param id
	 * @return  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@GetMapping("/articles/{id}")
	public Article findArticleById(@PathVariable Long id) {

		return articleBiz.selectById(id);
	}

	/**
	 *
	 * createArticle:(新增动态（文章）对象). <br/>
	 *
	 * @author lpf
	 * @param article
	 * @return
	 * @since JDK 1.8
	 */
	@PostMapping("/articles")
	public Map<String,Object> createArticle(@RequestBody Article article) {
		Map<String,Object> result = new HashMap<String,Object>();
		Boolean isSuccess = Boolean.FALSE;
		Long accountId = getCurrentAccountId();
		 if(article.getId()!=null){
			 article.setId(null);
		 }
		 try {
		 	article.setAccountId(accountId);
		 	article.setStatus(0);
			articleBiz.insert(article);
			isSuccess = Boolean.TRUE;
		} catch (Exception e) {
			isSuccess = Boolean.FALSE;
			e.printStackTrace();  
			
		}
		result.put("isSuccess", isSuccess);
		return result;
	}
	
	/**
	 * 
	 * updateArticle:(根据Id更新动态（文章）实体). <br/>  
	 *  
	 * @author lpf  
	 * @param article
	 * @return  
	 * @since JDK 1.8
	 */
	@PutMapping("/articles")
	public Map<String,Object> updateArticle(@RequestBody Article article) {
		Map<String,Object> result = new HashMap<String,Object>();
		Boolean isSuccess = Boolean.FALSE;
		 if(article.getId()==null){
			 result.put("isSuccess", Boolean.FALSE);
			 result.put("message", "更新失败，id不能为空！");
			 return result;
		 }
		 try {
			articleBiz.updateSelectiveById(article);
			isSuccess = Boolean.TRUE;
		} catch (Exception e) {
			isSuccess = Boolean.FALSE;
			e.printStackTrace();  
			
		}
		result.put("isSuccess", isSuccess);
		return result;
	}

	/**
	 *
	 * @methodName updateArticleUpOrDown
	 * @function   对文章点赞或点踩
	 * @author lpf
	 * @date 2017/10/25 15:31
	 * @param [articleId, status]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @version JDK1.8
	 */
	@PutMapping("/articles/upDown/{articleId}/{status}")
	public Map<String,Object> updateArticleUpOrDown(@PathVariable Long articleId,@PathVariable String status) {
		/* status=0 点赞   status=1 点踩 */
		Map<String,Object> resultMap = new HashMap<>();
		Boolean isSuccess = Boolean.FALSE;
		boolean isZ = false;
		boolean isC = false;
		try {
			String userId = getCurrentAccountId().toString();
			String timez = redisBiz.readAccessTimesInRedis(Constants.IG_ARTICLE_UP, articleId.toString(),userId);
			String timec = redisBiz.readAccessTimesInRedis(Constants.IG_ARTICLE_DOWN, articleId.toString(),userId);
			if((StringUtils.isNotEmpty(timez))&&!"0".equals(timez)){
				isZ = true; 
			}
			if((StringUtils.isNotEmpty(timec))&&!"0".equals(timec)){
				isC = true; 
			}
			String modules = null;
			if ("0".equals(status)) {
				modules = Constants.IG_ARTICLE_UP;
			} else if ("1".equals(status)) {
				modules = Constants.IG_ARTICLE_DOWN;
			}
			if(isZ==false&&isC==false){
				redisBiz.recordArticleTimesInRedis(modules,articleId.toString(),userId,1L);
				isSuccess = Boolean.TRUE;
			}else{  
				if((isC==true&&Constants.IG_ARTICLE_DOWN.equals(modules))||(isZ==true&&Constants.IG_ARTICLE_UP.equals(modules))){
					//redisBiz.recordArticleTimesInRedis(modules,articleId.toString(),userId,-1L);
					isSuccess = Boolean.FALSE;
				}else{
					redisBiz.recordArticleTimesInRedis(modules,articleId.toString(),userId,1L);
					isSuccess = Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = Boolean.FALSE;
		}
		resultMap.put("isSuccess",isSuccess);
		return resultMap;
	}

	/**
	 * 取消点赞、点踩
	 * status=0 取消点赞   status=1 取消点踩
	 * @param articleId
	 * @param status
	 * @return
	 */
	@PutMapping("/articles/upDownCancel/{articleId}/{status}")
	public Map<String,Object> cancelArticleUpOrDown(@PathVariable Long articleId,@PathVariable String status) {
		/* status=0 取消点赞   status=1 取消点踩 */
		Map<String,Object> resultMap = new HashMap<>();
		Boolean isSuccess = Boolean.FALSE;
		String userId = getCurrentAccountId().toString();
		String module = null;
		if("0".equals(status)){//执行取消点赞
			module = Constants.IG_ARTICLE_UP;
			String time = redisBiz.readAccessTimesInRedis(module, articleId.toString(),userId);
			if(!(StringUtils.isEmpty(time))&&!"0".equals(time)){
				redisBiz.cancelArticleTimesInRedis(module, articleId.toString(),userId);
			}else{
				isSuccess = Boolean.FALSE;
			}
		}else if("1".equals(status)){//执行取消点踩
			module = Constants.IG_ARTICLE_DOWN;
			String time = redisBiz.readAccessTimesInRedis(module, articleId.toString(),userId);
			if(!(StringUtils.isEmpty(time))&&!"0".equals(time)){
				redisBiz.cancelArticleTimesInRedis(module, articleId.toString(),userId);
			}else{
				isSuccess = Boolean.FALSE;
			}
		}else{
			resultMap.put("isSuccess",isSuccess);
		}

		return resultMap;
	}
	
	/**
	 * 
	 * deleteArticle:(删除动态（文章）对象). <br/>  
	 *  
	 * @author lpf  
	 * @param id
	 * @return  
	 * @since JDK 1.8
	 */

	@DeleteMapping("/articles/{id}")
	public Map<String,Object> deleteArticle(@PathVariable Long id) {
		Map<String,Object> result = new HashMap<String,Object>();
		Boolean isSuccess = Boolean.FALSE;
		 try {
			articleBiz.deleteById(id);
			isSuccess = Boolean.TRUE;
		} catch (Exception e) {
			isSuccess = Boolean.FALSE;
			e.printStackTrace();  
			
		}
		result.put("isSuccess", isSuccess);
		return result;
	}

	/**
	 *
	 * @methodName getCircleInfo
	 * @function  根据圈子Id查询圈子及动态详情
	 * @author lpf
	 * @date 2017/10/23 9:53
	 * @param [circleId]
	 * @return com.igrowth.app.vo.CircleArticles
	 * @version JDK1.8
	 */
	@IgnoreClientToken
	@GetMapping("/articles/circle/{circleId}")
	public CircleArticles getCircleInfo(@PathVariable Long circleId,
										@RequestParam(defaultValue = "10") int limit,
										@RequestParam(defaultValue = "1") int page,
										@RequestParam(defaultValue = "0") int status){
		Long childId = getCurrentChildId();
		if(childId==null){
			CircleArticles circleArticles = articleBiz.findArticlePageByCircleId(circleId,limit,page,status);
			return circleArticles;
		}else{
			CircleArticles circleArticles = articleBiz.findArticlePageByCircleId(circleId,limit,page,status);
			MyCircle myCircle = new MyCircle();
			myCircle.setChildId(childId);
			myCircle.setCircleId(circleId);
			MyCircle c = myCircleBiz.selectOne(myCircle);
			if(c==null || c.getIsfocus()==1){
				circleArticles.getCircle().setIsfocus(0);
			}else{
				circleArticles.getCircle().setIsfocus(1);
			}
			return circleArticles;
		}

	}
	
}
