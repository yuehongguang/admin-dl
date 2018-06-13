package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.FeedbackBiz;
import com.github.wxiaoqi.security.admin.biz.MessageBiz;
import com.github.wxiaoqi.security.admin.biz.OrgBiz;
import com.github.wxiaoqi.security.api.entity.Feedback;
import com.github.wxiaoqi.security.api.entity.Message;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("igrowthFeedback")
public class IgrowthFeedbackController extends BaseController<FeedbackBiz,Feedback> {
	
	@Autowired
	private FeedbackBiz feedbackBiz;
	
	@Autowired
	private MessageBiz messageBiz;
	
	@Autowired
	private OrgBiz orgBiz;
	
	/**
	 * 机构回复
	 */
    @RequestMapping(value = "/page1",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Feedback> list(@RequestParam Map<String, Object> params,
    										  @RequestParam(defaultValue = "") String name){
     	Map<String,Object> resultMap = new HashMap<String,Object>();
        Query query = new Query(params);
        String userId = getCurrentUserId();
		Org org = new Org();
        org.setBaseUserId(Integer.parseInt(userId));
        org = orgBiz.selectOne(org);
        resultMap = feedbackBiz.findByOrgId(query,org.getId(),name);
        return new TableResultResponse<Feedback>((Long)resultMap.get("total"),(List<Feedback>)resultMap.get("data"));
    }
    
	/**
	 * 系统反馈
	 */
    @RequestMapping(value = "/page2",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Feedback> list2(@RequestParam Map<String, Object> params,
    										   @RequestParam(defaultValue = "") String name){
     	Map<String,Object> resultMap = new HashMap<String,Object>();
        Query query = new Query(params);
        resultMap = feedbackBiz.findByOrgId(query,null,name);
        return new TableResultResponse<Feedback>((Long)resultMap.get("total"),(List<Feedback>)resultMap.get("data"));
    }

    @RequestMapping(value = "/feedback/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Feedback> get(@PathVariable Long id){
        return new ObjectRestResponse<Feedback>().rel(true).data(baseBiz.selectById(id));
    }
    
    @RequestMapping(value = "/feedbacks/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Feedback> put(@RequestBody Feedback feedback){
    	Feedback t = feedbackBiz.selectById(feedback.getId());
    	t.setFeedbackStatus(1);
    	t.setFeedbackRelpy(feedback.getFeedbackRelpy());
    	baseBiz.updateSelectiveById(t);
    	Message message = new Message();
    	message.setAccountId(t.getAccountId());
    	message.setMessageTitle("您的反馈机构已回复，点击查看.");
    	message.setMessageContent(feedback.getFeedbackRelpy());
    	message.setMessageStatus(0);
    	message.setMessageTime(new Date());
    	message.setMessageType(0);
    	message.setMessageUrl(t.getmLessonId().toString());
    	messageBiz.insert(message);
        return new ObjectRestResponse<Feedback>().rel(true);
    }
}
