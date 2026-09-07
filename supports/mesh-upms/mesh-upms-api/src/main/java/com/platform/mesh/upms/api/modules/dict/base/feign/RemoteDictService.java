package com.platform.mesh.upms.api.modules.dict.base.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseBO;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseValueBO;
import com.platform.mesh.upms.api.modules.dict.base.feign.factory.RemoteDictFallbackFactory;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description 用户信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteDictService", value = ServiceNameConst.SYSTEM_SERVICE,
		fallbackFactory = RemoteDictFallbackFactory.class)
public interface RemoteDictService {


	/**
	 * 功能描述:
	 * 〈获取字段〉
	 * @param baseIds baseIds
	 * @return 正常返回:{@link List<DictBaseBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取字段")
	@PostMapping(value = "/api/dict/base/by/ids", headers = HttpConst.HEADER_FROM_IN)
	Result<List<DictBaseBO>> selectDictByIds(@RequestBody List<Long> baseIds);

	/**
	 * 功能描述:
	 * 〈获取所有的子项〉
	 * @param baseId baseId
	 * @return 正常返回:{@link List<DictBaseBO>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/dict/base/child/{baseId}", headers = HttpConst.HEADER_FROM_IN)
	Result<List<DictBaseBO>> getChildDict(@PathVariable(value = "baseId",required = false)Long baseId);

	/**
	 * 功能描述:
	 * 〈根据字典值获取字典〉
	 * @param dictName dictName
	 * @return 正常返回:{@link Result<DictBaseValueBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/dict/value/by/{dictId}/{dictName}", headers = HttpConst.HEADER_FROM_IN)
	Result<DictBaseValueBO> getFistSysDictByName(@PathVariable("dictId") Long dictId,@PathVariable("dictName") String dictName);

	/**
	 * 功能描述:
	 * 〈根据字典值获取字典〉
	 * @param dictMac dictMac
	 * @return 正常返回:{@link Result<DictBaseValueBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/dict/value/by/mac", headers = HttpConst.HEADER_FROM_IN)
	Result<DictBaseValueBO> getFistSysDictByMac(@RequestParam("dictMac") String dictMac, @RequestParam("dictValue") Integer dictValue);

}
