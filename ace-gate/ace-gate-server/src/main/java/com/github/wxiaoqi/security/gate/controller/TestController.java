package com.github.wxiaoqi.security.gate.controller;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/11/1716:40
 *    desc    : 输入描述
 * </pre>
 */
@IgnoreUserToken
@IgnoreClientToken
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test2")
    public void test(HttpServletRequest request){
        StringBuffer url = request.getRequestURL();
        System.out.println(url);
        System.out.println("---gate +++++++ pass----");
    }
}
