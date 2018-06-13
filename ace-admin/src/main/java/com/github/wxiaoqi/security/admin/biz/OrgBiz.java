package com.github.wxiaoqi.security.admin.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.admin.entity.OrgClassifyBiz;
import com.github.wxiaoqi.security.admin.mapper.OrgMapper;
import com.github.wxiaoqi.security.api.entity.Banner;
import com.github.wxiaoqi.security.api.entity.Org;
import com.github.wxiaoqi.security.api.vo.OrgVo;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: OrgBiz <br/>
 * Function: 机构服务类 <br/>
 * date: 2017年10月19日 下午6:13:37 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class OrgBiz extends BaseBiz<OrgMapper, Org> {

	@Autowired
	private OrgClassifyBiz orgClassifyBiz;
	
	@Autowired
	private CourseBiz courseBiz;
	
	public TableResultResponse<Org> selectByQueryAndOrgName(Query query,String orgName) {
        Class<Org> clazz = (Class<Org>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(orgName)){
			criteria.andLike("orgName", "%" + orgName + "%");
		}
        for (Map.Entry<String, Object> entry : query.entrySet()) {
        	if(StringUtils.isNotEmpty(entry.getValue().toString())){
        		criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
        	}
        }
        example.orderBy("id").desc();
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<Org> list = mapper.selectByExample(example);
        for(Org org : list){
        	if(org.getOrgLable()!=null){
        		org.setOrgLables(org.getOrgLable().split(","));
        	}
			String[] pics = org.getOrgPic() == null ? new String[0] : org.getOrgPic().split(",");
    		List<Banner> bList = new ArrayList<Banner>();
    		for(int i=0;i<pics.length;i++){
    			Banner banner = new Banner();
    			banner.setId(Long.valueOf(i+1));
    			banner.setImgurl(pics[i]);
    			bList.add(banner);
    		}
    		org.setOrgPics(bList);
        }
        
        return new TableResultResponse<Org>(result.getTotal(), list);
    }
	
	public Org getOrgById(Long id){
		return selectById(id); 
	}
	
	public void updateOrgClassifyIds(Long orgId) {
		Org org = selectById(orgId);
		List<Long> list = courseBiz.findOrgClassifyIdsByOrgId(orgId);
		if (list != null && !list.isEmpty()) {
			String str = "";
			for (Long l : list) {
				str += l + ",";
			}
			org.setOrgClassifyId(str.substring(0, str.length() - 1));
		} else {
			org.setOrgClassifyId("");
		}
		updateById(org);
	}

	public List<Org> selectQrCodeUrlEmpty() {
		return mapper.selectQrCodeUrlEmpty();
	}

	public Map<String, Object> findOrgByTeacherIdAndPage(Long teacherId, int limit, int page) {
		List<OrgVo> teacherList = mapper.findOrgByTeacherIdAndPage(teacherId, limit, page-1);
		Long total = mapper.findOrgByTeacherId(teacherId);
		Map<String,Object> resultPage = new HashMap<String,Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		resultPage.put("total",total);
	    resultPage.put("data",teacherList);
		return resultPage;
	}

	public List<Org> selectAll() {
		return mapper.selectAll();
	}
}
