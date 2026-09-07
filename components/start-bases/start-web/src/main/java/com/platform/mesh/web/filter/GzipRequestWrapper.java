package com.platform.mesh.web.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

/**
 * @description gzip 请求体包装器，用于将压缩请求体解压后继续交给 MVC 读取
 * @author 蝉鸣
 */
public class GzipRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 解压后的请求体字节
     */
    private final byte[] body;

    /**
     * 功能描述:
     * 〈读取并解压 gzip 请求体〉
     * @param request request
     * @throws IOException IO异常
     * @author 蝉鸣
     */
    public GzipRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(request.getInputStream())) {
            this.body = StreamUtils.copyToByteArray(gzipInputStream);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        // 将解压后的字节重新包装成 ServletInputStream，供 Spring MVC/Jackson 读取。
        return new ServletInputStream() {
            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), getCharset()));
    }

    @Override
    public int getContentLength() {
        return body.length;
    }

    @Override
    public long getContentLengthLong() {
        return body.length;
    }

    @Override
    public String getHeader(String name) {
        // 请求体已解压，避免下游继续按 gzip 处理。
        if (isContentEncoding(name)) {
            return null;
        }
        if (isContentLength(name)) {
            return String.valueOf(body.length);
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        // 请求体已解压，避免下游继续按 gzip 处理。
        if (isContentEncoding(name)) {
            return Collections.emptyEnumeration();
        }
        if (isContentLength(name)) {
            return Collections.enumeration(List.of(String.valueOf(body.length)));
        }
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // 请求体已解压，移除 Content-Encoding 响应给下游的可见性。
        List<String> headerNames = Collections.list(super.getHeaderNames()).stream()
                .filter(headerName -> !isContentEncoding(headerName))
                .collect(Collectors.toList());
        return Collections.enumeration(headerNames);
    }

    private Charset getCharset() {
        String characterEncoding = getCharacterEncoding();
        if (characterEncoding == null) {
            return StandardCharsets.UTF_8;
        }
        return Charset.forName(characterEncoding);
    }

    private boolean isContentEncoding(String name) {
        return HttpHeaders.CONTENT_ENCODING.equalsIgnoreCase(name);
    }

    private boolean isContentLength(String name) {
        return HttpHeaders.CONTENT_LENGTH.equalsIgnoreCase(name);
    }
}
