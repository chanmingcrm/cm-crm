package com.platform.mesh.ai.biz.modules.ai.mcp.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.ai.api.modules.mcp.domain.ro.McpToolDefinitionRO;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.dto.AiMcpServerDTO;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.po.AiMcpServer;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.vo.AiMcpServerVO;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.exception.AiMcpServerExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.mapper.AiMcpServerMapper;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.constant.McpServerConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.service.manual.McpClientRegistry;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.service.manual.McpServerUrlValidator;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.service.IAiMcpServerService;
import com.platform.mesh.ai.biz.modules.ai.mcp.tool.service.AiMcpToolService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AIMcp
 * @author 蝉鸣
 */
@Service
public class AiMcpServerServiceImpl extends ServiceImpl<AiMcpServerMapper, AiMcpServer> implements IAiMcpServerService {

    private final McpClientRegistry clientRegistry;
    private final McpServerUrlValidator urlValidator;
    private final AiMcpToolService toolService;

    /**
     * 功能描述:
     * 〈创建外部 MCP 连接服务〉
     * @param clientRegistry 外部 MCP 客户端注册表
     * @param urlValidator 服务地址校验器
     * @param toolService 本地 MCP 工具目录服务
     * @author qingfeng
     */
    public AiMcpServerServiceImpl(McpClientRegistry clientRegistry, McpServerUrlValidator urlValidator,
            AiMcpToolService toolService) {
        this.clientRegistry = clientRegistry;
        this.urlValidator = urlValidator;
        this.toolService = toolService;
    }

    /**
     * 功能描述:
     * 〈分页查询当前租户外部 MCP 连接〉
     * @param page 分页对象
     * @return 外部 MCP 连接分页
     * @author qingfeng
     */
    @Override
    public MPage<AiMcpServer> pageCurrentTenant(MPage<AiMcpServer> page) {
        return this.page(page);
    }

    
    /**
     * 功能描述: 
     * 〈获取当前AIMcp信息〉
     * @param mcpId mcpId
     * @return 正常返回:{@link AiMcpServerVO}
     * @author 蝉鸣
     */
    @Override
    public AiMcpServerVO getMcpServerById(Long mcpId) {
        AiMcpServer aiMcp = this.lambdaQuery()
                .eq(AiMcpServer::getId, mcpId)
                .eq(AiMcpServer::getDelFlag, McpServerConst.DATA_ACTIVE)
                .one();
        return BeanUtil.copyProperties(aiMcp, AiMcpServerVO.class);
    }

    /**
     * 功能描述:
     * 〈新增AIMcp〉
     * @param mcpDTO mcpDTO
     * @return 正常返回:{@link AiMcpServerVO}
     * @author 蝉鸣
     */
    @Override
    public AiMcpServerVO addMcpServer(AiMcpServerDTO mcpDTO) {
        validate(mcpDTO);
        AiMcpServer aiMcp = BeanUtil.copyProperties(mcpDTO, AiMcpServer.class);
        aiMcp.setServerType(McpServerConst.SERVER_TYPE_EXTERNAL);
        aiMcp.setDelFlag(McpServerConst.DATA_ACTIVE);
        if (aiMcp.getStatusFlag() == null) {
            aiMcp.setStatusFlag(McpServerConst.STATUS_ENABLED);
        }
        this.save(aiMcp);
        return BeanUtil.copyProperties(aiMcp, AiMcpServerVO.class);
    }

