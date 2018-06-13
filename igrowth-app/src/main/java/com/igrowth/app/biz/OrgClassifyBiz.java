package com.igrowth.app.biz;

import com.github.wxiaoqi.security.api.entity.OrgClassify;
import com.igrowth.app.mapper.OrgClassifyMapper;
import com.igrowth.app.vo.OrgClassifyVo;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<OrgClassify> getByPid(Long pId){
		OrgClassify orgClassify = new OrgClassify();
		orgClassify.setPid(pId);
		return selectByExample(orgClassify);
	}

	/**
	 * getAllVo:获取所有的机构分类 <br/>  
	 * @author dingshuyan  
	 * @return  
	 * @since JDK 1.8
	 */
	public List<OrgClassifyVo> getAllVo() {
		List<OrgClassifyVo> resultList = new ArrayList<OrgClassifyVo>();
		Map<Long, OrgClassifyVo> map = new HashMap<Long, OrgClassifyVo>();
		List<OrgClassify> elements = getAll();
		for (OrgClassify orgClassify : elements) {
			if (orgClassify.getPid() == null) {
				OrgClassifyVo orgClassifyVo = new OrgClassifyVo(orgClassify);
				map.put(orgClassify.getId(), orgClassifyVo);
				resultList.add(orgClassifyVo);
			} else {
				OrgClassifyVo orgClassifyVo = map.get(Long.valueOf(orgClassify.getPid()));
				List<OrgClassifyVo> tList = orgClassifyVo.getList();
				tList.add(new OrgClassifyVo(orgClassify));
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

			olist.get(i).setList(orgClassifyMapper.getList(olist.get(i).getId()));


		}

		System.out.println("-------------------"+olist);

		return olist;

	}*/





	public List<OrgClassify> selectAll() {
		return mapper.selectAll();
	}
}
