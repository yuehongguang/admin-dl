package com.github.wxiaoqi.security.auth.util.user;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String expire;

    private final String token;
    
    private final boolean result;

    public JwtAuthenticationResponse(String token,String expire,boolean result) {
        this.token = token;
        this.expire = expire;
        this.result = result;
    }

    public String getToken() {
        return this.token;
    }

    public String getExpire() {
        return expire;
    }
 
	public boolean getResult() {
		return result;
	}
    
    
}
