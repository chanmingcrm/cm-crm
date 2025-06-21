package com.platform.mesh.security.utils;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * @description
 * @author 蝉鸣
 */
public class ScopeException extends OAuth2AuthenticationException {

	/**
	 * 功能描述:
	 * 〈作用域异常〉
	 * @param msg msg
	 * @author 蝉鸣
	 */
	public ScopeException(String msg) {
		super(new OAuth2Error(msg), msg);
	}

	/**
	 * 功能描述:
	 * 〈作用域异常〉
	 * @param msg msg
	 * @param cause cause
	 * @author 蝉鸣
	 */
	public ScopeException(String msg, Throwable cause) {
		super(new OAuth2Error(msg), cause);
	}

}