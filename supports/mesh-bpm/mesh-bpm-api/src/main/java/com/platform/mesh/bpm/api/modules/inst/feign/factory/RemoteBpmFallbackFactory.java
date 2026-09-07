package com.platform.mesh.bpm.api.modules.inst.feign.factory;

import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmPDTO;
import com.platform.mesh.bpm.api.modules.inst.feign.RemoteBpmService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 蝉鸣
 * 
 * @description 流程服务降级处理
 */
@Component
public class RemoteBpmFallbackFactory implements FallbackFactory<RemoteBpmService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteBpmFallbackFactory.class);

	@Override
	public RemoteBpmService create(Throwable throwable) {
		log.error("流程服务调用失败:{}", throwable.getMessage());
		return new RemoteBpmService() {

			@Override
			public Result<PageVO<Long>> getRunDataIdsByModuleSchema(BpmPDTO pageDTO) {
				return Result.error();
			}

			@Override
			public Result<Map<String, Long>> getRunDataNumByModuleSchema(BpmPDTO pageDTO) {
				return Result.error();
			}
		};
	}

}
