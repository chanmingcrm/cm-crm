package com.platform.mesh.core.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.ServletRequestPathUtils;

/**
 * 功能描述:
 * 〈请求路径规范化工具〉
 * @author qingfeng
 */
public final class CanonicalRequestPath {

    private CanonicalRequestPath() {
    }

    /**
     * 功能描述:
     * 〈获取不含矩阵参数的应用内请求路径〉
     *
     * @param request HTTP 请求
     * @return 规范化应用路径
     * @author qingfeng
     */
    public static String applicationPath(HttpServletRequest request) {
        return withoutMatrixParameters(ServletRequestPathUtils.parse(request).pathWithinApplication());
    }

    /**
     * 功能描述:
     * 〈移除指定路径中的矩阵参数〉
     *
     * @param path 原始路径
     * @return 规范化路径
     * @author qingfeng
     */
    public static String canonicalize(String path) {
        return withoutMatrixParameters(PathContainer.parsePath(path));
    }

    private static String withoutMatrixParameters(PathContainer path) {
        StringBuilder canonical = new StringBuilder();
        for (PathContainer.Element element : path.elements()) {
            if (element instanceof PathContainer.PathSegment segment) {
                canonical.append(segment.valueToMatch());
            }
            else {
                canonical.append(element.value());
            }
        }
        return canonical.toString();
    }
}
