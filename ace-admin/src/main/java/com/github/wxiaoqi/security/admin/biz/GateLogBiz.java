package com.github.wxiaoqi.security.admin.biz;

import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.admin.entity.GateLog;
import com.github.wxiaoqi.security.admin.mapper.GateLogMapper;
import com.github.wxiaoqi.security.common.biz.BaseBiz;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-07-01 14:36
 */
@Service
public class GateLogBiz extends BaseBiz<GateLogMapper,GateLog> {


    @Override
    public void insertSelective(GateLog entity) {
        mapper.insertSelective(entity);
    }
}
