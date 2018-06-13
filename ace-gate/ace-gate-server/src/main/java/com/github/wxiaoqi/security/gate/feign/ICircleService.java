package com.github.wxiaoqi.security.gate.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.wxiaoqi.security.gate.config.ZuulConfig;


/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-21 8:11
 */
@FeignClient(value = "igrouth-circle",configuration = {ZuulConfig.class})
@RequestMapping("api")
public interface ICircleService {
  @RequestMapping(value = "/circle/test/{username}", method = RequestMethod.POST)
  public void getTestName(@PathVariable("username") String username);

}
