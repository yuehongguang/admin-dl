package com.igrowth.app.biz;

import com.igrowth.app.config.RedisKey;
import com.igrowth.app.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisBiz {

	@Autowired
    StringRedisTemplate redisTemplate;
	
	/*@Autowired
	RedisTemplate<Object, Object> redisTemplate;*/

	/**
	 * 在Redis中记录指定文章点赞点踩次数
	 *
	 * @param module  模块名， 对应Constants对应值。
	 * @param articleId 	  模块对象Id，如不同文章Id不同。
	 * @param accountId        记录用户Id，同一文章只能点一次赞或踩。
	 */
	public void recordArticleTimesInRedis(String module, String articleId,String accountId, Long num) {
		String key = getAccessTimesKey(module, articleId,accountId);
		if(Constants.IG_ARTICLE_UP.equals(module)){//点赞
			/*String timesUp = readAccessTimesInRedis(module, articleId,accountId);//读取单个用户点赞次数
			String timesDown = readAccessTimesInRedis(Constants.IG_ARTICLE_DOWN,articleId,accountId);//读取单个用户点踩次数
			if((StringUtils.isEmpty(timesUp)||"0".equals(timesUp))&&
					(StringUtils.isEmpty(timesDown)||"0".equals(timesDown))){*/
				redisTemplate.opsForValue().increment(key, num);
				recordAllArticleTimesInRedis(Constants.IG_ARTICLE_UP_ALL,articleId,num);//增加总点赞记录数
		}else if(Constants.IG_ARTICLE_DOWN.equals(module)){//点踩
			String timesDown = readAccessTimesInRedis(module, articleId,accountId);//读取单个用户点踩次数
			String timesUp = readAccessTimesInRedis(Constants.IG_ARTICLE_UP,articleId,accountId);//读取单个用户点赞次数
			if((StringUtils.isEmpty(timesUp)||"0".equals(timesUp))&&
					(StringUtils.isEmpty(timesDown)||"0".equals(timesDown))){
				redisTemplate.opsForValue().increment(key, num);
				recordAllArticleTimesInRedis(Constants.IG_ARTICLE_DOWN_ALL,articleId,num);//增加总点踩记录数
			}
		}
	}

	public void cancelArticleTimesInRedis(String module,String articleId,String accountId){
		String key = getAccessTimesKey(module, articleId,accountId);
		redisTemplate.opsForValue().increment(key, -1L);
		if(Constants.IG_ARTICLE_UP.equals(module)){
			cancelAllArticleTimesInRedis(Constants.IG_ARTICLE_UP_ALL,articleId);
		}else{
			cancelAllArticleTimesInRedis(Constants.IG_ARTICLE_DOWN_ALL,articleId);
		}
	}


	/*增加对某个文章的总点赞或点踩数量*/
	/*public void recordAllArticleTimesInRedis(String module, String articleId) {*/
	/*所有用户对某个文章的点赞和点踩数量*/
	public void recordAllArticleTimesInRedis(String module, String articleId, Long num) {
		String key = getAllArticleTimesKey(module, articleId);
		redisTemplate.opsForValue().increment(key, num);
	}

	/*减少对某个文章的总点赞或点踩数量*/
	public void cancelAllArticleTimesInRedis(String module, String articleId) {
		String key = getAllArticleTimesKey(module, articleId);
		redisTemplate.opsForValue().increment(key, -1L);
	}



	/**
	 * 在Redis中记录指定功能访问次数
	 *
	 * @param module  模块名， 对应Constants对应值。
	 * @param id 	  模块对象Id，如视频中不同视频Id不同。
	 *
	 */
	public String readAccessTimesInRedis(String module, String articleId,String accountId) {
		String key = getAccessTimesKey(module, articleId,accountId);
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 获取文章总点赞/点踩数
	 * @param module
	 * @param articleId
	 * @return
	 */
	public String readArticleAllTimesInRedis(String module, String articleId) {
		String key = getAllArticleTimesKey(module, articleId);
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 根据模块和id生成访问次数统计key
	 *
	 * @param module
	 * @param id
	 * @return
	 */
	private String getAccessTimesKey(String module, String articleId,String accountId) {
		if (StringUtils.isNotBlank(articleId)) {
			return String.format(RedisKey.FAV_MODULE_ID_KEY, module, articleId,accountId);
		} else {
			return null;
		}
	}

	private String getAllArticleTimesKey(String module, String articleId) {
		if (StringUtils.isNotBlank(articleId)) {
			return String.format(RedisKey.FAV_ARTICLE_ID_ALL_KEY, module, articleId);
		} else {
			return null;
		}
	}


	/**
	 * 读取指定功能实体被fav的数量
	 *
	 * @param module
	 * @param entityId
	 * @return
	 */
	public Long getModuleFavNumbers(String module, String entityId) {
		String key = getModuleFavKey(module, entityId);
		return redisTemplate.opsForHash().size(key);
	}

	/**
	 * 根据模块和id生成是否fav的key
	 *
	 * @param module
	 * @param entityId
	 * @return
	 */
	private String getModuleFavKey(String module, String entityId) {
		if (StringUtils.isNotBlank(entityId)) {
			return String.format(RedisKey.FAV_MODULE_ID_KEY, module, entityId);
		} else {
			return String.format(RedisKey.FAV_MODULE_ID_KEY, module);
		}
	}


}
