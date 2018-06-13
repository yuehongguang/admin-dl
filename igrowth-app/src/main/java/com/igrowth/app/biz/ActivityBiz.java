package com.igrowth.app.biz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.api.entity.Activity;
import com.igrowth.app.mapper.ActivityMapper;
import com.github.wxiaoqi.security.common.biz.BaseBiz;

/**
 * 
 * ClassName: OrgBiz <br/>
 * Function: 活动服务类 <br/>
 * date: 2017年10月19日 下午6:13:37 <br/>
 * 
 * @author dingshuyan
 * @version
 * @since JDK 1.8
 */
@Service
public class ActivityBiz extends BaseBiz<ActivityMapper, Activity> {

	public List<Activity> selectActivityByOrgId(Long id) {
		return mapper.selectActivityByOrgId(id);
	}

	public Activity getActivity(Integer id) {
		return selectById(id);
	}
}
