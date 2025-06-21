package com.platform.mesh.uaa.api.modules.token.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.uaa.api.modules.token.feign.factory.RemoteTokenFallbackFactory;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description 令牌管理服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConst.AUTH_SERVICE,
		fallbackFactory = RemoteTokenFallbackFactory.class)
public interface RemoteTokenService {

//	/**
//	 * 分页查询token 信息
//	 * @param tokenBO TokenDTO
//	 * @return R<TableDataInfo>
//	 */
//	@GetMapping(value = "/api/token/pageQuery", headers = SecurityConstants.HEADER_FROM_IN)
//	R<TableDataInfo> getTokenPage(@SpringQueryMap TokenBO tokenBO);

	/**
	 * 功能描述:
	 * 〈删除token〉
	 * @param token token
	 * @return 正常返回:{@link Result<Void>}
	 * @author 蝉鸣
	 */
	@DeleteMapping(value = "/api/token/{token}", headers = HttpConst.HEADER_FROM_IN)
	Result<Void> removeToken(@PathVariable("token") String token);

}
