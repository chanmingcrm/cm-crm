package com.platform.mesh.security.constants;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

/**
 * @description 权限相关常量
 * @author 蝉鸣
 */
public interface SecurityConstant {



	/*=============================================项目通用变量=============================================*/

	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";

	/**
	 * 项目的license
	 */
	String PROJECT_LICENSE = "https://www.baidu.com";

	/**
	 * 登录成功
	 */
	String LOGIN_SUCCESS = "0";

	/**
	 * 登录失败
	 */
	String LOGIN_FAIL = "1";

	/**
	 * 退出成功
	 */
	String LOGOUT_SUCCESS = "2";



	/*=============================================授权服务变量=============================================*/

	/**
	 * Basic
	 */
	String BASIC = "Basic";

	/**
	 * Bearer
	 */
	String BEARER = "Bearer";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";

	/**
	 * {noop} 加密的特征码
	 */
	String NOOP = "{noop}";

	/*=============================================授权URL=============================================*/
	/**
	 * 默认登录URL
	 */
	String OAUTH_TOKEN_URL = "/oauth2/token";

	/**
	 * 授权码模式confirm
	 */
	String CUSTOM_CONSENT_PAGE_URI = "/token/confirm_access";

	/*=============================================授权client变量=============================================*/
	/**
	 * 客户端ID
	 */
	String CLIENT_ID = "clientId";

	/*=============================================授权用户变量=============================================*/
	/**
	 * 认证属性
	 */
	String PRINCIPAL = "java.security.Principal";
	/**
	 * 匿名用户
	 */
	String ANONYMOUS_USER = "anonymousUser";
	/**
	 * 用户信息
	 */
	String DETAILS_USER = "user_info";
	/**
	 * 账户来源
	 */
	String SOURCE_FLAG = "sourceFlag";
	/**
	 * 用户信息
	 */
	List<String> STATIC_IGNORE_URLS = CollUtil.newArrayList(
			"/api/token/*",
			"/captcha/*",
			"/sms/send",
			"/token/**",
			"/actuator/**",
			"/assets/**", "/error",
			"/v3/api-docs/**",
			"/tenant/client/info",
			"/tenant/base/register"
	);
}
