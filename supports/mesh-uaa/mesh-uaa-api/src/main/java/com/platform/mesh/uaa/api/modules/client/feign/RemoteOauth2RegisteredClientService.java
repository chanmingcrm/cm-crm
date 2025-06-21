package com.platform.mesh.uaa.api.modules.client.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.uaa.api.modules.client.domain.bo.Oauth2RegisteredClientBO;
import com.platform.mesh.uaa.api.modules.client.feign.factory.RemoteOauth2RegisteredClientFallbackFactory;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @description Oauth2服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "RemoteOauth2ClientDetailsService", value = ServiceNameConst.AUTH_SERVICE,
		fallbackFactory = RemoteOauth2RegisteredClientFallbackFactory.class)
public interface RemoteOauth2RegisteredClientService {

	/**
	 * 功能描述:
	 * 〈获取终端配置详细信息〉
	 * @param clientId clientId
	 * @return 正常返回:{@link Result<Oauth2RegisteredClientBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/client/{clientId}", headers = HttpConst.HEADER_FROM_IN)
	Result<Oauth2RegisteredClientBO> getClientDetailsById(@PathVariable("clientId") String clientId);

}
