package com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.domain.po.AiMcpAccessKey;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 功能描述:
 * 〈CRM MCP 访问密钥数据访问接口〉
 * @author qingfeng
 */
public interface AiMcpAccessKeyMapper extends BaseMapper<AiMcpAccessKey> {

    /**
     * 功能描述:
     * 〈根据公钥标识查询待认证访问密钥〉
     * @param publicId 公钥标识
     * @return 访问密钥
     * @author qingfeng
     */
    @InterceptorIgnore(tenantLine = "true")
    AiMcpAccessKey selectForAuthentication(@Param("publicId") String publicId);

    /**
     * 功能描述:
     * 〈根据主键读取内部凭证校验所需的访问密钥〉
     * @param accessKeyId 访问密钥 ID
     * @return 访问密钥
     * @author qingfeng
     */
    @InterceptorIgnore(tenantLine = "true")
    AiMcpAccessKey selectForCredential(@Param("accessKeyId") long accessKeyId);

    /**
     * 功能描述:
     * 〈更新已认证访问密钥的最近使用时间〉
     * @param accessKeyId 访问密钥 ID
     * @param lastUsedAt 最近使用时间
     * @return 更新数量
     * @author qingfeng
     */
    @InterceptorIgnore(tenantLine = "true")
    int updateLastUsed(@Param("accessKeyId") long accessKeyId,
            @Param("lastUsedAt") LocalDateTime lastUsedAt);
}
