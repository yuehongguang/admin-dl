package com.igrowth.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.Feedback;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.FeedbackBiz;

/**
 * ClassName: ChildController <br/>  
 * Function: 用户反馈api. <br/>  
 * date: 2017年10月26日 下午6:15:16 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@RequestMapping("feedback")
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackBiz feedbackBiz;

	/**
	 * 获取用户的反馈
	 * @author dingshuyan  
	 * @param limit
	 * @param page
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/feedbacks", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Feedback> page(@RequestParam(defaultValue = "10") int limit,
										      @RequestParam(defaultValue = "1") int page,
										      @RequestParam(defaultValue = "-1") int type,
										      @RequestParam(defaultValue = "-1") int status) {
		Page<Feedback> result = feedbackBiz.findFeedbackByAccountId(limit,page,getCurrentAccountId(),type,status);
		return new TableResultResponse<Feedback>(result.size(),result);
	}
	
	/**
	 * 获取用户的反馈详情
	 * @author dingshuyan  
	 * @param limit
	 * @param page
	 * @return  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@RequestMapping(value = "/feedback/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Feedback getFeedback(@PathVariable Long id) {
		return feedbackBiz.selectById(id);
	}
}