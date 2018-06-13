package com.igrowth.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.Child;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.AccountBiz;
import com.igrowth.app.biz.ChildBiz;

/**
 * ClassName: ChildController <br/>
 * Function: 孩子api. <br/>
 * date: 2017年10月26日 下午6:15:16 <br/>
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Controller
@RequestMapping("child")
public class ChildController extends BaseController {

	@Autowired
	private ChildBiz childBiz;
	
	@Autowired
	private AccountBiz accountBiz;

	/**
	 * 获取用户的孩子列表
	 * @author dingshuyan
	 * @param limit
	 * @param page
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/childs", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Child> page(@RequestParam(defaultValue = "10") int limit,
										   @RequestParam(defaultValue = "1") int page) {
		Page<Child> result = childBiz.findChildByAccountId(limit, page, getCurrentAccountId(), getCurrentChildId());
		return new TableResultResponse<Child>(result.size(), result);
	}
	
	
	/**
	  * 获取当前孩子信息
	  * @author dingshuyan  
	  * @param id
	  * @return  
	  * @since JDK 1.8
	 */
	@RequestMapping(value = "curchild", method = RequestMethod.GET)
	@ResponseBody
	public Child curchild(){
		Child child =  childBiz.getChildById(getCurrentChildId());
		Account account = accountBiz.findAccountById(getCurrentAccountId());
		if(account.getCurrentCid()==child.getId()){
			child.setCurChild(true);
		}
		return child;
	}

	
	/**
	 * crateChild:创建孩子信息 <br/>  
	 * @author dingshuyan  
	 * @param childName
	 * @param childSex
	 * @param childBrith
	 * @param childPic
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/child", method = RequestMethod.PUT)
	@ResponseBody
	public ObjectRestResponse crateChild(@RequestParam String childName, 
									     @RequestParam Integer childSex,
									     @RequestParam String childBrith,
									     @RequestParam String childPic,
									     @RequestParam String childNickname) {
		try {
			childBiz.createChild(getCurrentAccountId(),childName, childSex, childBrith, childPic, childNickname);
		} catch (Exception e) {
			return new ObjectRestResponse().rel(false);
		}
		return new ObjectRestResponse().rel(true);
	}
	
	
	/**
	 * 修改孩子信息
	 * @param id
	 * @param childName
	 * @param childSex
	 * @param childBrith
	 * @param childPic
	 * @param childNickname
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ObjectRestResponse updateChild(@PathVariable Long id,
										  @RequestParam(defaultValue = "") String childName , 
									      @RequestParam(defaultValue = "-1") Integer childSex,
									      @RequestParam(defaultValue = "") String childBrith,
									      @RequestParam(defaultValue = "") String childPic,
									      @RequestParam(defaultValue = "") String childNickname) {
		try {
			childBiz.updateChild(id, childName, childSex, childBrith, childPic, childNickname);
		} catch (Exception e) {
			return new ObjectRestResponse().rel(false);
		}
		return new ObjectRestResponse().rel(true);
	}
	

	/**
	 * 切换孩子
	 * selectChild
	 * @author dingshuyan  
	 * @param id
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ObjectRestResponse selectChild(@PathVariable Long id) {
		try {
			childBiz.selectChild(id, getCurrentAccountId());
		} catch (Exception e) {
			return new ObjectRestResponse().rel(false);
		}
		return new ObjectRestResponse().rel(true);
	}
	
}
