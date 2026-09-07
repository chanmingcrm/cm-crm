package com.platform.mesh.upms.api.modules.sys.menu.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.api.modules.sys.menu.feign.factory.RemoteSysMenuFallbackFactory;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *      菜单信息服务，用于其他模块主要是APP模块增删改，联动修改菜单
 * </p>
 *
 * @author 蝉鸣
 * @since 2024/11/1 16:29
 **/
@FeignClient(contextId = "remoteMenuService", value = ServiceNameConst.SYSTEM_SERVICE,
        fallbackFactory = RemoteSysMenuFallbackFactory.class)
public interface RemoteSysMenuService {

    /**
     * 功能描述:
     * 〈创建菜单〉
     * @param appMenuBO appMenuBO
     * @return 正常返回:{@link Result <Boolean>}
     * @author 蝉鸣
     */
    @PostMapping(value = "/api/menu/save/menu", headers = HttpConst.HEADER_FROM_IN)
    Result<Boolean> addOrEditMenu(@Validated @RequestBody AppMenuBO appMenuBO);

    /**
     * 功能描述:
     * 〈删除菜单〉
     * @param moduleIds moduleIds
     * @return 正常返回:{@link Result <Boolean>}
     * @author 蝉鸣
     */
    @PostMapping(value = "/api/menu/app/module/delete", headers = HttpConst.HEADER_FROM_IN)
    Result<Boolean> appModuleMenuDelete(@Validated @RequestBody List<Long> moduleIds);

    /**
     * 功能描述:
     * 〈清除菜单〉
     * @param moduleIds moduleIds
     * @return 正常返回:{@link Result <Boolean>}
     * @author 蝉鸣
     */
    @PostMapping(value = "/api/menu/app/module/clear", headers = HttpConst.HEADER_FROM_IN)
    Result<Boolean> appModuleMenuClear(@Validated @RequestBody List<Long> moduleIds);

    /**
     * 功能描述:
     * 〈拷贝租户菜单〉
     * @param sourceTenantId sourceTenantId
     * @param targetTenantId targetTenantId
     * @author 蝉鸣
     */
    @GetMapping(value = "/api/menu/tenant/copy", headers = HttpConst.HEADER_FROM_IN)
    void copyTenantMenu(@RequestParam("sourceTenantId") Long sourceTenantId, @RequestParam("targetTenantId") Long targetTenantId);

}
