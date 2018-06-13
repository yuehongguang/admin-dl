package com.igrowth.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxiaoqi.security.api.entity.AppInfo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.AppResponse;
import com.igrowth.app.biz.AppInfoBiz;

/**
 * ClassName: FavController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2017年10月23日 上午9:27:36 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@IgnoreClientToken
@RequestMapping("appinfo")
public class AppInfoController extends BaseController {
	
	@Autowired
	private AppInfoBiz appInfoBiz;

	/**
	 * 最新版本app信息
	 * @author dingshuyan  
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/latest", method = RequestMethod.GET)
	@ResponseBody
	public AppResponse<Object> fav() {
		AppInfo appInfo = appInfoBiz.findLatestAppInfo();
		return new AppResponse<Object>(200, "success", appInfo);
	}
	
}
