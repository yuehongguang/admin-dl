package com.igrowth.app.biz;

import com.github.wxiaoqi.security.api.entity.AppUrl;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.igrowth.app.mapper.AppUrlMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/1311:40
 *    desc    : 输入描述
 * </pre>
 */
@Service
public class AppUrlBiz extends BaseBiz<AppUrlMapper, AppUrl> {

    @Cacheable(value="loginUrls", key="'appLoginUrl'")
    public List<AppUrl> findAppLoginUrl(){
       return mapper.selectAll();
    }
}
