package com.platform.mesh.uaa.api.modules.client.feign.factory;

import com.platform.mesh.uaa.api.modules.client.domain.bo.Oauth2RegisteredClientBO;
import com.platform.mesh.uaa.api.modules.client.feign.RemoteOauth2RegisteredClientService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author 蝉鸣
 * 
 * @description Oauth2服务降级处理
 */
@Component
public class RemoteOauth2RegisteredClientFallbackFactory implements FallbackFactory<RemoteOauth2RegisteredClientService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteOauth2RegisteredClientFallbackFactory.class);

	@Override
	public RemoteOauth2RegisteredClientService create(Throwable throwable) {
		log.error("Oauth2服务调用失败:{}", throwable.getMessage());
		return new RemoteOauth2RegisteredClientService() {

			@Override
			public Result<Oauth2RegisteredClientBO> getClientDetailsById(String clientId) {
				return Result.error();
			}
		};
	}

}
