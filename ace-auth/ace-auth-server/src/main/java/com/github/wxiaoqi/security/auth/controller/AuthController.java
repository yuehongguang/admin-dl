package com.github.wxiaoqi.security.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxiaoqi.security.auth.service.AuthService;
import com.github.wxiaoqi.security.auth.util.user.JwtAuthenticationRequest;
import com.github.wxiaoqi.security.auth.util.user.JwtAuthenticationResponse;
import com.github.wxiaoqi.security.auth.util.user.PhoneAuthenticationRequest;
import com.github.wxiaoqi.security.auth.vo.FrontUser;
import com.github.wxiaoqi.security.common.msg.AppResponse;

@RestController
@RequestMapping("jwt")
public class AuthController {
    @Value("${jwt.token-header}")
    private String tokenHeader;

    @Value("${jwt.expire}")
    private String expire;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "admin/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAdminAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        final String token = authService.adminLogin(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token,expire,true));
    }
    
    @RequestMapping(value = "app/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAppAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        final String token = authService.appLogin(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(StringUtils.isEmpty(token)){
        	return ResponseEntity.ok(new AppResponse(200, "用户名密码错误", false));
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(token,expire,true));
    }
    
    @RequestMapping(value = "app/phone/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAppAuthenticationToken(
            @RequestBody PhoneAuthenticationRequest authenticationRequest) throws Exception {
        final String token = authService.phoneLogin(authenticationRequest.getPhone(), authenticationRequest.getIdentifyCode());
        if(StringUtils.isEmpty(token)){
        	return ResponseEntity.ok(new AppResponse(200, "验证码错误", null));
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(token,expire,true));
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken,expire,true));
        }
    }

    @RequestMapping(value = "verify", method = RequestMethod.GET)
    public ResponseEntity<?> verify(String token) throws Exception {
        authService.validate(token);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "invalid", method = RequestMethod.POST)
    public ResponseEntity<?> invalid(@RequestHeader("access-token") String token){
        authService.invalid(token);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo(String token) throws Exception {
        FrontUser userInfo = authService.getUserInfo(token);
        if(userInfo==null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }
}
