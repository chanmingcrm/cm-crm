package com.platform.mesh.app.api.modules.app.feign.factory;

import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 用户信息服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteAppFallbackFactory implements FallbackFactory<RemoteAppService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteAppFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteAppService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteAppService create(Throwable throwable) {
		log.error("应用服务调用失败:{}", throwable.getMessage());
		return new RemoteAppService() {

			@Override
			public Result<AppModuleBaseBO> getModuleBaseInfoById(@PathVariable("moduleBaseId")Long moduleBaseId){
				return Result.error();
			}

			@Override
			public Result<List<AppModuleBaseBO>> getModuleBaseInfoByIds(@RequestBody List<Long> moduleBaseIds){
				return Result.error();
			}

			@Override
			public Result<List<AppModuleBaseBO>> getModuleBaseInfoBySchema(@RequestBody List<String> appTables) {
				return Result.error();
			}

			@Override
			public Result<List<AppModuleBaseBO>> getModuleBaseChildById(@PathVariable("moduleBaseId")Long moduleBaseId){
				return Result.error();
			}

			@Override
			public Result<List<AppModuleBaseBO>> initModuleBaseEs(@PathVariable("moduleBaseId")Long moduleBaseId){
				return Result.error();
			}

			@Override
			public Result<List<AppFormColumnBO>> getFormColumnList(@PathVariable("moduleId")Long moduleId
					, @PathVariable("formId")Long formId){
				return Result.error();
			}

			@Override
			public Result<List<AppFormColumnBO>> fastColumnByModuleAndFormType(@PathVariable("moduleId")Long moduleId,@PathVariable("formType") Integer formType) {
				return Result.error();
			}

			@Override
			public Result<List<AppFormColumnBO>> getFormColumnTree(@PathVariable("moduleId")Long moduleId
					, @PathVariable("formId")Long formId){
				return Result.error();
			}

			@Override
			public Result<PageVO<AppModuleSetTransBO>> getModuleSetTransPage(@RequestBody ModulePageDTO pageDTO) {
				return Result.error();
			}

			@Override
			public Result<AppModuleSetTransBO> getModuleSetTransById(@PathVariable("transId")Long transId) {
				return Result.error();
			}
		};
	}

}
