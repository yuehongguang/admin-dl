package com.github.wxiaoqi.security.admin.biz;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.admin.mapper.ChildMapper;
import com.github.wxiaoqi.security.api.entity.Child;
import com.github.wxiaoqi.security.common.biz.BaseBiz;

/**
 * ClassName: ChildBiz <br/>  
 * Function: 孩子服务类 <br/>  
 * date: 2017年10月26日 下午6:17:14 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Service
public class ChildBiz extends BaseBiz<ChildMapper, Child> {

	public void saveChild(Child child){
		mapper.insert(child);
	}

	public List<Child> findChildByAccountId(Long accountId){
		return mapper.findChildByAccountId(accountId);
	}
	
}
