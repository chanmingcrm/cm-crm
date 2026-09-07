package com.platform.mesh.crm.api.modules.crm.feign.factory;

import com.platform.mesh.crm.api.modules.crm.feign.RemoteCrmService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * @description 用户信息服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteCrmFallbackFactory implements FallbackFactory<RemoteCrmService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteCrmFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteCrmService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteCrmService create(Throwable throwable) {
		log.error("应用服务调用失败:{}", throwable.getMessage());
		return new RemoteCrmService() {
			@Override
			public Result<Void> addCrmDrainage(String object) {
				return Result.error();
			}
		};
	}

}
