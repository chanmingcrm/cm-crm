package com.platform.mesh.uaa.api.modules.tenant;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.uaa.api.modules.tenant.domain.TenantClientBO;
import com.platform.mesh.uaa.api.modules.tenant.factory.RemoteTenantFallbackFactory;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @description 令牌管理服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteTenantService", value = ServiceNameConst.AUTH_SERVICE,
		fallbackFactory = RemoteTenantFallbackFactory.class)
public interface RemoteTenantService {

	/**
	 * 功能描述:
	 * 〈获取租户客户端配置〉
	 * @param clientSource clientSource
	 * @return 正常返回:{@link Result<TenantClientBO>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/tenant/client/{clientSource}", headers = HttpConst.HEADER_FROM_IN)
	Result<TenantClientBO> getTenantClientInfo(@PathVariable("clientSource") Integer clientSource);

}
