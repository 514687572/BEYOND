package com.stip.net.token;

/**
 * Title: 自定义的RuntimeException
 * Description:Token过期时抛出
 * @author chenjunan
 * @created 2017年7月4日 下午4:56:44
 */
public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String msg;

	public TokenException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
