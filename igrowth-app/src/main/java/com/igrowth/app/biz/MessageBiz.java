package com.igrowth.app.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.Message;
import com.igrowth.app.mapper.MessageMapper;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: MessageBiz <br/>
 * Function: 消息服务类<br/>
 * date: 2017年10月25日 下午5:58:04 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class MessageBiz extends BaseBiz<MessageMapper, Message> {

	/**
	 * getMessageByMessageTypeAndAccountId:. <br/>  
	 * @author dingshuyan  
	 * @param limit
	 * @param page
	 * @param messageType
	 * @param accountId
	 * @return  
	 * @since JDK 1.8
	 */
	public Page<Message> getMessageByMessageTypeAndAccountId(int limit, int page, String messageType, Long accountId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		example.orderBy("id").desc();
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(messageType)) {
			criteria.andLike("messageType", messageType);
		}	
		criteria.andEqualTo("accountId", accountId);
		Page<Message> result = PageHelper.startPage(query.getPage(), query.getLimit());
		selectByExample(example,page,result);
		return result;
	}

	public Message findById(int messageId) {
		  return selectById(messageId);
	}

	public void saveMessage(Message message) {
		updateSelectiveById(message);
	}

}
