package com.platform.mesh.upms.api.modules.sys.log.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogOperateBO;
import com.platform.mesh.upms.api.modules.sys.log.feign.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description 日志服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConst.SYSTEM_SERVICE,
		fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {


	/**
	 * 功能描述:
	 * 〈保存登录记录〉
	 * @param logLoginBO sysLoginInfo
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/log/login/add", headers = HttpConst.HEADER_FROM_IN)
	void saveLoginLog(@RequestBody LogLoginBO logLoginBO);

	/**
	 * 功能描述:
	 * 〈保存系统日志〉
	 * @param logOperateBO logOperateBO
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/log/operate/add", headers = HttpConst.HEADER_FROM_IN)
	void saveOperationLog(@RequestBody LogOperateBO logOperateBO);

	/**
	 * 功能描述:
	 * 〈保存修改记录〉
	 * @param modifyBO modifyBO
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/log/modify/add", headers = HttpConst.HEADER_FROM_IN)
	void saveModifyLog(@RequestBody LogModifyBO modifyBO);



}
