package com.igrowth.app.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.api.entity.Circle;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import com.igrowth.app.mapper.CircleMapper;
import com.igrowth.app.mapper.MyCircleMapper;
import com.igrowth.app.vo.MyCircleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: CircleBiz <br/>  
 * Function: 圈子服务类. <br/>  
 * date: 2017年10月20日 上午11:59:11 <br/>  
 *  
 * @author lpf  
 * @version   
 * @since JDK 1.8
 */
@Service
public class CircleBiz extends BaseBiz<CircleMapper, Circle> {
	
	@Autowired
	private MyCircleMapper myCircleMapper;
	/**
	 * 
	 * findCircleByPage:根据分页条件查询圈子列表. <br/>  
	 *  
	 * @author lpf  
	 * @param limit
	 * @param page
	 * @return  
	 * @since JDK 1.8
	 */
	public Page<Circle> findCircleByPage(int limit, int page,String sortColumn,String orderBy) {
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query(params);
		query.setLimit(limit);
		query.setPage(page);
		Example example = selectByQueryApi(query);
		if("asc".equals(orderBy)){
			example.orderBy(sortColumn).asc();
		}else{
			example.orderBy(sortColumn).desc();
		}
		Page<Circle> result = PageHelper.startPage(query.getPage(), query.getLimit());
		selectByExample(example,page,result);
		return result;  
	}

}
