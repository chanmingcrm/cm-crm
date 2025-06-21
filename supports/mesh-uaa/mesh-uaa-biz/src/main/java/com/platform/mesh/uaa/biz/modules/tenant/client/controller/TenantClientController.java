package com.platform.mesh.uaa.biz.modules.tenant.client.controller;

import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientAddDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientEditDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto.TenantClientQueryDTO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.po.TenantClient;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.vo.TenantClientVO;
import com.platform.mesh.uaa.biz.modules.tenant.client.service.ITenantClientService;
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



/**
 * 约定当前controller 只引入当前service
 * @description 授权客户端系统关系信息
 * @author 蝉鸣
 */
@Tag(description = "TenantClientController", name = "授权客户端系统关系")
@RestController
@RequestMapping
public class TenantClientController extends BaseController{
    @Autowired
    private ITenantClientService  tenantClientService;

    /**
	 * 功能描述:
	 * 〈获取授权客户端系统关系列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TenantClientVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取授权客户端系统关系分页")
	@PostMapping("/tenant/client/page")
	public Result<PageVO<TenantClientVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TenantClient> clientMPage = MPageUtil.pageEntityToMPage(pageDTO, TenantClient.class);
        MPage<TenantClient> page = tenantClientService.page(clientMPage);
        PageVO<TenantClientVO> voPage = MPageUtil.convertToVO(page, TenantClientVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前授权客户端系统关系信息〉
     * @param clientId clientId
     * @return 正常返回:{@link Result<TenantClientVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前授权客户端系统关系信息")
    @GetMapping("/tenant/client/info/{clientId}")
    public Result<TenantClientVO> getClientInfoByClientId(@PathVariable("clientId")Long clientId) {
        TenantClientVO tenantClientVO = tenantClientService.getClientInfoByClientId(clientId);
        return Result.success(tenantClientVO);
    }

    /**
     * 功能描述:
     * 〈获取当前授权客户端系统关系信息〉
     * @param queryDTO queryDTO
     * @return 正常返回:{@link Result<TenantClientVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前授权客户端系统关系信息")
    @PostMapping("/tenant/client/info")
    public Result<TenantClientVO> getClientInfoByTenant(@RequestBody TenantClientQueryDTO queryDTO) {
        TenantClientVO tenantClientVO = tenantClientService.getClientInfoByTenant(queryDTO);
        return Result.success(tenantClientVO);
    }

    /**
     * 功能描述:
     * 〈新增授权客户端系统关系〉
     * @param clientDTO clientDTO
     * @return 正常返回:{@link Result<TenantClientVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增授权客户端系统关系")
    @Log(moduleName = "授权客户端系统关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/tenant/client/add")
    public Result<TenantClientVO> addClient(@Validated @RequestBody TenantClientAddDTO clientDTO) {
        return Result.success(tenantClientService.addClient(clientDTO));
    }

    /**
     * 功能描述:
     * 〈修改授权客户端系统关系〉
     * @param clientDTO clientDTO
     * @return 正常返回:{@link Result<TenantClientVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改授权客户端系统关系")
    @Log(moduleName = "授权客户端系统关系管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/tenant/client/edit")
    public Result<TenantClientVO> editClient(@Validated @RequestBody TenantClientEditDTO clientDTO) {
        return Result.success(tenantClientService.editClient(clientDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除授权客户端系统关系〉
     * @param clientId clientId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除授权客户端系统关系")
    @Log(moduleName = "授权客户端系统关系管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/tenant/client/delete/{clientId}")
    public Result<Boolean> deleteClient(@PathVariable(value = "clientId",required = false)Long clientId) {
        return Result.success(tenantClientService.deleteClient(clientId));
    }

}