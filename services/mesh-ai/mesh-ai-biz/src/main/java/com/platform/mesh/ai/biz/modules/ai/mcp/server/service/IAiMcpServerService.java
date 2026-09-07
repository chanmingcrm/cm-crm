package com.platform.mesh.ai.biz.modules.ai.mcp.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.dto.AiMcpServerDTO;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.po.AiMcpServer;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.vo.AiMcpServerVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AIMcp信息
 * @author 蝉鸣
 */
public interface IAiMcpServerService extends IService<AiMcpServer> {

    /**
     * 功能描述:
     * 〈分页查询当前租户外部 MCP 连接〉
     * @param page 分页对象
     * @return 外部 MCP 连接分页
     * @author qingfeng
     */
    MPage<AiMcpServer> pageCurrentTenant(MPage<AiMcpServer> page);

    /**
     * 功能描述:
     * 〈获取当前AIMcp信息〉
     * @param serverId MCP Server ID
     * @return 正常返回:{@link AiMcpServerVO}
     * @author 蝉鸣
     */
    AiMcpServerVO getMcpServerById(Long serverId);

    /**
     * 功能描述:
     * 〈新增AIMcp〉
     * @param serverDTO MCP Server 参数
     * @return 正常返回:{@link AiMcpServerVO}
     * @author 蝉鸣
     */
    AiMcpServerVO addMcpServer(AiMcpServerDTO serverDTO);

    /**
     * 功能描述:
     * 〈修改AIMcp〉
     * @param serverDTO MCP Server 参数
     * @return 正常返回:{@link AiMcpServerVO}
     * @author 蝉鸣
     */
    AiMcpServerVO editMcpServer(AiMcpServerDTO serverDTO);

    /**
     * 功能描述:
     * 〈删除AIMcp〉
     * @param serverId MCP Server ID
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteMcpServer(Long serverId);

    /**
     * 功能描述:
     * 〈发现指定外部 MCP 连接提供的工具〉
     * @param serverId 外部 MCP Server ID
     * @return 工具名称列表
     * @author qingfeng
     */
    List<String> discoverTools(Long serverId);

    /**
     * 功能描述:
     * 〈获取 MCP 对外服务地址〉
     * @return MCP 对外服务地址
     * @author qingfeng
     */
    String getPlatformServerUrl();
}
