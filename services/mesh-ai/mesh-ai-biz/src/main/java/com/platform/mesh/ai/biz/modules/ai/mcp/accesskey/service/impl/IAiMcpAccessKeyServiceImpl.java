package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.exception.AiMcpAccessKeyExceptionEnum;
import com.platform.mesh.ai.api.modules.mcp.constants.McpConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeyPrincipal;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpAccessInfoRO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.po.AiMcpAccessKey;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeyCreatedVO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.ro.McpKeySummaryVO;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.mapper.AiMcpAccessKeyMapper;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.service.IAiMcpAccessKeyService;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.constant.McpServerConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.service.IAiMcpServerService;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.AiMcpToolService;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 功能描述:
 * 〈MCP 访问密钥服务实现〉
 * @author qingfeng
 */
@Service
public class IAiMcpAccessKeyServiceImpl implements IAiMcpAccessKeyService {

    private final AiMcpAccessKeyMapper mapper;
    private final IAiMcpServerService serverService;
    private final AiMcpToolService toolService;
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * 功能描述:
     * 〈创建 MCP 访问密钥服务〉
     * @param mapper MCP 访问密钥数据访问接口
     * @param policyService MCP 多租户策略服务
     * @param serverService MCP 服务配置服务
     * @param toolService MCP 工具配置服务
     * @author qingfeng
     */
    public IAiMcpAccessKeyServiceImpl(AiMcpAccessKeyMapper mapper,
                                      IAiMcpServerService serverService, AiMcpToolService toolService) {
        this.mapper = mapper;
        this.serverService = serverService;
        this.toolService = toolService;
    }

    /**
     * 功能描述:
     * 〈创建 MCP 访问密钥〉
     * @param name 密钥名称
     * @param expiresAt 过期时间
     * @return MCP 访问密钥创建结果
     * @author qingfeng
     */
    @Override
    public McpKeyCreatedVO create(String name, LocalDateTime expiresAt) {
        // 生成公钥标识和只返回一次的完整访问密钥
        String publicId = randomBase64Url(12);
        String secret = McpConst.ACCESS_KEY_PREFIX + publicId + "." + randomBase64Url(32);
        LocalDateTime now = LocalDateTime.now();
        // 将密钥哈希与当前账号、用户和租户绑定
        AiMcpAccessKey row = new AiMcpAccessKey()
                .setKeyName(name)
                .setPublicId(publicId)
                .setSecretHash(HexFormat.of().formatHex(sha256(secret)))
                .setAccountId(UserCacheUtil.getAccountId())
                .setUserId(UserCacheUtil.getUserId())
                .setStatus(McpConst.ACCESS_KEY_STATUS_ACTIVE)
                .setExpiresAt(expiresAt)
                .setCreateTime(now)
                .setUpdateTime(now);
        // 持久化失败时禁止返回未落库的访问密钥
        if (mapper.insert(row) != 1 || row.getId() == null) {
            throw AiMcpAccessKeyExceptionEnum.CREATE_FAILED.getBaseException();
        }
        // 完整访问密钥仅在创建成功时返回一次
        return new McpKeyCreatedVO(row.getId(), row.getKeyName(), secret, row.getExpiresAt(), row.getCreateTime());
    }

    /**
     * 功能描述:
     * 〈查询当前账号的 MCP 访问密钥〉
     * @return MCP 访问密钥摘要列表
     * @author qingfeng
     */
    @Override
    public List<McpKeySummaryVO> listMine() {
        // 获取当前可信账号和租户身份
        long accountId = UserCacheUtil.getAccountId();
        // 单机版按账号限定访问密钥
        LambdaQueryWrapper<AiMcpAccessKey> query = new LambdaQueryWrapper<AiMcpAccessKey>()
                .eq(AiMcpAccessKey::getAccountId, accountId)
                .orderByDesc(AiMcpAccessKey::getCreateTime);
        // 转换为不包含密钥哈希的安全摘要
        return mapper.selectList(query).stream().map(this::toSummary).toList();
    }

    /**
     * 功能描述:
     * 〈查询当前租户的 MCP 访问密钥〉
     * @return MCP 访问密钥摘要列表
     * @author qingfeng
     */
    @Override
    public List<McpKeySummaryVO> listTenantForAdmin() {
        // 单机版管理员可查看全部访问密钥
        LambdaQueryWrapper<AiMcpAccessKey> query = new LambdaQueryWrapper<AiMcpAccessKey>()
                .orderByDesc(AiMcpAccessKey::getCreateTime);
        // 转换为不包含密钥哈希的安全摘要
        return mapper.selectList(query).stream().map(this::toSummary).toList();
    }

    /**
     * 功能描述:
     * 〈撤销当前账号的 MCP 访问密钥〉
     * @param id 访问密钥 ID
     * @author qingfeng
     */
    @Override
    public void revokeMine(long id) {
        // 使用当前账号和租户作为撤销边界
        long accountId = UserCacheUtil.getAccountId();
        revoke(id, accountId);
    }

    /**
     * 功能描述:
     * 〈撤销当前租户的 MCP 访问密钥〉
     * @param id 访问密钥 ID
     * @author qingfeng
     */
    @Override
    public void revokeForAdmin(long id) {
        revoke(id, null);
    }

