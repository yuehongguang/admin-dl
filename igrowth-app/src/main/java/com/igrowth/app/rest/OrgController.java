package com.igrowth.app.rest;

import java.util.ArrayList;
import java.util.List;

import com.github.wxiaoqi.security.api.entity.OrgClassify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.wxiaoqi.security.api.entity.Fav;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.igrowth.app.biz.FavBiz;
import com.igrowth.app.biz.OrgBiz;
import com.igrowth.app.biz.OrgClassifyBiz;
import com.igrowth.app.vo.OrgClassifyVo;

/**
 * ClassName: OrgController <br/>  
 * Function: 有关机构的api类 <br/>  
 * date: 2017年10月19日 下午6:06:06 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Controller
@IgnoreClientToken
@RequestMapping("org")
public class OrgController extends BaseController {
	
	@Autowired
	private OrgBiz orgBiz;

	@Autowired
	private OrgClassifyBiz orgClassifyBiz;
	

	@Autowired
	private FavBiz favBiz;
	
	
	/**
	 * orgList:通过参数查询线上机构列表分页数据 <br/>  
	 * @author dingshuyan  
	 * @param limit 
	 * @param page
	 * @param orgName 机构名称
	 * @param orgClassifyId 机构分类Id
	 * @param latitude 经度
	 * @param longitude 纬度
	 * @param label 标签
	 * @param distance 距离
	 * @return TableResultResponse<Org>
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/online", method = RequestMethod.POST)
	@ResponseBody
	public TableResultResponse<Org> onLineOrgList(@RequestParam(defaultValue = "10") int limit,
												  @RequestParam(defaultValue = "1") int page, 
									              @RequestParam(defaultValue = "")String keyword,
									              @RequestParam(defaultValue = "0") Long orgClassifyId,
									              @RequestParam(defaultValue = "0.0")Double latitude,
									              @RequestParam(defaultValue = "0.0")Double longitude,
									              @RequestParam(defaultValue = "0.0")Double distance,
									              @RequestParam(defaultValue = "")String label) {
		System.out.println(limit+"----"+page+"----"+keyword+"-----"+orgClassifyId+"-----"+latitude+"-----"+longitude+"------distance"+"------"+label);
		Page<Org> result = orgBiz.findOnLineOrgByPage(limit, page, keyword, orgClassifyId, latitude, longitude,distance,label);
		return new TableResultResponse<Org>(result.getTotal(), result.getResult());
	}
	
	/**
	 * onLineOrg:(根据id查询机构信息). <br/>  
	 * @author dingshuyan  
	 * @param id
	 * @return  
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/online/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Org onLineOrg(@PathVariable Long id) {
		Org org = orgBiz.getOrgVoById(id);
		if(getCurrentAccountId()==null){
			org.setFav(-1);
		}else{
			Fav fav = favBiz.findFavByAccountIdAndOrgId(getCurrentAccountId(),org.getId(),1);
			if(fav!=null){
				org.setFav(1);
			}else{
				org.setFav(0);
			}
		}
		return org;
	}
	
	/**
	 * orgList:通过参数查询线上机构列表分页数据 <br/>  
	 * @author dingshuyan  
	 * @param limit 
	 * @param page
	 * @param orgName 机构名称
	 * @param orgClassifyId 机构分类Id
	 * @param latitude 经度
	 * @param longitude 纬度
	 * @param label 标签
	 * @param distance 距离
	 * @return TableResultResponse<Org>
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/offline", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<Org> offLineOrgList(@RequestParam(defaultValue = "10") int limit,
											       @RequestParam(defaultValue = "1") int page, 
							                       @RequestParam(defaultValue = "")String keyword) {
		Page<Org> result = orgBiz.findOffLineOrgByPage(limit, page, keyword);
		return new TableResultResponse<Org>(result.getTotal(), result.getResult());
	}
	
	
	/**
	 * orgclassifyList:查询所有机构分类列表数据 <br/>  
	 * @author dingshuyan  
	 * @return TableResultResponse<OrgClassifyVo>
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/orgclassify", method = RequestMethod.GET)
	@ResponseBody
	public TableResultResponse<OrgClassifyVo> orgClassifyList() {
		/*System.out.println("-------------------------------"+1);
		List<OrgClassifyVo> resultList = new ArrayList<OrgClassifyVo>();
		List<OrgClassifyVo> tempList = new ArrayList<OrgClassifyVo>();
		tempList.add(new OrgClassifyVo(0L,"全部",new ArrayList<OrgClassifyVo>()));
		System.out.println("-----------------------------------------------------------------"+tempList.toString());
		OrgClassifyVo orgClassifyVo = new OrgClassifyVo(0L,"全部",tempList);
		resultList.add(orgClassifyVo);
		resultList.addAll(orgClassifyBiz.getAllVo());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+resultList);
		return new TableResultResponse<OrgClassifyVo>(resultList.size(), resultList);*/
		List<OrgClassifyVo> list = orgClassifyBiz.getAllVo();
		return new TableResultResponse<OrgClassifyVo>(list.size(),list);
	}
}