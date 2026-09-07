package com.platform.mesh.ai.biz.modules.ai.mcp.security.accesskey;

import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeyPrincipal;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.exception.AiMcpAccessKeyExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.service.IAiMcpAccessKeyService;
import com.platform.mesh.ai.api.modules.mcp.constants.McpConst;
import com.platform.mesh.security.accesskey.model.AccessKeyCredential;
import com.platform.mesh.security.accesskey.model.AuthenticatedAccessKey;
import com.platform.mesh.security.accesskey.spi.AccessKeyVerifier;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import org.redisson.api.RBucket;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import java.util.Set;

/**
 * 功能描述:
 * 〈AI 模块对 ai_mcp_access_key 的业务校验适配器〉
 *
 * @author qingfeng
 */
public class AiMcpAccessKeyVerifier implements AccessKeyVerifier {

    private final IAiMcpAccessKeyService keyService;
    private final RemoteUserService remoteUserService;
    private final RedissonClient redisson;

    /**
     * 功能描述:
     * 〈创建 AI MCP Access Key 校验器〉
     * @param keyService MCP Access Key 业务服务
     * @param remoteUserService 用户账号租户查询服务
     * @param redisson Redis 客户端
     * @author qingfeng
     */
    public AiMcpAccessKeyVerifier(IAiMcpAccessKeyService keyService,
            RemoteUserService remoteUserService, RedissonClient redisson) {
        this.keyService = keyService;
        this.remoteUserService = remoteUserService;
        this.redisson = redisson;
    }

    /**
     * 功能描述:
     * 〈获取 AI MCP Access Key 凭证类型〉
     * @return AI MCP Access Key 凭证类型
     * @author qingfeng
     */
    @Override
    public String credentialType() {
        return McpConst.ACCESS_KEY_CREDENTIAL_TYPE;
    }

    /**
     * 功能描述:
     * 〈校验 MCP 密钥、账号租户绑定及访问频率，并生成统一认证身份〉
     * @param credential 待校验的访问密钥
     * @return 已认证的访问密钥身份
     * @author qingfeng
     */
    @Override
    public AuthenticatedAccessKey verify(AccessKeyCredential credential) {
        // 校验密钥本身及其有效期，读取密钥绑定的用户、账号和租户。
        McpKeyPrincipal key = keyService.verify(credential.secret());
        // 防止业务数据缺失，并确认当前账号租户绑定仍然有效。
        requireComplete(key);
        // 执行租户账号维度限流，并以防抖方式记录最近使用时间。
        requireRateLimit(key);
        updateLastUsed(key.accessKeyId());
        return new AuthenticatedAccessKey(McpConst.ACCESS_KEY_CREDENTIAL_TYPE,
                key.accessKeyId().toString(),
                key.userId(), key.accountId(), Set.of(), null);
    }

    /**
     * 功能描述:
     * 〈限制 AI MCP Access Key 只能访问 MCP 服务端点〉
     * @param accessKey 已认证的访问密钥身份
     * @param method HTTP 请求方法
     * @param path 应用内规范化请求路径
     * @return true 允许访问，false 拒绝访问
     * @author qingfeng
     */
    @Override
    public boolean isRequestAllowed(AuthenticatedAccessKey accessKey, String method, String path) {
        return McpConst.SERVER_PATH.equals(path);
    }

    /**
     * 功能描述:
     * 〈校验密钥身份绑定字段是否完整〉
     * @param key 密钥身份绑定
     * @author qingfeng
     */
    private void requireComplete(McpKeyPrincipal key) {
        if (key == null || key.accessKeyId() == null || key.accountId() == null
                || key.userId() == null) {
            throw AiMcpAccessKeyExceptionEnum.IDENTITY_INVALID.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈校验账号、用户和租户绑定是否一致且处于生效状态〉
     * @param key 密钥身份绑定
     * @author qingfeng
     */
    /**
     * 功能描述:
     * 〈执行租户账号维度的 MCP Access Key 请求限流〉
     * @param key 密钥身份绑定
     * @author qingfeng
     */
    private void requireRateLimit(McpKeyPrincipal key) {
        String limiterKey = McpConst.REDIS_RATE_LIMIT_PREFIX + key.accountId();
        RRateLimiter limiter = redisson.getRateLimiter(limiterKey);
        limiter.trySetRate(RateType.OVERALL, McpConst.DEFAULT_RATE_LIMIT, McpConst.RATE_INTERVAL);
        if (!limiter.tryAcquire()) {
            throw AiMcpAccessKeyExceptionEnum.RATE_LIMIT_EXCEEDED.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈防抖更新 Access Key 最近使用时间，避免每次请求写数据库〉
     * @param accessKeyId Access Key ID
     * @author qingfeng
     */
    private void updateLastUsed(long accessKeyId) {
        RBucket<Boolean> debounce = redisson.getBucket(McpConst.REDIS_LAST_USED_PREFIX + accessKeyId);
        if (debounce.setIfAbsent(Boolean.TRUE, McpConst.LAST_USED_DEBOUNCE)) {
            keyService.markLastUsed(accessKeyId);
        }
    }
}
