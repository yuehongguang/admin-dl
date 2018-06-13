package com.igrowth.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.Fav;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.FavBiz;

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
@RequestMapping("fav")
public class FavController extends BaseController {
	
	@Autowired
	private FavBiz favBiz;

 
	/**
	 * @author dingshuyan  
	 * @param favType 收藏的类型 0文章 1机构 2课程 
	 * @param modelId 收藏对应的id 如文章id机构id课程id
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/{favType}/{modelId}", method = RequestMethod.PUT)
	@ResponseBody
	public ObjectRestResponse<Object> fav(@PathVariable Integer favType,
										  @PathVariable Long modelId) {
		try{
			Fav fav = favBiz.getFavByTypeAndModelIdAndAccountId(favType, modelId);
			if (fav != null) {
				favBiz.deleteById(fav.getId());
			}else{
				favBiz.createFav(favType, getCurrentAccountId(), modelId); 
			}
		}catch(Exception e){
			return new ObjectRestResponse().rel(false);
		}
		return new ObjectRestResponse().rel(true);
	}
	
	/**
	 * 我的收藏列表
	 * @author dingshuyan  
	 * @param name
	 * @param groupType
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/favs", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Fav> onLineOrgList(@RequestParam(defaultValue = "10") int limit,
												  @RequestParam(defaultValue = "1") int page, 
							                      @RequestParam(defaultValue = "1") int favType) {
		Page<Fav> result = favBiz.findFavByTypePage(limit, page, favType, getCurrentAccountId());
		return new TableResultResponse<Fav>(result.getTotal(), result.getResult());
	}
}
