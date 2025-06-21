package com.platform.mesh.upms.biz.modules.dict.base.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseBO;
import com.platform.mesh.upms.biz.modules.dict.base.domain.po.DictBase;
import com.platform.mesh.upms.biz.modules.dict.base.service.IDictBaseService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 字典基础信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "DictBaseApi", name = "字典基础")
@RestController
public class DictBaseApi extends BaseController{
    @Autowired
    private IDictBaseService dictBaseService;


	/**
	 * 功能描述:
	 * 〈获取字段〉
	 * @param baseIds baseIds
	 * @return 正常返回:{@link List<DictBaseBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取字段")
	@PostMapping("/api/dict/base/by/ids")
	public Result<List<DictBaseBO>> selectDictByIds(@RequestBody List<Long> baseIds) {
		List<DictBase> childDict = dictBaseService.selectDictByIds(baseIds);
		return Result.success(BeanUtil.copyToList(childDict, DictBaseBO.class));
	}

	/**
	 * 功能描述:
	 * 〈获取所有的子项〉
	 * @param baseId baseId
	 * @return 正常返回:{@link List<DictBaseBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取所有的子项")
	@PostMapping("/api/dict/base/child/{baseId}")
	public Result<List<DictBaseBO>> getChildDict(@PathVariable(value = "baseId",required = false)Long baseId) {
		List<DictBase> childDict = dictBaseService.getChildDict(baseId);
		return Result.success(BeanUtil.copyToList(childDict, DictBaseBO.class));
	}
}