package com.platform.mesh.utils.spring;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.utils.http.IpUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @description Servlet工具
 * @author 蝉鸣
 */
public class ServletUtil {

	private final static Logger log = LoggerFactory.getLogger(ServletUtil.class);

	/**
	 * 渲染json数据
	 * @param code 客户端状态码
	 * @param json json字符串
	 */
	public static void render(int code, String json) {
		HttpServletResponse response = getResponse();
		try {
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setStatus(code);
			response.setContentType(HttpConst.APPLICATION_JSON_CHARSET);
			response.getWriter().print(json);
		}
		catch (Exception ignore) {
		}
	}

	/**
	 * 获取request
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequestInst() {
		if(getRequest().isPresent()){
			return getRequest().get();
		}
		return getRequest().orElseThrow();
	}

	/**
	 * 获取request
	 * @return HttpServletRequest
	 */
	public static Optional<HttpServletRequest> getRequest() {
		return Optional
				.of(((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest());
	}

	/**
	 * 获取当前地址完整URI(注意是服务器的IP,便于查找出错机器)
	 * @return 当前完整访问地址
	 */
	public static String getHostRequestUri() {
		HttpServletRequest request = getRequestInst();
		return IpUtil.getHostIp() + SymbolConst.COLON + request.getServerPort() + request.getRequestURI();
	}

	/**
	 * 获取getResponse
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}

	/**
	 * 获取RequestAttributes
	 * @return ServletRequestAttributes
	 */
	public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
	}

	/**
	 * 获取session
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		return getRequestInst().getSession();
	}

	/**
	 * 获取json格式参数
	 * @return Map
	 */
	public static Map<String, String> getMapParam() {
		return getMapParam(getRequestInst());
	}

	/**
	 * 获取json格式参数
	 * @param httpServletRequest HttpServletRequest
	 * @return Map
	 */
	public static Map<String, String> getMapParam(HttpServletRequest httpServletRequest) {
		Map<String, String> map = new HashMap<>(NumberConst.NUM_16);
		// 获取所有参数名称
		Enumeration<String> enu = httpServletRequest.getParameterNames();
		// 遍历hash
		while (enu.hasMoreElements()) {
			String paramName = enu.nextElement();
			// 获取参数值
			String[] paramValues = httpServletRequest.getParameterValues(paramName);
			// 是否存在参数
			if (paramValues.length == NumberConst.NUM_1) {
				String paramValue = paramValues[NumberConst.NUM_0];
				if (paramValue.length() != NumberConst.NUM_0) {
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}

	/**
	 * 获取body数据
	 * @return String
	 */
	public static String getStrFromStream(HttpServletRequest req) {
		StringBuilder stringBuilder = new StringBuilder();
		try (ServletInputStream inputStream = req.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			char[] charBuffer = new char[NumberConst.NUM_128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) > NumberConst.NUM_0) {
				stringBuilder.append(charBuffer, NumberConst.NUM_0, bytesRead);
			}

			return stringBuilder.toString();
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
			return stringBuilder.toString();
		}
	}

	/**
	 * 内容编码
	 * @param str 内容
	 * @return 编码后的内容
	 */
	public static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

	/**
	 * 内容解码
	 * @param str 内容
	 * @return 解码后的内容
	 */
	public static String urlDecode(String str) {
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }

}
