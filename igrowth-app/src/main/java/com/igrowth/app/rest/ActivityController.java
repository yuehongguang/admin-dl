package com.igrowth.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxiaoqi.security.api.entity.Activity;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.igrowth.app.biz.ActivityBiz;

/**
 * ClassName: ActivityController <br/>  
 * 活动接口
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2017年11月21日 上午11:31:48 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@RequestMapping("activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityBiz activityBiz;

	/**
	 * 根据Id查询活动
	 * getCourse: <br/>  
	 * @author dingshuyan  
	 * @param id
	 * @return  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@RequestMapping(value = "activity/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Activity getActivity(@PathVariable Integer id) {
		return activityBiz.getActivity(id);
	}

}