    /**
     * 功能描述:
     * 〈校验 MCP Access Key〉
     * @param accessKeySecret Access Key 明文
     * @return 访问密钥绑定身份
     * @author qingfeng
     */
    @Override
    public McpKeyPrincipal verify(String accessKeySecret) {
        // 校验访问密钥格式并提取公钥标识
        Matcher matcher = accessKeySecret == null ? null : McpConst.ACCESS_KEY_PATTERN.matcher(accessKeySecret);
        if (matcher == null || !matcher.matches()) {
            throw AiMcpAccessKeyExceptionEnum.UNAUTHORIZED.getBaseException();
        }
        // 根据公钥标识读取认证数据
        AiMcpAccessKey row = mapper.selectForAuthentication(matcher.group(1));
        LocalDateTime now = LocalDateTime.now();
        // 统一校验启用状态、撤销状态、有效期和密钥哈希
        if (row == null || !Integer.valueOf(McpConst.ACCESS_KEY_STATUS_ACTIVE).equals(row.getStatus())
                || row.getRevokedAt() != null
                || row.getExpiresAt() != null && !row.getExpiresAt().isAfter(now)
                || !hashMatches(row.getSecretHash(), accessKeySecret)) {
            throw AiMcpAccessKeyExceptionEnum.UNAUTHORIZED.getBaseException();
        }
        // 返回数据库绑定的可信账号、用户和租户身份
        return new McpKeyPrincipal(row.getId(), row.getAccountId(), row.getUserId());
    }

    /**
     * 功能描述:
     * 〈更新访问密钥最近使用时间〉
     * @param accessKeyId 访问密钥 ID
     * @author qingfeng
     */
    @Override
    public void markLastUsed(long accessKeyId) {
        // 仅更新已认证且仍有效的访问密钥
        if (mapper.updateLastUsed(accessKeyId, LocalDateTime.now()) != 1) {
            throw AiMcpAccessKeyExceptionEnum.UPDATE_LAST_USED_FAILED.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈获取当前账号的 MCP 接入信息〉
     * @return MCP 接入信息
     * @author qingfeng
     */
    @Override
    public McpAccessInfoRO getAccessInfo() {
        // 有效 AccessKey 可以使用全部已发布工具，数据范围由密钥绑定身份控制。
        var definitions = toolService.listEnabledToolDefinitions();
        return new McpAccessInfoRO(serverService.getPlatformServerUrl(),
                McpServerConst.TYPE_STREAMABLE_HTTP, true, true,
                McpConst.DEFAULT_RATE_LIMIT, definitions);
    }

    /**
     * 功能描述:
     * 〈按租户和可选账号条件撤销访问密钥〉
     * @param id 访问密钥 ID
     * @param tenantId 租户 ID
     * @param accountId 账号 ID，租户管理员撤销时为空
     * @author qingfeng
     */
    private void revoke(long id, Long accountId) {
        LocalDateTime now = LocalDateTime.now();
        // 构造包含租户、账号和有效状态的更新条件
        LambdaUpdateWrapper<AiMcpAccessKey> update = new LambdaUpdateWrapper<AiMcpAccessKey>()
                .eq(AiMcpAccessKey::getId, id)
                .eq(accountId != null, AiMcpAccessKey::getAccountId, accountId)
                .eq(AiMcpAccessKey::getStatus, McpConst.ACCESS_KEY_STATUS_ACTIVE)
                .set(AiMcpAccessKey::getStatus, McpConst.ACCESS_KEY_STATUS_REVOKED)
                .set(AiMcpAccessKey::getRevokedAt, now)
                .set(AiMcpAccessKey::getUpdateTime, now);
        // 使用条件更新保证撤销操作不越过租户或账号边界
        mapper.update(null, update);
    }

    /**
     * 功能描述:
     * 〈将访问密钥持久化对象转换为安全摘要〉
     * @param row 访问密钥持久化对象
     * @return 访问密钥摘要
     * @author qingfeng
     */
    private McpKeySummaryVO toSummary(AiMcpAccessKey row) {
        // 从缓存补充密钥所属用户名称
        SysUserBO owner = UserCacheUtil.getSysUserInfoCache(row.getUserId());
        String ownerName = owner == null ? null : owner.getNickName();
        return new McpKeySummaryVO(row.getId(), row.getKeyName(), row.getPublicId(), row.getAccountId(),
                row.getUserId(), ownerName, row.getStatus(), row.getExpiresAt(), row.getLastUsedAt(),
                row.getRevokedAt(), row.getCreateTime());
    }

    /**
     * 功能描述:
     * 〈校验当前用户是否为租户管理员〉
     * @author qingfeng
     */
    /**
     * 功能描述:
     * 〈生成无填充的 Base64URL 随机字符串〉
     * @param byteCount 随机字节数量
     * @return Base64URL 随机字符串
     * @author qingfeng
     */
    private String randomBase64Url(int byteCount) {
        // 使用安全随机数生成指定长度的随机字节
        byte[] bytes = new byte[byteCount];
        secureRandom.nextBytes(bytes);
        // 移除 Base64URL 填充字符，保持访问密钥格式固定
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * 功能描述:
     * 〈以固定时间方式比较访问密钥哈希〉
     * @param storedHex 数据库存储的十六进制哈希
     * @param secret 待校验访问密钥
     * @return 哈希是否匹配
     * @author qingfeng
     */
    private boolean hashMatches(String storedHex, String secret) {
        try {
            // 使用固定时间比较降低时序攻击风险
            byte[] stored = HexFormat.of().parseHex(storedHex);
            return MessageDigest.isEqual(stored, sha256(secret));
        } catch (IllegalArgumentException | NullPointerException ignored) {
            return false;
        }
    }

    /**
     * 功能描述:
     * 〈计算字符串的 SHA-256 摘要〉
     * @param value 待计算字符串
     * @return SHA-256 摘要
     * @author qingfeng
     */
    private byte[] sha256(String value) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw AiMcpAccessKeyExceptionEnum.HASH_ALGORITHM_UNAVAILABLE.getBaseException();
        }
    }

}
