package com.platform.mesh.app.biz.modules.app.base.controller;

import com.platform.mesh.app.biz.modules.app.base.domain.dto.AppBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.base.domain.dto.AppBaseDTO;
import com.platform.mesh.app.biz.modules.app.base.domain.po.AppBase;
import com.platform.mesh.app.biz.modules.app.base.domain.vo.AppBaseVO;
import com.platform.mesh.app.biz.modules.app.base.service.IAppBaseService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
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
 * @description 应用信息
 * @author 蝉鸣
 */
@Tag(description = "AppBaseController", name = "应用")
@RestController
public class AppBaseController extends BaseController{
    @Autowired
    private IAppBaseService appBaseService;

    /**
	 * 功能描述:
	 * 〈获取应用列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppBaseVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取应用分页")
	@PostMapping("/app/base/page")
	public Result<PageVO<AppBaseVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<AppBase> page = appBaseService.selectPage(pageDTO);
        PageVO<AppBaseVO> voPage = MPageUtil.convertToVO(page, AppBaseVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前应用信息〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<AppBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前应用信息")
    @GetMapping("/app/base/info/{baseId}")
    public Result<AppBaseVO> getBaseInfoById(@PathVariable("baseId")Long baseId) {
        AppBaseVO appBaseVO = appBaseService.getBaseInfoById(baseId);
        return Result.success(appBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增应用〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<AppBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增应用")
    @Log(moduleName = "应用管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/base/add")
    public Result<AppBaseVO> addBase(@Validated @RequestBody AppBaseDTO baseDTO) {
        return Result.success(appBaseService.addBase(baseDTO));
    }

    /**
     * 功能描述:
     * 〈修改应用〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<AppBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改应用")
    @Log(moduleName = "应用管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/base/edit")
    public Result<AppBaseVO> editBase(@Validated @RequestBody AppBaseDTO baseDTO) {
        return Result.success(appBaseService.editBase(baseDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除应用〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除应用")
    @Log(moduleName = "应用管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/base/delete/{baseId}")
    public Result<Boolean> deleteBase(@PathVariable(value = "baseId",required = false)Long baseId) {
        return Result.success(appBaseService.deleteBase(baseId));
    }

    /**
     * 功能描述:
     * 〈拷贝应用〉
     * @param copyDTO copyDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "拷贝应用")
    @PostMapping("/app/base/app/copy")
    public Result<Boolean> copyAppBase(@RequestBody AppBaseCopyDTO copyDTO) {
        return Result.success(appBaseService.copyAppBase(copyDTO));
    }
}
