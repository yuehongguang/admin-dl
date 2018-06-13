package com.igrowth.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxiaoqi.security.api.entity.Banner;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.BannerBiz;

/**
 * ClassName: BannerController <br/>  
 * Function: 轮播图 <br/>  
 * date: 2017年10月26日 下午6:12:20 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@RequestMapping("banner")
public class BannerController extends BaseController {

	@Autowired
	private BannerBiz bannerBiz;

	/**
	 * 根据类型分页查询轮播
	 * @author dingshuyan  
	 * @param limit
	 * @param offset
	 * @param bannertype
	 * @return  
	 * @since JDK 1.8
	 */
	@IgnoreClientToken
	@RequestMapping(value = "/banners", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Banner> page(@RequestParam(defaultValue = "10") int limit,
										    @RequestParam(defaultValue = "1") int offset,
											@RequestParam(defaultValue = "org") String bannertype) {
		List<Banner> elements = bannerBiz.getBannerByTypeName(bannertype);
		return new TableResultResponse<Banner>(elements.size(),elements);
	}
}
