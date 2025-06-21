package com.platform.mesh.upms.biz.modules.label.base.controller;

import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.upms.biz.modules.label.base.domain.dto.LabelBaseDTO;
import com.platform.mesh.upms.biz.modules.label.base.domain.po.LabelBase;
import com.platform.mesh.upms.biz.modules.label.base.domain.vo.LabelBaseVO;
import com.platform.mesh.upms.biz.modules.label.base.service.ILabelBaseService;
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
 * @description 标签基础信息
 * @author 蝉鸣
 */
@Tag(description = "LabelBaseController", name = "标签基础")
@RestController
@RequestMapping
public class LabelBaseController extends BaseController{
    @Autowired
    private ILabelBaseService labelBaseService;

    /**
	 * 功能描述:
	 * 〈获取标签基础列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<LabelBaseVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取标签基础分页")
	@PostMapping("/label/base/page")
	public Result<PageVO<LabelBaseVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<LabelBase> baseMPage = MPageUtil.pageEntityToMPage(pageDTO, LabelBase.class);
        MPage<LabelBase> page = labelBaseService.page(baseMPage);
        PageVO<LabelBaseVO> voPage = MPageUtil.convertToVO(page, LabelBaseVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前标签基础信息〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<LabelBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前标签基础信息")
    @GetMapping("/label/base/info/{baseId}")
    public Result<LabelBaseVO> getBaseInfoById(@PathVariable("baseId")Long baseId) {
        LabelBaseVO labelBaseVO = labelBaseService.getBaseInfoById(baseId);
        return Result.success(labelBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增标签基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<LabelBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增标签基础")
    @Log(moduleName = "标签基础管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/label/base/add")
    public Result<LabelBaseVO> addBase(@Validated @RequestBody LabelBaseDTO baseDTO) {
        return Result.success(labelBaseService.addBase(baseDTO));
    }

    /**
     * 功能描述:
     * 〈修改标签基础〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<LabelBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改标签基础")
    @Log(moduleName = "标签基础管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/label/base/edit")
    public Result<LabelBaseVO> editBase(@Validated @RequestBody LabelBaseDTO baseDTO) {
        return Result.success(labelBaseService.editBase(baseDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除标签基础〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除标签基础")
    @Log(moduleName = "标签基础管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/label/base/delete/{baseId}")
    public Result<Boolean> deleteBase(@PathVariable(value = "baseId",required = false)Long baseId) {
        return Result.success(labelBaseService.deleteBase(baseId));
    }

}