    /**
     * 功能描述:
     * 〈修改AIMcp〉
     * @param mcpDTO mcpDTO
     * @return 正常返回:{@link AiMcpServerVO}
     * @author 蝉鸣
     */
    @Override
    public AiMcpServerVO editMcpServer(AiMcpServerDTO mcpDTO) {
        if(ObjectUtil.isEmpty(mcpDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AiMcpServerDTO::getId);
            throw AiMcpServerExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AiMcpServer current = this.lambdaQuery().eq(AiMcpServer::getId, mcpDTO.getId())
                .eq(AiMcpServer::getDelFlag, McpServerConst.DATA_ACTIVE)
                .one();
        if (current == null) {
            throw new IllegalArgumentException("外部 MCP 连接不存在");
        }
        if (McpServerConst.SERVER_TYPE_EXTERNAL.equals(current.getServerType())) {
            validate(mcpDTO);
        }
        AiMcpServer aiMcp = BeanUtil.copyProperties(mcpDTO, AiMcpServer.class);
        aiMcp.setServerType(current.getServerType());
        if (StrUtil.isBlank(mcpDTO.getRequestHeaders())) {
            aiMcp.setRequestHeaders(current.getRequestHeaders());
        }
        this.update(aiMcp, Wrappers.<AiMcpServer>lambdaUpdate()
                .eq(AiMcpServer::getId, aiMcp.getId()));
        clientRegistry.invalidate(aiMcp.getId());
        return BeanUtil.copyProperties(aiMcp, AiMcpServerVO.class);
    }

    /**
     * 功能描述:
     * 〈删除AIMcp〉
     * @param mcpId mcpId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteMcpServer(Long mcpId) {
        boolean removed = this.lambdaUpdate().eq(AiMcpServer::getId, mcpId)
                .eq(AiMcpServer::getServerType, McpServerConst.SERVER_TYPE_EXTERNAL)
                .set(AiMcpServer::getDelFlag, 0)
                .update();
        clientRegistry.invalidate(mcpId);
        return removed;
    }

    /**
     * 功能描述:
     * 〈发现指定外部 MCP 连接提供的工具〉
     * @param mcpId 外部 MCP 连接 ID
     * @return 工具名称列表
     * @author qingfeng
     */
    @Override
    public List<String> discoverTools(Long mcpId) {
        // 系统 MCP 直接读取本地已启用工具，不通过网络访问自身服务。
        AiMcpServer platformServer = this.baseMapper.selectPlatformServer();
        if (platformServer != null && platformServer.getId().equals(mcpId)) {
            return toolService.listEnabledToolDefinitions().stream()
                    .map(McpToolDefinitionRO::name)
                    .toList();
        }
        return clientRegistry.client(mcpId).client()
                .listTools().tools().stream().map(McpSchema.Tool::name).toList();
    }

    /**
     * 功能描述:
     * 〈获取 MCP 对外服务地址〉
     * @return MCP 对外服务地址
     * @author qingfeng
     */
    @Override
    public String getPlatformServerUrl() {
        String serverUrl = getPlatformServer().getServerUrl();
        if (StrUtil.isBlank(serverUrl)) {
            throw new IllegalStateException("MCP 对外服务地址不能为空");
        }
        return serverUrl.trim();
    }

    /**
     * 功能描述:
     * 〈获取当前启用的平台 MCP 服务配置〉
     * @return 平台 MCP 服务配置
     * @author qingfeng
     */
    private AiMcpServer getPlatformServer() {
        AiMcpServer server = this.baseMapper.selectPlatformServer();
        if (server == null) {
            throw new IllegalStateException("未配置启用的平台 MCP 服务");
        }
        return server;
    }

    /**
     * 功能描述:
     * 〈校验外部 MCP 连接参数〉
     * @param dto 外部 MCP 连接参数
     * @author qingfeng
     */
    private void validate(AiMcpServerDTO dto) {
        if (!McpServerConst.TYPE_STREAMABLE_HTTP.equalsIgnoreCase(dto.getMcpType())) {
            throw new IllegalArgumentException("外部 MCP 仅支持 Streamable HTTP");
        }
        urlValidator.validate(dto.getServerUrl());
        if (StrUtil.isNotBlank(dto.getRequestHeaders())
                && !JSONUtil.isTypeJSONObject(dto.getRequestHeaders())) {
            throw new IllegalArgumentException("MCP 请求头必须是 JSON 对象");
        }
    }
}
