package com.platform.mesh.app.biz.modules.app.compbase.controller;

import com.platform.mesh.app.biz.modules.app.compbase.domain.dto.AppCompBaseDTO;
import com.platform.mesh.app.biz.modules.app.compbase.domain.dto.AppCompBasePageDTO;
import com.platform.mesh.app.biz.modules.app.compbase.domain.po.AppCompBase;
import com.platform.mesh.app.biz.modules.app.compbase.domain.vo.AppCompBaseVO;
import com.platform.mesh.app.biz.modules.app.compbase.service.IAppCompBaseService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 页面组件信息
 * @author 蝉鸣
 */
@Tag(description = "AppCompBaseController", name = "页面组件")
@RestController
public class AppCompBaseController extends BaseController{
    @Autowired
    private IAppCompBaseService appCompBaseService;

    /**
	 * 功能描述:
	 * 〈获取页面组件列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppCompBaseVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取页面组件分页")
	@PostMapping("/app/comp/base/page")
	public Result<PageVO<AppCompBaseVO>> selectPage(@RequestBody AppCompBasePageDTO pageDTO) {
        MPage<AppCompBase> page = appCompBaseService.selectPage(pageDTO);
        PageVO<AppCompBaseVO> voPage = MPageUtil.convertToVO(page, AppCompBaseVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前页面组件信息〉
     * @param compBaseId compBaseId
     * @return 正常返回:{@link Result<AppCompBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前页面组件信息")
    @GetMapping("/app/comp/base/info/{compBaseId}")
    public Result<AppCompBaseVO> getCompBaseInfoById(@PathVariable("compBaseId")Long compBaseId) {
        AppCompBaseVO appCompBaseVO = appCompBaseService.getCompBaseInfoById(compBaseId);
        return Result.success(appCompBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增页面组件〉
     * @param compBaseDTO compBaseDTO
     * @return 正常返回:{@link Result<AppCompBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增页面组件")
    @Log(moduleName = "页面组件管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/comp/base/add")
    public Result<AppCompBaseVO> addCompBase(@Validated @RequestBody AppCompBaseDTO compBaseDTO) {
        return Result.success(appCompBaseService.addCompBase(compBaseDTO));
    }

    /**
     * 功能描述:
     * 〈修改页面组件〉
     * @param compBaseDTO compBaseDTO
     * @return 正常返回:{@link Result<AppCompBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改页面组件")
    @Log(moduleName = "页面组件管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/comp/base/edit")
    public Result<AppCompBaseVO> editCompBase(@Validated @RequestBody AppCompBaseDTO compBaseDTO) {
        return Result.success(appCompBaseService.editCompBase(compBaseDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除页面组件〉
     * @param compBaseId compBaseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除页面组件")
    @Log(moduleName = "页面组件管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/comp/base/delete/{compBaseId}")
    public Result<Boolean> deleteCompBase(@PathVariable(value = "compBaseId",required = false)Long compBaseId) {
        return Result.success(appCompBaseService.deleteCompBase(compBaseId));
    }

}