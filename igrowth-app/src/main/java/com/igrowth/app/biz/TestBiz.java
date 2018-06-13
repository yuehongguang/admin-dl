package com.igrowth.app.biz;

import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.api.entity.Test;
import com.igrowth.app.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-08 16:23
 */
@Service
public class TestBiz extends BaseBiz<TestMapper,Test> {


    @Override
    public void insertSelective(Test entity) {
        /*String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
        //entity.setPassword(password);
        super.insertSelective(entity);*/
    }

    @Override
    public void updateSelectiveById(Test entity) {

//        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
//        entity.setPassword(password);
        super.updateSelectiveById(entity);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public Test getUserByUsername(String username){
    	Test user = new Test();
        return mapper.selectOne(user);
    }


}
