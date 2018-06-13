package com.github.wxiaoqi.security.admin.biz;

import com.github.wxiaoqi.security.admin.entity.User;
import com.github.wxiaoqi.security.admin.mapper.UserMapper;
import com.github.wxiaoqi.security.admin.vo.ChildVO;
import com.github.wxiaoqi.security.auth.client.jwt.UserAuthUtil;
import com.github.wxiaoqi.security.common.biz.BaseBiz;
import com.github.wxiaoqi.security.common.constant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-08 16:23
 */
@Service
public class UserBiz extends BaseBiz<UserMapper,User> {

    /*@Autowired
    private MenuMapper menuMapper;*/
    @Autowired
    private UserAuthUtil userAuthUtil;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void insertSelective(User entity) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
        entity.setPassword(password);
        super.insertSelective(entity);
    }

    @Override
    public void updateSelectiveById(User entity) {

//        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
//        entity.setPassword(password);
        super.updateSelectiveById(entity);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public User getUserByUsername(String username){
        User user = new User();
        user.setUsername(username);
        return mapper.selectOne(user);
    }

    /**
     * 根据手机号获取孩子信息
     * @param cellphone
     * @return
     */
    public List<ChildVO> getChildByPhone(String cellphone){
        return userMapper.getChildByPhone(cellphone);
    }

    public Boolean phoneExist(String cellphone){
        return userMapper.phoneExist(cellphone)>0?Boolean.TRUE:Boolean.FALSE;
    }
}
