package com.platform.mesh.security.constants;

/**
 * @description GrantType   基本AuthorizationGrantType + 自定义 GrantType
 * @author 蝉鸣
 */
public interface GrantTypeConstant {

	/*=============================================授权GrantType类型=============================================*/

	/**
	 * grant_type
	 */
	String AUTHORIZATION_CODE = "authorization_code";

	/**
	 * grant_type
	 */
	String CLIENT_CREDENTIALS = "client_credentials";

	/**
	 * grant_type
	 */
	String REFRESH_TOKEN = "refresh_token";

	/**
	 * grant_type
	 */
	String PASSWORD = "password";

	/**
	 * grant_type
	 */
	String JWT_BEARER = "urn:ietf:params:oauth:grant-type:jwt-bearer";

	/**
	 * 手机号登录
	 */
	String SMS = "sms";

	/**
	 * 第三方登录
	 */
	String THIRD = "third";

}
