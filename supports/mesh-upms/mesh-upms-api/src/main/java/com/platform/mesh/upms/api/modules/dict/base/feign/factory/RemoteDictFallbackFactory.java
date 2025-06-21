package com.platform.mesh.upms.api.modules.dict.base.feign.factory;

import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseBO;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseValueBO;
import com.platform.mesh.upms.api.modules.dict.base.feign.RemoteDictService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 用户信息服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteDictFallbackFactory implements FallbackFactory<RemoteDictService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteDictFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteDictService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteDictService create(Throwable throwable) {
		log.error("用户服务调用失败:{}", throwable.getMessage());
		return new RemoteDictService() {
			@Override
			public Result<List<DictBaseBO>> selectDictByIds(List<Long> baseIds) {
				return Result.error();
			}

			@Override
			public Result<List<DictBaseBO>> getChildDict(Long baseId) {
				return Result.error();
			}

			@Override
			public Result<DictBaseValueBO> getFistSysDictByName(Long baseId,String dictName) {
				return Result.error();
			}
		};
	}

}
