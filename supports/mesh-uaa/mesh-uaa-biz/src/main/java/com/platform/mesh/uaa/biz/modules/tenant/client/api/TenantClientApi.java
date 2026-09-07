package com.platform.mesh.uaa.biz.modules.tenant.client.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.uaa.api.modules.tenant.domain.TenantClientBO;
import com.platform.mesh.uaa.biz.modules.tenant.client.domain.po.TenantClient;
import com.platform.mesh.uaa.biz.modules.tenant.client.service.ITenantClientService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 授权客户端租户关系信息
 * @author 蝉鸣
 */
@Tag(description = "TenantClientController", name = "授权客户端租户关系")
@RestController
@RequestMapping
public class TenantClientApi extends BaseController{
    @Autowired
    private ITenantClientService  tenantClientService;

    /**
     * 功能描述:
     * 〈根据来源获取租户客户端配置〉
     * @param clientSource clientSource
     * @return 正常返回:{@link Result<TenantClientBO>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据来源获取租户客户端配置")
    @PostMapping("/api/tenant/client/{clientSource}")
    public Result<TenantClientBO> getClientInfoByClientSource(@PathVariable("clientSource")Integer clientSource) {
        TenantClient tenantClient = tenantClientService.getClientInfoByClientSource(clientSource);
        return Result.success(BeanUtil.copyProperties(tenantClient, TenantClientBO.class));
    }

}