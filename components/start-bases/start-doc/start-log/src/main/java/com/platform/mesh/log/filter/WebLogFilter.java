package com.platform.mesh.log.filter;

import com.platform.mesh.core.constants.HttpConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 功能描述:
 * 〈备用 Web 请求日志过滤器，当前未注册到过滤器链〉
 *
 * @author 蝉鸣
 */
public class WebLogFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(WebLogFilter.class);
    private static final int MAX_PAYLOAD_LENGTH = 8192;
    private static final String MASK_VALUE = "******";
    private static final String TRUNCATED_SUFFIX = "...(已截断)";
    private static final List<String> IGNORE_PATH_PREFIXES = List.of(
            "/actuator/health", "/doc/file/download/", "/doc/file/preview/");
    private static final Pattern SENSITIVE_JSON_PATTERN = Pattern.compile(
            "(?i)(\\\"(?:password|passwd|pwd|secret|token|accessToken|refreshToken|apiKey|clientSecret)"
                    + "\\\"\\s*:\\s*\\\")([^\\\"]*)(\\\")");

    /**
     * 功能描述:
     * 〈记录请求摘要，不缓存响应正文〉
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param filterChain 过滤器链
     * @author qingfeng
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (shouldBypass(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper(request, MAX_PAYLOAD_LENGTH);
        long startedAt = System.nanoTime();
        try {
            filterChain.doFilter(requestWrapper, response);
        }
        finally {
            log.info("Web请求 method={} uri={} status={} elapsedMs={} token={} request={}",
                    request.getMethod(), request.getRequestURI(), response.getStatus(),
                    elapsedMillis(startedAt), maskToken(request.getHeader(HttpConst.AUTHORIZATION)),
                    requestBody(requestWrapper));
        }
    }

    /**
     * 功能描述:
     * 〈判断是否跳过日志处理〉
     * @param request HTTP 请求
     * @return 是否跳过
     * @author qingfeng
     */
    private boolean shouldBypass(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return IGNORE_PATH_PREFIXES.stream().anyMatch(requestUri::startsWith)
                || isStreamingOrBinary(request.getContentType())
                || isStreamingOrBinary(request.getHeader(HttpConst.ACCEPT));
    }

    /**
     * 功能描述:
     * 〈判断是否为流式、表单或二进制请求〉
     * @param contentType 媒体类型
     * @return 是否跳过正文处理
     * @author qingfeng
     */
    private boolean isStreamingOrBinary(String contentType) {
        if (contentType == null) {
            return false;
        }
        String normalized = contentType.toLowerCase(Locale.ROOT);
        return normalized.contains(MediaType.TEXT_EVENT_STREAM_VALUE)
                || normalized.contains(MediaType.MULTIPART_FORM_DATA_VALUE)
                || normalized.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                || normalized.contains(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                || normalized.contains("application/x-ndjson");
    }

    /**
     * 功能描述:
     * 〈读取、限长并脱敏请求正文〉
     * @param requestWrapper 请求缓存包装器
     * @return 安全的请求正文摘要
     * @author qingfeng
     */
    private String requestBody(ContentCachingRequestWrapper requestWrapper) {
        byte[] bytes = requestWrapper.getContentAsByteArray();
        if (bytes.length == 0) {
            return "-";
        }
        Charset charset = requestWrapper.getCharacterEncoding() == null
                ? StandardCharsets.UTF_8 : Charset.forName(requestWrapper.getCharacterEncoding());
        String content = new String(bytes, charset);
        boolean truncated = requestWrapper.getContentLengthLong() > bytes.length
                || content.length() > MAX_PAYLOAD_LENGTH;
        if (content.length() > MAX_PAYLOAD_LENGTH) {
            content = content.substring(0, MAX_PAYLOAD_LENGTH);
        }
        String sanitized = SENSITIVE_JSON_PATTERN.matcher(content)
                .replaceAll("$1" + MASK_VALUE + "$3");
        return truncated ? sanitized + TRUNCATED_SUFFIX : sanitized;
    }

    /**
     * 功能描述:
     * 〈对认证信息进行掩码处理〉
     * @param token 认证信息
     * @return 掩码后的认证信息
     * @author qingfeng
     */
    private String maskToken(String token) {
        if (token == null || token.isBlank()) {
            return "-";
        }
        if (token.length() <= 8) {
            return "****";
        }
        return token.substring(0, 4) + "****" + token.substring(token.length() - 4);
    }

    /**
     * 功能描述:
     * 〈计算请求耗时〉
     * @param startedAt 请求开始时间
     * @return 请求耗时毫秒数
     * @author qingfeng
     */
    private long elapsedMillis(long startedAt) {
        return (System.nanoTime() - startedAt) / 1_000_000L;
    }
}
