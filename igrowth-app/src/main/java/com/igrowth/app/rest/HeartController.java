package com.igrowth.app.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.Heart;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.HeartBiz;

/**
 * ClassName: HeartController <br/>  
 * 爱心接口
 * date: 2017年11月15日 下午4:15:08 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@RequestMapping("heart")
public class HeartController extends BaseController {

	@Autowired
	private HeartBiz heartBiz;


	/**
	 * 爱心列表接口
	 * @author dingshuyan  
	 * @param limit
	 * @param page
	 * @param messageType
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/hearts", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Heart> page(@RequestParam(defaultValue = "10") int limit,
										   @RequestParam(defaultValue = "1") int page,
										   @RequestParam(defaultValue = "") String messageType) {
		Page<Heart> result = heartBiz.getHeartByAccountId(limit,page,getCurrentChildId());
		return new TableResultResponse<Heart>(result.getTotal(), result);
	}
	
	/**
	 * 分享新增爱心
	 * @author dingshuyan  
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/heart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> heart() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Heart> heartList = heartBiz.getHeartByChildIdAndDate(getCurrentChildId(),0,new Date());
		if(heartList!=null&&heartList.size()>=5){
			resultMap.put("result", false);
			resultMap.put("msg", "获取爱心失败,已达到当天最大值.");
		}else{
			heartBiz.saveHeart(Heart.SHARE_HEART_NUM, 0, "分享获取爱心", getCurrentChildId());
			resultMap.put("result", true);
			resultMap.put("msg", "获取爱心成功.");
		}
		return resultMap;
	}
}
