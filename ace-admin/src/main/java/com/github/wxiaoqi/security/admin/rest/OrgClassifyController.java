package com.github.wxiaoqi.security.admin.rest;

import java.util.ArrayList;
import java.util.List;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxiaoqi.security.admin.entity.Group;
import com.github.wxiaoqi.security.admin.entity.OrgClassifyBiz;
import com.github.wxiaoqi.security.admin.vo.OrgClassifyVo;
import com.github.wxiaoqi.security.api.entity.OrgClassify;
import com.github.wxiaoqi.security.common.rest.BaseController;

import io.swagger.annotations.Api;
import tk.mybatis.mapper.entity.Example;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-12 8:49
 */
@Controller
@RequestMapping("orgClassify")
@Api("机构分类模块")
public class OrgClassifyController extends BaseController<OrgClassifyBiz, OrgClassify> {

	@Autowired
	private OrgClassifyBiz orgClassifyBiz;
	
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<OrgClassify> list(String name,String groupType) {
        if(StringUtils.isBlank(name)&&StringUtils.isBlank(groupType)) {
            return new ArrayList<OrgClassify>();
        }
        Example example = new Example(Group.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(groupType)) {
            example.createCriteria().andEqualTo("groupType", groupType);
        }
        return baseBiz.selectByExample(example);
    }
    
    
	/**
	 * orgclassifyList:查询所有机构分类列表数据 <br/>  
	 * @author dingshuyan  
	 * @return TableResultResponse<OrgClassifyVo>
	 * @since JDK 1.8
	 */
	@RequestMapping(value = "/orgclassify", method = RequestMethod.GET)
	@ResponseBody
    @IgnoreClientToken
	public List<OrgClassifyVo> orgClassifyList(@RequestParam(defaultValue = "1") Integer classifyType) {
		List<OrgClassifyVo> resultList = orgClassifyBiz.getAllVo(classifyType);
		return resultList;
	}
}
