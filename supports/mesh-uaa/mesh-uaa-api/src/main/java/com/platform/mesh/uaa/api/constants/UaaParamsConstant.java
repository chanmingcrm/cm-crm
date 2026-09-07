package com.platform.mesh.uaa.api.constants;

/**
 * @description UaaParamsConstant   参数变量
 * @author 蝉鸣
 */
public interface UaaParamsConstant {

	/*=============================================授权返回URL=============================================*/
	//
	/**
	 * 账户名称
	 */
	String LOGIN = "/login";

	/**
	 * 账户密码
	 */
	String CONFIRM = "/confirm";

	/*=============================================授权参数变量=============================================*/
	//Oauth2默认接收username password
	//系统用账户校验字段
	/**
	 * 账户名称
	 */
	String ACCOUNT_CODE = "accountCode";

	/**
	 * 账户密码
	 */
	String CHECK_CODE = "checkCode";

	/**
	 * 短信登录 参数名称
	 */
	String SMS_PARAMETER_NAME = "mobile";

	/**
	 * 短信验证码
	 */
	String SMS_CODE = "smsCode";

	/**
	 * 授权人名称
	 */
	String PRINCIPAL_NAME = "principalName";

	/**
	 * 第三方登录 参数名称
	 */
	String THIRD_PARAMETER_UUID = "uuid";

	/**
	 * 第三方登录 参数客户绑定ID
	 */
	String THIRD_PARAMETER_CLIENT_ID = "appId";

	/**
	 * 第三方登录 参数客户绑定CODE
	 */
	String THIRD_PARAMETER_CLIENT_CODE = "code";

	/**
	 * 静默登录
	 */
	String SILENCE = "silence";

	/**
	 * 企微应用ID
	 */
	String AGENT_ID = "agentId";
}
