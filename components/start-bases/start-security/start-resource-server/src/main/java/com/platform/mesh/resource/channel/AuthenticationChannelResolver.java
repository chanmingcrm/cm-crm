package com.platform.mesh.resource.channel;

import com.platform.mesh.security.authentication.AuthenticationChannel;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.security.exception.SecurityExceptionEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * 功能描述:
 * 〈根据请求特征识别 Bearer、Access Key、Inner 或匿名通道，不负责校验凭证真实性〉
 *
 * @author qingfeng
 */
public class AuthenticationChannelResolver {

    /**
     * 功能描述:
     * 〈解析当前请求唯一的认证通道〉
     * @param request HTTP 请求
     * @return 请求对应的认证通道
     * @author qingfeng
     */
    public AuthenticationChannel resolve(HttpServletRequest request) {
        // 同一请求只允许一个 Authorization，避免代理合并后产生凭证歧义。
        List<String> values = request.getHeaders(HttpHeaders.AUTHORIZATION) == null
                ? List.of() : Collections.list(request.getHeaders(HttpHeaders.AUTHORIZATION));
        if (values.size() > 1) {
            throw SecurityExceptionEnum.AUTH_CHANNEL_MULTIPLE.getBaseException();
        }
        // Inner 仅表示内部调用来源，不参与认证方式竞争；存在凭证时仍按凭证完成认证。
        boolean inner = HttpConst.INNER.equals(request.getHeader(HttpConst.REQUEST_SOURCE));
        // 无 Authorization 时，内部调用交给 @AuthIgnore 识别，其他请求按匿名处理。
        if (values.isEmpty()) {
            return inner ? AuthenticationChannel.INNER : AuthenticationChannel.ANONYMOUS;
        }
        // 这里只识别认证方案，具体凭证由相应认证器校验。
        String value = values.getFirst().trim();
        String lower = value.toLowerCase(Locale.ROOT);
        if (lower.startsWith((StrConst.ACCESS_KEY + SymbolConst.SPACE).toLowerCase(Locale.ROOT))) {
            return AuthenticationChannel.ACCESS_KEY;
        }
        if (lower.startsWith((StrConst.BEARER + SymbolConst.SPACE).toLowerCase(Locale.ROOT))) {
            String token = value.substring(StrConst.BEARER.length() + 1).trim();
            if (token.isEmpty()) {
                throw SecurityExceptionEnum.AUTH_CREDENTIAL_EMPTY.getBaseException();
            }
            return AuthenticationChannel.BEARER;
        }
        throw SecurityExceptionEnum.AUTH_SCHEME_UNSUPPORTED.getBaseException();
    }
}
