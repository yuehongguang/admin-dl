package com.github.wxiaoqi.security.admin.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.admin.mapper.CircleMgrMapper;
import com.github.wxiaoqi.security.api.entity.Circle;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/610:02
 *    desc    : 输入描述
 * </pre>
 */
@Service
public class CircleMgrBiz extends BaseBiz<CircleMgrMapper, Circle> {

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
    public Page<Circle> findCircleByPage(int limit, int page, String sortColumn, String orderBy,String circleName) {
        Map<String, Object> params = new HashMap<String, Object>();
        Query query = new Query(params);
        query.setLimit(limit);
        query.setPage(page);
        Example example = selectByQueryApi(query);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(circleName)){
            criteria.andLike("circleName", "%" + circleName + "%");
        }
        if("asc".equals(orderBy)){
            example.orderBy(sortColumn).asc();
        }else{
            example.orderBy(sortColumn).desc();
        }
        Page<Circle> result = PageHelper.startPage(query.getPage(), query.getLimit());
        selectByExample(example);
        return result;
    }
}
