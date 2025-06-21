package com.platform.mesh.utils.http;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.utils.spring.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @description IP工具类
 * @author 蝉鸣
 */
public class IpUtil {

	private static final Logger log = LoggerFactory.getLogger(IpUtil.class);

	private static final String UNKNOWN = "unknown";

	/**
	 * 获取客户端IP
	 * @return IP
	 */
	public static String getIpAddr() {
		return getIpAddr(ServletUtil.getRequestInst());
	}

	/**
	 * 获取访问代理
	 * @return IP
	 */
	public static String getIpAgent() {
		return ServletUtil.getRequestInst().getHeader(HttpHeaders.USER_AGENT);
	}

	/**
	 * 获取客户端IP地址
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static String getIpAddr(HttpServletRequest request) {

		if (request == null) {
			return UNKNOWN;
		}

		String ip = null;

		// X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader(HttpConst.X_FORWARDED_FOR);
		if (StrUtil.isEmpty(ipAddresses) || ipAddresses.isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			// Proxy-Client-IP：apache 服务代理
			ipAddresses = request.getHeader(HttpConst.PROXY_CLIENT_IP);
		}
		if (StrUtil.isEmpty(ipAddresses) || ipAddresses.isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			// WL-Proxy-Client-IP：weblogic 服务代理
			ipAddresses = request.getHeader(HttpConst.WL_PROXY_CLIENT_IP);
		}
		if (StrUtil.isEmpty(ipAddresses) || ipAddresses.isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			// HTTP_CLIENT_IP：有些代理服务器
			ipAddresses = request.getHeader(HttpConst.HTTP_CLIENT_IP);
		}
		if (StrUtil.isEmpty(ipAddresses) || ipAddresses.isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			// X-Real-IP：nginx服务代理
			ipAddresses = request.getHeader(HttpConst.X_REAL_IP);
		}

		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ObjectUtil.isNotEmpty(ipAddresses) && !ipAddresses.isEmpty()) {
			ip = ipAddresses.split(SymbolConst.COMMA)[NumberConst.NUM_0];
		}

		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ObjectUtil.isEmpty(ip) || Objects.requireNonNull(ip).isEmpty() || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
		}
		return HttpConst.DEFAULT_MAC.equals(ip) ? HttpConst.DEFAULT_IP : ip;
	}

	/**
	 * 获得服务器IP
	 * @return String
	 */
	public static String getHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
			return HttpConst.DEFAULT_IP;
		}
	}

	/**
	 * 获取服务器名称
	 * @return String
	 */
	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		}
		catch (UnknownHostException e) {
			return "未知";
		}
	}

}
