package com.igrowth.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.Message;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.MessageBiz;

/**
 * ClassName: MessageController <br/>
 * Function: 消息 controller<br/>
 * date: 2017年10月25日 下午5:55:06 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Controller
@RequestMapping("message")
public class MessageController extends BaseController {

	@Autowired
	private MessageBiz messageBiz;

	/**
	 * page:(消息列表). <br/>  
	 * @author dingshuyan  
	 * @param limit
	 * @param offset
	 * @param messageType
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Message> page(@RequestParam(defaultValue = "10") int limit,
										     @RequestParam(defaultValue = "1") int page,
										     @RequestParam(defaultValue = "") String messageType) {
		Page<Message> result = messageBiz.getMessageByMessageTypeAndAccountId(limit,page,messageType,getCurrentAccountId());
		return new TableResultResponse<Message>(result.getTotal(), result);
	}
	
	/**
	 * read:(消息已读接口). <br/>  
	 * @author dingshuyan  
	 * @param messageId
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/read/{messageId}", method = RequestMethod.POST)
	@ResponseBody
	public ObjectRestResponse<Object> read(@PathVariable int messageId) {
		Message message = messageBiz.findById(messageId);
		if(message==null){
			return new ObjectRestResponse().rel(false);
		}else{
			message.setMessageStatus(1);
			messageBiz.saveMessage(message);
			return new ObjectRestResponse().rel(true);
		}
	}
	
}
