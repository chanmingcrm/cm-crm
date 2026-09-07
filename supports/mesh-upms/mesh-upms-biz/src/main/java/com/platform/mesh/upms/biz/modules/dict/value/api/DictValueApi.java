package com.platform.mesh.upms.biz.modules.dict.value.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseValueBO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.po.DictValue;
import com.platform.mesh.upms.biz.modules.dict.value.service.IDictValueService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 字典值信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "DictValueApi", name = "字典值")
@RestController
public class DictValueApi extends BaseController{
    @Autowired
    private IDictValueService dictValueService;


	/**
	 * 功能描述:
	 * 〈根据字典值获取字典〉
	 * @param dictName dictName
	 * @return 正常返回:{@link Result<DictBaseValueBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "根据字典值获取字典")
	@GetMapping(value = "/api/dict/value/by/{dictId}/{dictName}")
	public Result<DictBaseValueBO> getFistSysDictByName(@PathVariable("dictId") Long dictId,@PathVariable("dictName") String dictName){
		DictValue dict = dictValueService.lambdaQuery().eq(DictValue::getDictId,dictId).eq(DictValue::getDictName,dictName).list().getFirst();
		return Result.success(BeanUtil.copyProperties(dict, DictBaseValueBO.class));
	}

	/**
	 * 功能描述:
	 * 〈根据字典值获取字典〉
	 * @param dictMac dictMac
	 * @param dictValue dictValue
	 * @return 正常返回:{@link Result<DictBaseValueBO>}
	 * @author 蝉鸣
	 */
	@AuthIgnore
	@GetMapping(value = "/api/dict/value/by/mac")
	public Result<DictBaseValueBO> getFistSysDictByMac(@RequestParam("dictMac") String dictMac
			, @RequestParam("dictValue") Integer dictValue){
		DictValue dict = dictValueService.getFistSysDictByMac(dictMac,dictValue);
		return Result.success(BeanUtil.copyProperties(dict, DictBaseValueBO.class));
	}

}