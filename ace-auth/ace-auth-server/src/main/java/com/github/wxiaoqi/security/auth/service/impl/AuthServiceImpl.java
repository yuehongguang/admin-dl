package com.github.wxiaoqi.security.auth.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.api.authority.PermissionInfo;
import com.github.wxiaoqi.security.api.user.UserInfo;
import com.github.wxiaoqi.security.auth.feign.IAccountService;
import com.github.wxiaoqi.security.auth.feign.IUserService;
import com.github.wxiaoqi.security.auth.service.AuthService;
import com.github.wxiaoqi.security.auth.util.user.JwtTokenUtil;
import com.github.wxiaoqi.security.auth.vo.FrontUser;
import com.github.wxiaoqi.security.common.constant.CommonConstants;
import com.github.wxiaoqi.security.common.util.jwt.JWTInfo;

@Service
public class AuthServiceImpl implements AuthService {

    private JwtTokenUtil jwtTokenUtil;
    private IUserService userService;
    private IAccountService  accountService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    
	@Autowired
	private StringRedisTemplate redisTemplate;
 	
    @Autowired
    public AuthServiceImpl(
            JwtTokenUtil jwtTokenUtil,
            IUserService userService,
            IAccountService accountService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public String adminLogin(String username, String password) throws Exception {
        UserInfo info = userService.getUserByUsername(username);
        String token = "";
        if (encoder.matches(password, info.getPassword())) {
            token = jwtTokenUtil.generateToken(new JWTInfo(info.getUsername(), info.getId() + "", info.getName()));
        }
        return token;
    }
    @Override
    public String appLogin(String username, String password) throws Exception {
        UserInfo info = accountService.getUserByUsername(username);
        String token = "";
        if (encoder.matches(password, info.getPassword())) {
            token = jwtTokenUtil.generateToken(new JWTInfo(info.getUsername(), info.getId() + "", info.getName()));
        }
        return token;
    }

    /*@Override
    public String apiLogin(String username, String password) throws Exception {
        Account account = accountService.getAccountByAccountName(username);
        String token = "";
        if (encoder.matches(password, account.getPassword())) {
            token = jwtTokenUtil.generateToken(new JWTInfo(account.getAccountName(), account.getId() + "", account.getName()));
        }
        return token;
    }*/

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    @Override
    public FrontUser getUserInfo(String token) throws Exception {
        String username = jwtTokenUtil.getInfoFromToken(token).getUniqueName();
        if (username == null) {
            return null;
        }
        UserInfo user = userService.getUserByUsername(username);
        FrontUser frontUser = new FrontUser();
        BeanUtils.copyProperties(user, frontUser);
        List<PermissionInfo> permissionInfos = userService.getPermissionByUsername(username);
        Stream<PermissionInfo> menus = permissionInfos.parallelStream().filter((permission) -> {
            return permission.getType().equals(CommonConstants.RESOURCE_TYPE_MENU);
        });
        frontUser.setMenus(menus.collect(Collectors.toList()));
        Stream<PermissionInfo> elements = permissionInfos.parallelStream().filter((permission) -> {
            return !permission.getType().equals(CommonConstants.RESOURCE_TYPE_MENU);
        });
        frontUser.setElements(elements.collect(Collectors.toList()));
        return frontUser;
    }

    @Override
    public Boolean invalid(String token) {
        // TODO: 2017/9/11 注销token
        return null;
    }

    @Override
    public String refresh(String oldToken) {
        // TODO: 2017/9/11 刷新token
        return null;
    }

	@Override
	public String phoneLogin(String phone, String identifyCode) throws Exception {
		UserInfo info = accountService.getUserByUsername(phone);
		String token = "";
		if(identifyCode(phone, identifyCode)){
			token = jwtTokenUtil.generateToken(new JWTInfo(info.getUsername(), info.getId() + "", info.getName()));
		}
		return token;
	}
	
	private boolean identifyCode(String phone, String identifyCode) {
		String randomkey = String.format(CommonConstants.RANDOMKEY, phone);
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		if (redisTemplate.hasKey(randomkey) && identifyCode.equals(ops.get(randomkey))) {
			return true;
		}
		return false;
	}
}
