package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.IgrowthCourseCardBiz;
import com.github.wxiaoqi.security.admin.biz.OrgBiz;
import com.github.wxiaoqi.security.admin.entity.OrgClassifyBiz;
import com.github.wxiaoqi.security.api.entity.Activity;
import com.github.wxiaoqi.security.api.entity.IgrowthCourseCard;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-12 8:49
 */
@RestController
@RequestMapping("org")
public class OrgController extends BaseController<OrgBiz, Org> {
	
	@Autowired
	private OrgBiz orgBiz;
	
	@Autowired
	private OrgClassifyBiz orgClassifyBiz;

	@Autowired
	private IgrowthCourseCardBiz courseCardBiz;

    /**
     * 创建机构
     * @param card
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orgs", method = RequestMethod.POST)
    public ObjectRestResponse<Activity> createOrg(@RequestBody Org org) {
        try {
            orgBiz.insertSelective(org);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ObjectRestResponse<Activity>().rel(true);
    }

    @RequestMapping(value = "/org/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse get(@PathVariable Long id){
    	 Org org = orgBiz.selectById(id);
    	 return new ObjectRestResponse<Org>().rel(true).data(org);
    }
    
    @RequestMapping(value = "/org/curr",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getcurr(){
    	Org org = new Org();
        org.setBaseUserId(Integer.parseInt(getCurrentUserId()));
        org = orgBiz.selectOne(org);
    	return new ObjectRestResponse<Org>().rel(true).data(org);
    }
    
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Org> update(@RequestBody Org org){
    	String pics = "";
    	if(StringUtils.isNotEmpty(org.getOrgPic())){
    		String[] strs = org.getOrgPic().split(",");
			for (String str : strs) {
				if (StringUtils.isNotEmpty(str)) {
					pics += str + ",";
				}
    		}
			pics=pics.substring(0,pics.length()-1);
    	}
    	org.setOrgPic(pics);
    	
    	String covs = "";
    	if(StringUtils.isNotEmpty(org.getOrgCoverImg())){
    		String[] strs = org.getOrgCoverImg().split(",");
			for (String str : strs) {
				if (StringUtils.isNotEmpty(str)) {
					covs += str + ",";
				}
    		}
			covs=covs.substring(0,covs.length()-1);
    	}
    	
    	String orgl = "";
    	if(StringUtils.isNotEmpty(org.getOrgLable())){
    		String[] strs = org.getOrgLable().split(",");
			for (String str : strs) {
				if (StringUtils.isNotEmpty(str)) {
					orgl += str + ",";
				}
    		}
			orgl=orgl.substring(0,orgl.length()-1);
    	}
    	org.setOrgLable(orgl);
    	org.setOrgPic(pics);
    	org.setOrgCoverImg(covs);
        baseBiz.updateSelectiveById(org);
        return new ObjectRestResponse<Org>().rel(true);
    }
    
    @Override
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Org> list(@RequestParam Map<String, Object> params){
        //查询列表数据
		String orgName = null;
		if(params.get("orgName")!=null){
			orgName = params.get("orgName").toString();
		}
        Query query = new Query(params);
        return orgBiz.selectByQueryAndOrgName(query,orgName);
    }

	@RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public ObjectRestResponse<Org> removeOrg(@PathVariable Long id){
		IgrowthCourseCard card = new IgrowthCourseCard();
		card.setOrgId(Integer.valueOf(id.toString()));
		List<IgrowthCourseCard> cardList = courseCardBiz.selectList(card);
		if(cardList!=null&&cardList.size()>0){
			return new ObjectRestResponse<Org>().rel(false);
		}
		baseBiz.deleteById(id);
		return new ObjectRestResponse<Org>().rel(true);
	}
}
