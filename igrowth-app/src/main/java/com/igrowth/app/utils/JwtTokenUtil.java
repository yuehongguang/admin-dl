package com.igrowth.app.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.wxiaoqi.security.common.util.jwt.IJWTInfo;
import com.github.wxiaoqi.security.common.util.jwt.JWTHelper;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class JwtTokenUtil {

    @Value("${jwt.expire}")
    private int expire;
    @Value("${jwt.pri-key.path}")
    private String priKeyPath;
    @Value("${jwt.pub-key.path}")
    private String pubKeyPath;

    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo,priKeyPath,expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return JWTHelper.getInfoFromToken(token,pubKeyPath);
    }


}
