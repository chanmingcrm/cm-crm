package com.platform.mesh.security.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * @description 用户已被冻结
 * @author 蝉鸣
 */
public class UserFrozenException extends AccessDeniedException {

	/**
	 * 功能描述:
	 * 〈账户异常信息〉
	 * @param msg msg
	 * @author 蝉鸣
	 */
	public UserFrozenException(String msg) {
		super(msg);
	}

	/**
	 * 功能描述:
	 * 〈账户异常信息〉
	 * @param msg msg
	 * @param cause cause
	 * @author 蝉鸣
	 */
	public UserFrozenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
