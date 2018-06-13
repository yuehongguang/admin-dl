package com.igrowth.app.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.Feedback;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import com.igrowth.app.mapper.FeedbackMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * ClassName: FeedbackBiz <br/>  
 * Function: 用户反馈服务<br/>  
 * date: 2017年10月27日 下午2:25:59 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Service
public class FeedbackBiz extends BaseBiz<FeedbackMapper, Feedback> {
 
	/**
	 * findFeedbackByAccountId:根据status type 查询用户反馈. <br/>  
	 * @author dingshuyan  
	 * @param limit
	 * @param page
	 * @param accountId
	 * @param type
	 * @param status
	 * @return  
	 * @since JDK 1.8
	 */
	public Page<Feedback> findFeedbackByAccountId(int limit, int page, Long accountId, int type, int status) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("accountId", accountId);
		if(type!=-1){
			criteria.andEqualTo("feedbackType", type);	
		}
		if(status!=-1){
			criteria.andEqualTo("feedbackStatus", status);
		}
		Page<Feedback> result = PageHelper.startPage(query.getPage(), query.getLimit());
		selectByExample(example,page,result);
		return result;
	}

	/**
	 * createFeedback:保存用户反馈 <br/>  
	 * @author dingshuyan  
	 * @param accountId
	 * @param title
	 * @param content  
	 * @since JDK 1.8
	 */
	public boolean createFeedback(Long childId ,Long accountId, String title, String content, String phone, String name, Long orgId, Long mLessonId) {
		Feedback feedBack = null;
		if (mLessonId != 0) {
			feedBack = findByMlessonIdAndChildId(mLessonId, childId);
		}
		if (feedBack == null) {
			Feedback feedback = new Feedback();
			feedback.setFeedbackType(1);
			if (orgId != 0 && mLessonId != 0) {
				feedback.setmLessonId(mLessonId);
				feedback.setOrgId(orgId);
				feedback.setFeedbackType(4);
			}
			feedback.setPhone(phone);
			feedback.setChildId(childId);
			feedback.setChildName(name);
			feedback.setFeedbackStatus(0);
			feedback.setAccountId(accountId);
			feedback.setFeedbackTime(new Date());
			feedback.setFeedbackContent(content);
			feedback.setFeedbackTitle(title);
			insertSelective(feedback);
			return true;
		}else{
			return false;
		}
	}

	public Feedback findByMlessonIdAndChildId(Long mLessonId,Long childId) {
		Feedback feedback = mapper.findByMlessonIdAndChildId(mLessonId,childId);
		return feedback;
	}
}
