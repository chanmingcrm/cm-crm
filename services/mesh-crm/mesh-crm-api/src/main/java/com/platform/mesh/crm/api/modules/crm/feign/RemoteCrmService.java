package com.platform.mesh.crm.api.modules.crm.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.crm.api.modules.crm.feign.factory.RemoteCrmFallbackFactory;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @description 应用信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteCrmService", value = ServiceNameConst.CRM_SERVICE,
		fallbackFactory = RemoteCrmFallbackFactory.class)
public interface RemoteCrmService {

	/**
	 * 功能描述:
	 * 〈获取模块信息〉
	 * @param object object
	 * @return 正常返回:{@link Result<Void>}
	 * @author 蝉鸣
	 */
	@PostMapping(value="/api/crm/pre/drainage/add/simp", headers = HttpConst.HEADER_FROM_IN)
	Result<Void> addCrmDrainage(@RequestBody String object);

}
