package com.platform.mesh.upms.api.modules.conf.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.conf.domian.bo.ConfSysSetBO;
import com.platform.mesh.upms.api.modules.conf.feign.factory.RemoteConfFallbackFactory;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description 配置信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteConfService", value = ServiceNameConst.SYSTEM_SERVICE,
		fallbackFactory = RemoteConfFallbackFactory.class)
public interface RemoteConfService {

	/**
	 * 功能描述:
	 * 〈获取字段〉
	 * @param confSource confSource
	 * @return 正常返回:{@link List<ConfSysSetBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取字段")
	@GetMapping(value = "/api/conf/sys/set/list", headers = HttpConst.HEADER_FROM_IN)
	Result<List<ConfSysSetBO>> selectList(@RequestParam("confSource") Integer confSource);

}
