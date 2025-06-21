package com.platform.mesh.upms.biz.modules.dict.value.controller;

import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.dto.DictValueDTO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.dto.DictValuePageDTO;
import com.platform.mesh.upms.biz.modules.dict.value.domain.vo.DictValueVO;
import com.platform.mesh.upms.biz.modules.dict.value.service.IDictValueService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 字典值信息
 * @author 蝉鸣
 */
@Tag(description = "DictValueController", name = "字典值")
@RestController
@RequestMapping
public class DictValueController extends BaseController{
    @Autowired
    private IDictValueService dictValueService;

    /**
	 * 功能描述:
	 * 〈获取字典值列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<DictValueVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取字典值分页")
	@PostMapping("/dict/value/page")
	public Result<PageVO<DictValueVO>> selectPage(@RequestBody DictValuePageDTO pageDTO) {
        PageVO<DictValueVO> voPage = dictValueService.selectPage(pageDTO);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前字典值信息〉
     * @param valueId valueId
     * @return 正常返回:{@link Result<DictValueVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前字典值信息")
    @GetMapping("/dict/value/info/{valueId}")
    public Result<DictValueVO> getValueInfoById(@PathVariable("valueId")Long valueId) {
        DictValueVO dictValueVO = dictValueService.getValueInfoById(valueId);
        return Result.success(dictValueVO);
    }

    /**
     * 功能描述:
     * 〈新增字典值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link Result<DictValueVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增字典值")
    @Log(moduleName = "字典值管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/dict/value/add")
    public Result<DictValueVO> addValue(@Validated @RequestBody DictValueDTO valueDTO) {
        return Result.success(dictValueService.addValue(valueDTO));
    }

    /**
     * 功能描述:
     * 〈修改字典值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link Result<DictValueVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改字典值")
    @Log(moduleName = "字典值管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/dict/value/edit")
    public Result<DictValueVO> editValue(@Validated @RequestBody DictValueDTO valueDTO) {
        return Result.success(dictValueService.editValue(valueDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除字典值〉
     * @param valueId valueId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除字典值")
    @Log(moduleName = "字典值管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/dict/value/delete/{valueId}")
    public Result<Boolean> deleteValue(@PathVariable(value = "valueId",required = false)Long valueId) {
        return Result.success(dictValueService.deleteValue(valueId));
    }

}