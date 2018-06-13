package com.igrowth.app.rpc;


import com.github.wxiaoqi.security.api.entity.Account;
import com.github.wxiaoqi.security.api.entity.AppUrl;
import com.github.wxiaoqi.security.api.user.UserInfo;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.igrowth.app.biz.AppUrlBiz;
import com.igrowth.app.rpc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-21 8:15
 */
@IgnoreUserToken
@IgnoreClientToken
@RestController
@RequestMapping("api")
public class AccountRest {
	
	@Autowired
    private AccountService accountService;
    @Autowired
    private AppUrlBiz appUrlBiz;

    @RequestMapping(value = "/account/accountName/{accountName}",method = RequestMethod.GET, produces="application/json")
    public UserInfo getUserByUsername(@PathVariable("accountName") String accountName) {
        UserInfo info = new UserInfo();
        String cellphone = accountName;
        Account account = new Account();
        account.setCellphone(cellphone);
        account = accountService.selectOne(account);
        info.setUsername(account.getCellphone());
        info.setName(account.getAccountName());
        info.setPassword(account.getAccountPassword());
        info.setId(account.getId().toString());
        info.setChildId(account.getCurrentCid());
        return info;
    }

    @RequestMapping(value = "/igrowth/url", method = RequestMethod.GET)
    public List<AppUrl> getAllAppLoginUrl(){
        return appUrlBiz.findAppLoginUrl();
    };
}
