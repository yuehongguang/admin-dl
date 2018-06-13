package com.igrowth.app.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.Course;
import com.github.wxiaoqi.security.api.entity.Fav;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import com.igrowth.app.mapper.FavMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * ClassName: FavBiz <br/>
 * Function: 收藏. <br/>
 * date: 2017年10月23日 上午9:29:19 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class FavBiz extends BaseBiz<FavMapper, Fav> {
	
	@Autowired
	private OrgBiz orgBiz;
	
	@Autowired
	private CourseBiz courseBiz;

	/**
	 * getFavByTypeAndModelId根据typeId&modelId查询<br/>
	 */
	public Fav getFavByTypeAndModelIdAndAccountId(int favType, Long modelId) {
		Fav fav = new Fav();
		fav.setFavType(favType);
		fav.setModelId(modelId);
		return selectOne(fav);
	}

	/**
	 * insert fav
	 */
	public void createFav(int favType, Long accountId, Long modelId) {
		Fav fav = new Fav();
		fav.setAccountId(accountId);
		fav.setFavType(favType);
		fav.setModelId(modelId);
		fav.setFavTime(new Date());
		insert(fav);
	}

	/**
	 * delete fav
	 */
	public void deleteById(int id) {
		deleteById(id);
	}
	

	/**
	 * find by page
	 */
	public Page<Fav> findFavByTypePage(int limit, int page, int favType, Long accountId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("favType", favType);
		criteria.andEqualTo("accountId", accountId);
		Page<Fav> result = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Fav> list = selectByExample(example,page,result);
		for(Fav fav : list){
			if(1==fav.getFavType()){
				Org org = orgBiz.getOrgById(fav.getModelId());
				fav.setOrg(org);
			}else if(2==fav.getFavType()){
				Course course = courseBiz.getCourseById(fav.getModelId());
				fav.setCourse(course);
			}
		}
		return result;
	}

	public Fav findFavByAccountIdAndOrgId(Long currentAccountId, Long orgId, Integer favType) {
		return mapper.findFavByAccountIdAndOrgId(currentAccountId, orgId, favType);
	}

}
