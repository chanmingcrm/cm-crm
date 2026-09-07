package com.platform.mesh.ai.biz.modules.ai.mcp.server.controller;

import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.dto.AiMcpServerDTO;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.po.AiMcpServer;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.vo.AiMcpServerVO;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.service.IAiMcpServerService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;



/**
 * 约定当前controller 只引入当前service
 * @description 外部 MCP Server 管理
 * @author qingfeng
 */
@Tag(description = "AiMcpServerController", name = "外部 MCP Server")
@RestController
public class AiMcpServerController extends BaseController{
    
    @Autowired
    private IAiMcpServerService mcpServerService;

    /**
	 * 功能描述:
	 * 〈获取AIMcp列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AiMcpServerVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取外部 MCP Server 分页")
	@PostMapping("/ai/mcp/server/page")
	public Result<PageVO<AiMcpServerVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AiMcpServer> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiMcpServer.class);
        MPage<AiMcpServer> page = mcpServerService.pageCurrentTenant(mPage);
        PageVO<AiMcpServerVO> voPage = MPageUtil.convertToVO(page, AiMcpServerVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前AIMcp信息〉
     * @param serverId 外部 MCP Server ID
     * @return 正常返回:{@link Result<AiMcpServerVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取外部 MCP Server 信息")
    @GetMapping("/ai/mcp/server/info/{serverId}")
    public Result<AiMcpServerVO> getMcpServerInfoById(@PathVariable("serverId") Long serverId) {
        return Result.success(mcpServerService.getMcpServerById(serverId));
    }

    /**
     * 功能描述:
     * 〈新增AIMcp〉
     * @param serverDTO serverDTO
     * @return 正常返回:{@link Result<AiMcpServerVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增外部 MCP Server")
    @Log(moduleName = "外部 MCP Server 管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/ai/mcp/server/add")
    public Result<AiMcpServerVO> addMcpServer(@Validated @RequestBody AiMcpServerDTO serverDTO) {
        return Result.success(mcpServerService.addMcpServer(serverDTO));
    }

    /**
     * 功能描述:
     * 〈修改AIMcp〉
     * @param serverDTO serverDTO
     * @return 正常返回:{@link Result<AiMcpServerVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改外部 MCP Server")
    @Log(moduleName = "外部 MCP Server 管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/ai/mcp/server/edit")
    public Result<AiMcpServerVO> editMcpServer(@Validated @RequestBody AiMcpServerDTO serverDTO) {
        return Result.success(mcpServerService.editMcpServer(serverDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除AIMcp〉
     * @param serverId 外部 MCP Server ID
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除外部 MCP Server")
    @Log(moduleName = "外部 MCP Server 管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/ai/mcp/server/delete/{serverId}")
    public Result<Boolean> deleteMcpServer(@PathVariable("serverId") Long serverId) {
        return Result.success(mcpServerService.deleteMcpServer(serverId));
    }

    /**
     * 功能描述:
     * 〈验证外部 MCP 连接并发现工具〉
     * @param serverId 外部 MCP Server ID
     * @return 工具名称列表
     * @author qingfeng
     */
    @Operation(summary = "验证外部 MCP 连接并发现工具")
    @PostMapping("/ai/mcp/server/tools/{serverId}")
    public Result<List<String>> discoverTools(@PathVariable("serverId") Long serverId) {
        return Result.success(mcpServerService.discoverTools(serverId));
    }

}
