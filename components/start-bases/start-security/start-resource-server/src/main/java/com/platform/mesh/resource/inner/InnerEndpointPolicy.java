package com.platform.mesh.resource.inner;

import com.platform.mesh.core.constants.HttpConst;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述:
 * 〈内部接口安全边界与规范请求策略〉
 *
 * @author qingfeng
 */
public class InnerEndpointPolicy {

    private final List<Rule> rules;

    /**
     * 功能描述:
     * 〈创建内部接口安全边界策略〉
     *
     * @author qingfeng
     */
    public InnerEndpointPolicy() {
        // 安全边界只识别受保护路径，HTTP 方法和规范路径在边界命中后单独校验。
        this.rules = List.of(
                new Rule(HttpConst.METHOD_POST,
                        Pattern.compile(Pattern.quote(HttpConst.INTERNAL_MCP_TOOL_INVOKE_PATH)),
                        Pattern.compile(Pattern.quote(HttpConst.INTERNAL_MCP_TOOL_INVOKE_PATH)),
                        null));
    }

    /**
     * 功能描述:
     * 〈匹配内部接口安全边界并校验规范请求〉
     * @param method HTTP 方法
     * @param path 请求路径
     * @return 未进入安全边界时为空，进入后返回规范校验结果
     * @author qingfeng
     */
    public Optional<Match> match(String method, String path) {
        return rules.stream()
                .map(rule -> rule.match(method, path))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    /**
     * 功能描述:
     * 〈内部接口安全规则〉
     * @param method 合法 HTTP 方法
     * @param boundaryPattern 安全边界路径表达式
     * @param canonicalPattern 规范路径表达式
     * @param accountIdGroup 账号 ID 分组名称
     * @author qingfeng
     */
    private record Rule(String method, Pattern boundaryPattern, Pattern canonicalPattern,
            String accountIdGroup) {

        /**
         * 功能描述:
         * 〈匹配安全边界并校验请求方法、规范路径和数字参数〉
         * @param requestMethod 请求方法
         * @param path 请求路径
         * @return 安全边界匹配结果
         * @author qingfeng
         */
        private Optional<Match> match(String requestMethod, String path) {
            if (!boundaryPattern.matcher(path).matches()) {
                return Optional.empty();
            }
            Matcher matcher = canonicalPattern.matcher(path);
            if (!method.equals(requestMethod) || !matcher.matches()) {
                return Optional.of(Match.invalid());
            }
            try {
                return Optional.of(new Match(true, pathValue(matcher, accountIdGroup)));
            }
            catch (NumberFormatException exception) {
                // 数字格式合法但超过 Long 范围时仍属于非法认证请求，不允许冒泡为 500。
                return Optional.of(Match.invalid());
            }
        }

        /**
         * 功能描述:
         * 〈安全读取路径参数中的 Long 值〉
         * @param matcher 规范路径匹配器
         * @param groupName 路径参数分组名称
         * @return 路径参数，为空表示当前规则不绑定该参数
         * @author qingfeng
         */
        private Long pathValue(Matcher matcher, String groupName) {
            return groupName == null ? null : Long.valueOf(matcher.group(groupName));
        }
    }

    /**
     * 功能描述:
     * 〈内部接口安全边界匹配结果〉
     * @param valid 请求方法与规范路径是否合法
     * @param accountId 路径账号 ID
     * @author qingfeng
     */
    public record Match(boolean valid, Long accountId) {

        /**
         * 功能描述:
         * 〈创建边界内非法请求结果〉
         * @return 非法请求结果
         * @author qingfeng
         */
        public static Match invalid() {
            return new Match(false, null);
        }
    }
}
