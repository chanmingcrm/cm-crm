package com.platform.mesh.security.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * @description OAuthClientException 异常信息
 * @author 蝉鸣
 */
public class OAuthClientException extends OAuth2AuthenticationException {

	/**
	 * 功能描述:
	 * 〈授权客户端异常〉
	 * @param msg msg
	 * @author 蝉鸣
	 */
	public OAuthClientException(String msg) {
		super(new OAuth2Error(msg), msg);
	}

	/**
	 * 功能描述:
	 * 〈授权客户端异常〉
	 * @param msg msg
	 * @param cause cause
	 * @author 蝉鸣
	 */
	public OAuthClientException(String msg, Throwable cause) {
		super(new OAuth2Error(msg), cause);
	}

}