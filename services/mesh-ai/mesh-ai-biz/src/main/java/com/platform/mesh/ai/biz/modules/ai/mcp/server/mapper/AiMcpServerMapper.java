package com.platform.mesh.ai.biz.modules.ai.mcp.server.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.po.AiMcpServer;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description AIMcp
 * @author 蝉鸣
 */
public interface AiMcpServerMapper extends BaseMapper<AiMcpServer> {

    /**
     * 功能描述:
     * 〈分页查询系统共享和当前租户自有的 MCP 服务〉
     * @param page 分页对象
     * @param tenantId 当前租户 ID
     * @return MCP 服务分页
     * @author qingfeng
     */
    @InterceptorIgnore(tenantLine = "true")
    MPage<AiMcpServer> selectCurrentTenantPage(MPage<AiMcpServer> page,
            @Param("tenantId") Long tenantId);

    /**
     * 功能描述:
     * 〈查询当前启用的平台 MCP 服务配置〉
     * @return 平台 MCP 服务配置
     * @author qingfeng
     */
    @InterceptorIgnore(tenantLine = "true")
    AiMcpServer selectPlatformServer();

    /**
     * 查询系统共享和当前租户自有的启用 MCP 服务。
     *
     * @param tenantId 当前租户 ID
     * @return 可用于聊天的 MCP 服务
     */
    @InterceptorIgnore(tenantLine = "true")
    List<AiMcpServer> selectEnabledExternalServers();
}
