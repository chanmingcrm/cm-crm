package com.github.xiaoymin.knife4j.spring.gateway.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Spring Framework 7 compatible override for Knife4j Gateway 4.5.0 PathUtils.
 */
public class PathUtils {

	private static final Logger log = LoggerFactory.getLogger(PathUtils.class);

	static final String DOC_URL = "(.*?)\\/doc\\.html";

	public static final String DEFAULT_CONTEXT_PATH = "/";

	static final Pattern PATTERN = Pattern.compile(DOC_URL, Pattern.CASE_INSENSITIVE);

	public static String getContextPath(String url) {
		if (StringUtils.hasLength(url)) {
			try {
				URI uri = URI.create(url);
				String path = uri.getPath();
				java.util.regex.Matcher matcher = PATTERN.matcher(path);
				if (matcher.find()) {
					return matcher.group(1);
				}
			}
			catch (Exception e) {
				log.warn(e.getMessage());
			}
		}
		return DEFAULT_CONTEXT_PATH;
	}

	public static String append(String... paths) {
		if (Objects.isNull(paths) || paths.length == 0) {
			return DEFAULT_CONTEXT_PATH;
		}
		String path = Arrays.stream(paths)
				.filter(StringUtils::hasLength)
				.map(item -> DEFAULT_CONTEXT_PATH + item)
				.collect(Collectors.joining());
		return path.replaceAll("/+", DEFAULT_CONTEXT_PATH);
	}

	public static String getDefaultContextPath(ServerHttpRequest request) {
		String contextPath = request.getPath().contextPath().value();
		if (!StringUtils.hasLength(contextPath)) {
			String referer = request.getHeaders().getFirst(HttpHeaders.REFERER);
			if (StringUtils.hasLength(referer)) {
				log.debug("Referer:{}", referer);
				contextPath = getContextPath(referer);
			}
			else {
				contextPath = DEFAULT_CONTEXT_PATH;
			}
		}
		return contextPath;
	}

	public static String processContextPath(String contextPath) {
		String targetContextPath = contextPath;
		if (DEFAULT_CONTEXT_PATH.equals(targetContextPath)) {
			targetContextPath = "";
		}
		if (targetContextPath.endsWith(DEFAULT_CONTEXT_PATH)) {
			targetContextPath = targetContextPath.substring(0, targetContextPath.length() - 1);
		}
		return targetContextPath;
	}

	public static boolean contextPathNull(String contextPath) {
		return StringUtils.hasText(contextPath) && !DEFAULT_CONTEXT_PATH.equalsIgnoreCase(contextPath);
	}

	private PathUtils() {
	}
}
