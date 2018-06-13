package com.github.wxiaoqi.security.admin.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.admin.mapper.OrgClassifyMapper;
import com.github.wxiaoqi.security.admin.vo.OrgClassifyVo;
import com.github.wxiaoqi.security.api.entity.OrgClassify;
import com.github.wxiaoqi.security.common.biz.BaseBiz;

/**
 * ClassName: OrgClassifyBiz <br/>  
 * Function: 机构分类服务类. <br/>  
 * date: 2017年10月25日 下午5:56:08 <br/>  
 * @author dingshuyan  
 * @version   
 * @since JDK 1.8
 */
@Service
public class OrgClassifyBiz extends BaseBiz<OrgClassifyMapper, OrgClassify> {

	@Autowired
	private OrgClassifyMapper orgClassifyMapper;
	
	public List<OrgClassify> getAll() {
		return mapper.selectAll();
	}
	
	public OrgClassify findById(Long id){
		return selectById(id);
	}

	public List<OrgClassify> getByPid(Long pId){
		return mapper.getByPid(pId);
	}
	
	public List<OrgClassify> getByClassifyType(Integer classifyType){
		OrgClassify orgClassify = new OrgClassify();
		orgClassify.setClassifyType(classifyType);
		return mapper.select(orgClassify);
	}
	
	public List<OrgClassifyVo> getAllVo(Integer classifyType) {
		List<OrgClassifyVo> resultList = new ArrayList<OrgClassifyVo>();
		Map<Long, OrgClassifyVo> map = new HashMap<Long, OrgClassifyVo>();
		List<OrgClassify> elements = getByClassifyType(classifyType);
		for (OrgClassify orgClassify : elements) {
			if (orgClassify.getPid() == null) {
				OrgClassifyVo orgClassifyVo = new OrgClassifyVo(orgClassify);
				map.put(orgClassify.getId(), orgClassifyVo);
				resultList.add(orgClassifyVo);
			} else {
				OrgClassifyVo orgClassifyVo = map.get(Long.valueOf(orgClassify.getPid()));
				List<OrgClassifyVo> tList = orgClassifyVo.getChildren();
				if(tList==null){
					tList = new ArrayList<OrgClassifyVo>();
				}
				tList.add(new OrgClassifyVo(orgClassify));
				orgClassifyVo.setChildren(tList);
			}
		}
		return resultList;
	}

	/*public List<OrgClassifyVo> getAllVo() {
		List<OrgClassify> elements = getAll();

		List<OrgClassify> alist = new ArrayList<>();

		List<OrgClassify> list = orgClassifyMapper.getParent();

		List<OrgClassifyVo> olist = new ArrayList<>();

		for (OrgClassify orgClassify : list){
			OrgClassifyVo orgClassifyVo = new OrgClassifyVo(orgClassify.getId(),orgClassify.getClassifyName(),alist);
			olist.add(orgClassifyVo);
		}

		for (int i=0;i<olist.size();i++){

			olist.get(i).setChildren(orgClassifyMapper.getList(olist.get(i).getValue()));


		}

		System.out.println("-------------------"+olist);

		return olist;

	}*/
}
