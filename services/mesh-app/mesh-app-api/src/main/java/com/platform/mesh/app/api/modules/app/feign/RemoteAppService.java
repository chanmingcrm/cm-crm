package com.platform.mesh.app.api.modules.app.feign;

import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.api.modules.app.feign.factory.RemoteAppFallbackFactory;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 应用信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteAppService", value = ServiceNameConst.APP_SERVICE,
		fallbackFactory = RemoteAppFallbackFactory.class)
public interface RemoteAppService {

	/**
	 * 功能描述:
	 * 〈获取模块信息〉
	 * @param moduleBaseId moduleBaseId
	 * @return 正常返回:{@link Result<AppModuleBaseBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value="/api/app/module/base/info/{moduleBaseId}", headers = HttpConst.HEADER_FROM_IN)
	Result<AppModuleBaseBO> getModuleBaseInfoById(@PathVariable("moduleBaseId")Long moduleBaseId);

	/**
	 * 功能描述:
	 * 〈获取模块信息〉
	 * @param moduleBaseIds moduleBaseIds
	 * @return 正常返回:{@link Result<List<AppModuleBaseBO>>}
	 * @author 蝉鸣
	 */
	@PostMapping(value="/api/app/module/base/info", headers = HttpConst.HEADER_FROM_IN)
	Result<List<AppModuleBaseBO>> getModuleBaseInfoByIds(@RequestBody List<Long> moduleBaseIds);

	/**
	 * 功能描述:
	 * 〈获取模块信息〉
	 * @param appTables appTables
	 * @return 正常返回:{@link Result<List<AppModuleBaseBO>>}
	 * @author 蝉鸣
	 */
	@PostMapping(value="/api/app/module/base/info/by/schema", headers = HttpConst.HEADER_FROM_IN)
	Result<List<AppModuleBaseBO>> getModuleBaseInfoBySchema(@RequestBody List<String> appTables);

	/**
	 * 功能描述:
	 * 〈获取模块的子模块〉
	 * @param moduleBaseId moduleBaseId
	 * @return 正常返回:{@link Result<List<AppModuleBaseBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value="/api/app/module/base/child/{moduleBaseId}", headers = HttpConst.HEADER_FROM_IN)
	Result<List<AppModuleBaseBO>> getModuleBaseChildById(@PathVariable("moduleBaseId")Long moduleBaseId);

	/**
	 * 功能描述:
	 * 〈获取初始化Es的模块〉
	 * @param moduleBaseId moduleBaseId
	 * @return 正常返回:{@link Result<List<AppModuleBaseBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value="/api/app/module/base/init/es/{moduleBaseId}", headers = HttpConst.HEADER_FROM_IN)
	Result<List<AppModuleBaseBO>> initModuleBaseEs(@PathVariable("moduleBaseId")Long moduleBaseId);

	/**
	 * 功能描述:
	 * 〈获取表单字段列表结构〉
	 * @param moduleId moduleId
	 * @param formId formId
	 * @return 正常返回:{@link Result<List<AppFormColumnBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value="/api/app/form/column/list/{moduleId}/{formId}", headers = HttpConst.HEADER_FROM_IN)
	Result<List<AppFormColumnBO>> getFormColumnList(@PathVariable("moduleId")Long moduleId
			, @PathVariable("formId")Long formId);

	/**
	 * 功能描述:
	 * 〈根据moduleId 表单类型快速获取默认字段信息〉
	 * @param moduleId moduleId
	 * @param formType formType
	 * @return 正常返回:{@link Result<List<AppFormColumnBO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "根据moduleId 业务字段类型快速获取默认字段信息")
	@PostMapping("/api/app/form/column/fast/form/type/{moduleId}/{formType}")
	Result<List<AppFormColumnBO>> fastColumnByModuleAndFormType(@PathVariable("moduleId")Long moduleId, @PathVariable("formType")Integer formType);

	/**
	 * 功能描述:
	 * 〈获取表单字段树结构〉
	 * @param moduleId moduleId
	 * @param formId formId
	 * @return 正常返回:{@link Result<List<AppFormColumnBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value="/api/app/form/column/tree/{moduleId}/{formId}", headers = HttpConst.HEADER_FROM_IN)
	Result<List<AppFormColumnBO>> getFormColumnTree(@PathVariable("moduleId")Long moduleId
			, @PathVariable("formId")Long formId);

	/**
	 * 功能描述:
	 * 〈查询可以模块转化配置信息分页〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AppModuleSetTransBO>>}
	 * @author 蝉鸣
	 */
	@PostMapping(value="/api/app/module/set/trans/page", headers = HttpConst.HEADER_FROM_IN)
	Result<PageVO<AppModuleSetTransBO>> getModuleSetTransPage(@RequestBody ModulePageDTO pageDTO);

	/**
	 * 功能描述:
	 * 〈根据Id查询可以模块转化配置信息〉
	 * @param transId transId
	 * @return 正常返回:{@link Result<AppModuleSetTransBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value="/api/app/module/set/trans/by/{transId}", headers = HttpConst.HEADER_FROM_IN)
	Result<AppModuleSetTransBO> getModuleSetTransById(@PathVariable("transId") Long transId);
}
