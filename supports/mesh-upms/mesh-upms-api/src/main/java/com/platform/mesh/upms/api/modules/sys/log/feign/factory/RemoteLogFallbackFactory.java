package com.platform.mesh.upms.api.modules.sys.log.feign.factory;

import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogOperateBO;
import com.platform.mesh.upms.api.modules.sys.log.feign.RemoteLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description 日志服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteLogService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteLogService create(Throwable throwable) {
		log.error("日志服务调用失败:{}", throwable.getMessage());
		return new RemoteLogService() {

			@Override
			public void saveLoginLog(LogLoginBO logLoginBO) {
			}

			@Override
			public void saveOperationLog(LogOperateBO logOperateBO) {
			}

			@Override
			public void saveModifyLog(LogModifyBO modifyBO) {
			}

		};
	}

}
