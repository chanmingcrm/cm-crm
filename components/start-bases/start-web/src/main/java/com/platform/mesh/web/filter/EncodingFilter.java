package com.platform.mesh.web.filter;

import com.platform.mesh.core.constants.HttpConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @description 配置字符编码
 * @author 蝉鸣
 */
@Component
public class EncodingFilter extends OncePerRequestFilter {

    /**
     * 功能描述:
     * 〈配置字符编码〉
     * @param request request
     * @param response response
     * @param filterChain filterChain
     * @author 蝉鸣
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            //设置 POST请求体编码
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());

            //设置响应编码
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(HttpConst.APPLICATION_JSON_CHARSET);
            // Feign 开启请求压缩时，请求体会以 gzip 传输，需要先解压再交给后续 MVC 解析。
            if (isGzipRequest(request)) {
                filterChain.doFilter(new GzipRequestWrapper(request),response);
                return;
            }
            filterChain.doFilter(request,response);
    }

    /**
     * 功能描述:
     * 〈判断请求体是否使用 gzip 编码〉
     * @param request request
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    private boolean isGzipRequest(HttpServletRequest request) {
        String contentEncoding = request.getHeader(HttpHeaders.CONTENT_ENCODING);
        return contentEncoding != null && contentEncoding.toLowerCase(Locale.ROOT).contains("gzip");
    }

}
