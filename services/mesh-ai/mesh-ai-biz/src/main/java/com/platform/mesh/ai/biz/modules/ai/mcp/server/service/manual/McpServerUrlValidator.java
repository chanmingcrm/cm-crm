package com.platform.mesh.ai.biz.modules.ai.mcp.server.service.manual;

import com.platform.mesh.core.constants.HttpConst;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.URI;

/**
 * 功能描述:
 * 〈外部 MCP 服务地址校验器〉
 * @author qingfeng
 */
@Component
public class McpServerUrlValidator {

    /**
     * 功能描述:
     * 〈校验外部 MCP 服务地址并返回规范地址〉
     * @param value 服务基础地址
     * @return 规范服务地址
     * @author qingfeng
     */
    public URI validate(String value) {
        try {
            URI uri = URI.create(value).normalize();
            validateStructure(uri);
            validateResolvedAddresses(uri);
            return uri;
        }
        catch (IllegalArgumentException exception) {
            throw exception;
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("MCP 服务地址无法解析", exception);
        }
    }

    /**
     * 功能描述:
     * 〈校验 MCP 服务地址协议、主机及身份信息〉
     * @param uri MCP 服务地址
     * @author qingfeng
     */
    private void validateStructure(URI uri) {
        boolean http = HttpConst.HTTP.equalsIgnoreCase(uri.getScheme())
                || HttpConst.HTTPS.equalsIgnoreCase(uri.getScheme());
        if (!http || uri.getHost() == null || uri.getUserInfo() != null) {
            throw new IllegalArgumentException("MCP 服务地址仅支持 HTTP/HTTPS");
        }
    }

    /**
     * 功能描述:
     * 〈校验域名解析结果不包含本地或内网地址〉
     * @param uri MCP 服务地址
     * @throws Exception 域名解析失败
     * @author qingfeng
     */
    private void validateResolvedAddresses(URI uri) throws Exception {
        for (InetAddress address : InetAddress.getAllByName(uri.getHost())) {
            if (isPrivateAddress(address)) {
                throw new IllegalArgumentException("MCP 服务地址不允许访问本地或内网地址");
            }
        }
    }

    /**
     * 功能描述:
     * 〈判断地址是否属于禁止访问的本地或内网地址〉
     * @param address 待校验地址
     * @return 是否为禁止访问的地址
     * @author qingfeng
     */
    private boolean isPrivateAddress(InetAddress address) {
        return address.isAnyLocalAddress() || address.isLoopbackAddress()
                || address.isLinkLocalAddress() || address.isSiteLocalAddress()
                || address.isMulticastAddress();
    }
}
