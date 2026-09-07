package com.platform.mesh.upms.api.modules.conf.feign.factory;

import com.platform.mesh.upms.api.modules.conf.domian.bo.ConfSysSetBO;
import com.platform.mesh.upms.api.modules.conf.feign.RemoteConfService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description 配置信息服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteConfFallbackFactory implements FallbackFactory<RemoteConfService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteConfFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteConfService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteConfService create(Throwable throwable) {
		log.error("用户服务调用失败:{}", throwable.getMessage());
		return new RemoteConfService() {
			@Override
			public Result<List<ConfSysSetBO>> selectList(@RequestParam("confSource") Integer confSource) {
				return Result.error();
			}
		};
	}

}
