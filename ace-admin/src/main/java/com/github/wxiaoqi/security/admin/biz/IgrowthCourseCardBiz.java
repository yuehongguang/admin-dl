package com.github.wxiaoqi.security.admin.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.admin.mapper.IgrowthCourseCardMapper;
import com.github.wxiaoqi.security.api.entity.IgrowthCourseCard;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @date 2017-11-29 16:13:04
 */
@Service
public class IgrowthCourseCardBiz extends BaseBiz<IgrowthCourseCardMapper,IgrowthCourseCard> {
    /**
     * 根据分页条件查询课程卡列表
     * @param limit
     * @param page
     * @param sortColumn
     * @param orderBy
     * @return
     */
    public Page<IgrowthCourseCard> findCourseByPage(int limit, int page, long orgId,String cardName) {
        Map<String, Object> params = new HashMap<String, Object>();
        Query query = new Query(params);
        query.setLimit(limit);
        query.setPage(page);
        Example example = selectByQueryApi(query);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(cardName)){
            criteria.andLike("cardName","%" + cardName + "%");
        }
        criteria.andEqualTo("orgId",orgId);
        example.orderBy("id").desc();
        Page<IgrowthCourseCard> result = PageHelper.startPage(query.getPage(), query.getLimit());
        selectByExample(example);
        return result;
    }

	public List<IgrowthCourseCard> selectActiveCourseCardByOrgId(Long orgId) {
		return mapper.selectActiveCourseByOrgId(orgId);
	}

}