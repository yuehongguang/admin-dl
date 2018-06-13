package com.github.wxiaoqi.security.common.msg;

/**
 * Created by ace on 2017/8/23.
 */
public class AppResponse<T> {
	private int status = 200;
	private String message;
	private T result;

	public AppResponse(int status, String message, T result) {
		this.status = status;
		this.message = message;
		this.result = result;
	}

	public AppResponse() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
