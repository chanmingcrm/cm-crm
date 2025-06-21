package com.platform.mesh.upms.biz.modules.label.value.controller;

import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.biz.modules.label.value.domain.dto.LabelValueDTO;
import com.platform.mesh.upms.biz.modules.label.value.domain.po.LabelValue;
import com.platform.mesh.upms.biz.modules.label.value.domain.vo.LabelValueVO;
import com.platform.mesh.upms.biz.modules.label.value.service.ILabelValueService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 标签值信息
 * @author 蝉鸣
 */
@Tag(description = "LabelValueController", name = "标签值")
@RestController
@RequestMapping
public class LabelValueController extends BaseController{
    @Autowired
    private ILabelValueService labelValueService;

    /**
	 * 功能描述:
	 * 〈获取标签值列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<LabelValueVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取标签值分页")
	@PostMapping("/label/value/page")
	public Result<PageVO<LabelValueVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<LabelValue> valueMPage = MPageUtil.pageEntityToMPage(pageDTO, LabelValue.class);
        MPage<LabelValue> page = labelValueService.page(valueMPage);
        PageVO<LabelValueVO> voPage = MPageUtil.convertToVO(page, LabelValueVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前标签值信息〉
     * @param valueId valueId
     * @return 正常返回:{@link Result<LabelValueVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前标签值信息")
    @GetMapping("/label/value/info/{valueId}")
    public Result<LabelValueVO> getValueInfoById(@PathVariable("valueId")Long valueId) {
        LabelValueVO labelValueVO = labelValueService.getValueInfoById(valueId);
        return Result.success(labelValueVO);
    }

    /**
     * 功能描述:
     * 〈新增标签值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link Result<LabelValueVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增标签值")
    @Log(moduleName = "标签值管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/label/value/add")
    public Result<LabelValueVO> addValue(@Validated @RequestBody LabelValueDTO valueDTO) {
        return Result.success(labelValueService.addValue(valueDTO));
    }

    /**
     * 功能描述:
     * 〈修改标签值〉
     * @param valueDTO valueDTO
     * @return 正常返回:{@link Result<LabelValueVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改标签值")
    @Log(moduleName = "标签值管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/label/value/edit")
    public Result<LabelValueVO> editValue(@Validated @RequestBody LabelValueDTO valueDTO) {
        return Result.success(labelValueService.editValue(valueDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除标签值〉
     * @param valueId valueId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除标签值")
    @Log(moduleName = "标签值管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/label/value/delete/{valueId}")
    public Result<Boolean> deleteValue(@PathVariable(value = "valueId",required = false)Long valueId) {
        return Result.success(labelValueService.deleteValue(valueId));
    }

}