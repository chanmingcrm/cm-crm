package com.platform.mesh.uaa.api.modules.token.feign.factory;

import com.platform.mesh.uaa.api.modules.token.feign.RemoteTokenService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description 令牌管理服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteTokenFallbackFactory implements FallbackFactory<RemoteTokenService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteTokenFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteTokenService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteTokenService create(Throwable throwable) {
		log.error("令牌管理服务调用失败:{}", throwable.getMessage());
		return new RemoteTokenService() {

//			@Override
//			public R<TableDataInfo> getTokenPage(TokenBO tokenBO) {
//				return R.fail();
//			}

			@Override
			public Result<Void> removeToken(String token) {
				return Result.error();
			}
		};
	}

}
