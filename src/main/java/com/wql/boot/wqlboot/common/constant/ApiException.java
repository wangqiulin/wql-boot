package com.wql.boot.wqlboot.common.constant;

/**
 * 自定义异常
 * @author wangqiulin
 *
 */
public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误code
	 */
	private String code;
	
	/**
	 * 错误原因
	 */
	private String msg;

	public ApiException(ApiEnum busEnum) {
		this.code = busEnum.getCode();
		this.msg = busEnum.getMsg();
	}
	
	public ApiException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
