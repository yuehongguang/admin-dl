package com.github.wxiaoqi.security.admin.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.admin.mapper.HeartMapper;
import com.github.wxiaoqi.security.api.entity.Heart;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: OrgBiz <br/>
 * Function: 课程服务类 <br/>
 * date: 2017年10月19日 下午6:13:37 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class HeartBiz extends BaseBiz<HeartMapper, Heart> {
	
	public Integer findHeartByChildId(Long childId){
		 return mapper.selectSumHeartNumByChild(childId);
	}
	
	public void saveHeart(Integer heartNum, Integer heartType, String heartName, Long childId){
		Heart heart = new Heart();
		heart.setChildId(childId);
		heart.setHeartName(heartName);
        heart.setHeartNum(heartNum);
        heart.setHeartType(heartType);
        heart.setHeartTime(new Date());
        mapper.insert(heart);
	}

	public Page<Heart> getHeartByAccountId(int limit, int page, Long childId) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("childId", childId);
		Page<Heart> result = PageHelper.startPage(query.getPage(), query.getLimit());
		selectByExample(example,page,result);
		return result;
	}

	public List<Heart> getHeartByChildIdAndDate(Long childId, Integer heartType, Date date) {
		return mapper.getHeartByChildIdAndDate(childId, heartType, date);
	}
}
