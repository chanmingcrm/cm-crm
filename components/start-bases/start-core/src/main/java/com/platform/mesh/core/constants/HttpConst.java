package com.platform.mesh.core.constants;

/**
 * @description 自定义常量
 * @author 蝉鸣
 */
public interface HttpConst {


	String HTTP = "http";

	String HTTPS = "https";

	/*=============================================通用请求方法变量=============================================*/

	 String METHOD_DELETE = "DELETE";

	 String METHOD_HEAD = "HEAD";

	 String METHOD_GET = "GET";

	 String METHOD_OPTIONS = "OPTIONS";

	 String METHOD_POST = "POST";

	 String METHOD_PUT = "PUT";

	 String METHOD_TRACE = "TRACE";

	/*=============================================通用请求头变量=============================================*/

	/**
	 *  accept
	 */
	String ACCEPT = "accept";

	/**
	 *  X-Requested-With
	 */
	String X_REQUESTED_WITH = "X-Requested-With";

	/**
	 *  X-Requested-For
	 */
	String X_FORWARDED_FOR = "X-Forwarded-For";

	/**
	 *  XMLHttpRequest
	 */
	String XML_HTTP_REQUEST = "XMLHttpRequest";

	/**
	 *  Proxy-Client-IP
	 */
	String PROXY_CLIENT_IP = "Proxy-Client-IP";

	/**
	 *  WL-Proxy-Client-IP
	 */
	String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

	/**
	 *  HTTP_CLIENT_IP
	 */
	String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

	/**
	 *  X-Real-IP
	 */
	String X_REAL_IP = "X-Real-IP";

	/**
	 *  __ajax
	 */
	String AJAX = "__ajax";

	/**
	 *  application/json
	 */
	String APPLICATION_JSON = "application/json";

	/**
	 *  application/json; charset=utf-8
	 */
	String APPLICATION_JSON_CHARSET = "application/json; charset=UTF-8";
	/**
	 *  下载
	 */
	String APPLICATION_STREAM_CHARSET = "application/octet-stream; charset=UTF-8";
	/**
	 *  excel导出
	 */
	String CONTENT_TYPE_SHEET = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/**
	 *  Content-Disposition
	 */
	String CONTENT_DISPOSITION = "Content-Disposition";

	/**
	 *  文件头前缀
	 */
	String FILE_NAME_PREFIX = "attachment; filename=";

	/**
	 *  Excel文件头前缀
	 */
	String FILE_NAME_PREFIX_SHEET= "attachment;filename*=utf-8''";

	/*=============================================请求地址变量=============================================*/
	/**
	 *  默认ip地址
	 */
	String DEFAULT_IP = "127.0.0.1";
	/**
	 *  默认Mac地址
	 */
	String DEFAULT_MAC = "0:0:0:0:0:0:0:1";

	/*=============================================鉴权请求头变量=============================================*/


	/**
	 * 授权key
	 */
	String AUTHORIZATION = "Authorization";

	/**
	 * 请求头用户key
	 */
	String LOGIN_USER = "login-user";

	/**
	 * 内部请求标志
	 */
	String REQUEST_SOURCE = "request-source";

	/**
	 * 内部请求
	 */
	String INNER = "inner";

	/**
	 * 请求header
	 */
	String HEADER_FROM_IN = REQUEST_SOURCE + "=" + INNER;

}
