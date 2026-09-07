package com.platform.mesh.uaa.api.modules.tenant.factory;

import com.platform.mesh.uaa.api.modules.tenant.RemoteTenantService;
import com.platform.mesh.uaa.api.modules.tenant.domain.TenantClientBO;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description 租户管理服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteTenantFallbackFactory implements FallbackFactory<RemoteTenantService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteTenantFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteTenantService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteTenantService create(Throwable throwable) {
		log.error("租户管理服务调用失败:{}", throwable.getMessage());
		return new RemoteTenantService() {

            @Override
            public Result<TenantClientBO> getTenantClientInfo(Integer clientSource) {
                return Result.error();
            }
        };
	}

}
