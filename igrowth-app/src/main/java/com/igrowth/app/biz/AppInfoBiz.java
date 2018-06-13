package com.igrowth.app.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.api.entity.AppInfo;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.igrowth.app.mapper.AppInfoMapper;

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
public class AppInfoBiz extends BaseBiz<AppInfoMapper, AppInfo> {

	public AppInfo findLatestAppInfo() {
		return mapper.findLatestAppInfo();
	}
	
}
