package com.github.wxiaoqi.security.auth.feign;

import com.github.wxiaoqi.security.api.user.UserInfo;
import com.github.wxiaoqi.security.auth.configuration.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-21 8:11
 */

@FeignClient(value = "igrowth-app",configuration = FeignConfiguration.class)
public interface IAccountService {
  @RequestMapping(value = "/api/account/accountName/{accountName}", method = RequestMethod.GET)
  public UserInfo getUserByUsername(@PathVariable("accountName") String accountName);
}
