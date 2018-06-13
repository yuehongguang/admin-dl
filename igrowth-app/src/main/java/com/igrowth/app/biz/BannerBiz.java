package com.igrowth.app.biz;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.api.entity.Banner;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.igrowth.app.mapper.BannerMapper;

/**
 * ${DESCRIPTION}
 * 
 * @author wanghaobin
 * @create 2017-06-23 20:27
 */
@Service
public class BannerBiz extends BaseBiz<BannerMapper, Banner> {
	public List<Banner> getBannerByTypeName(String typeName) {
		return mapper.selectByBannertype(typeName);
	}
}
