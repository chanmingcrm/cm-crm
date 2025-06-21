package com.platform.mesh.app.biz.modules.app.allgrouprel.controller;

import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.dto.AppAllGroupRelDTO;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.po.AppAllGroupRel;
import com.platform.mesh.app.biz.modules.app.allgrouprel.domain.vo.AppAllGroupRelVO;
import com.platform.mesh.app.biz.modules.app.allgrouprel.service.IAppAllGroupRelService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.application.domain.dto.PageDTO;
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
 * @description 模块分组关联信息
 * @author 蝉鸣
 */
@Tag(description = "AppAllGroupRelController", name = "模块分组关联")
@RestController
public class AppAllGroupRelController extends BaseController{
    @Autowired
    private IAppAllGroupRelService appAllGroupRelService;

    /**
	 * 功能描述:
	 * 〈获取模块分组关联列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppAllGroupRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取模块分组关联分页")
	@PostMapping("/app/all/group/rel/page")
	public Result<PageVO<AppAllGroupRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AppAllGroupRel> allGroupRelMPage = MPageUtil.pageEntityToMPage(pageDTO, AppAllGroupRel.class);
        MPage<AppAllGroupRel> page = appAllGroupRelService.page(allGroupRelMPage);
        PageVO<AppAllGroupRelVO> voPage = MPageUtil.convertToVO(page, AppAllGroupRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前模块分组关联信息〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Result<AppAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分组关联信息")
    @GetMapping("/app/all/group/rel/info/{allGroupRelId}")
    public Result<AppAllGroupRelVO> getAllGroupRelInfoById(@PathVariable("allGroupRelId")Long allGroupRelId) {
        AppAllGroupRelVO appAllGroupRelVO = appAllGroupRelService.getAllGroupRelInfoById(allGroupRelId);
        return Result.success(appAllGroupRelVO);
    }

    /**
     * 功能描述:
     * 〈新增模块分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link Result<AppAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块分组关联")
    @Log(moduleName = "模块分组关联管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/all/group/rel/add")
    public Result<AppAllGroupRelVO> addAllGroupRel(@Validated @RequestBody AppAllGroupRelDTO allGroupRelDTO) {
        return Result.success(appAllGroupRelService.addAllGroupRel(allGroupRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改模块分组关联〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link Result<AppAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改模块分组关联")
    @Log(moduleName = "模块分组关联管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/all/group/rel/edit")
    public Result<AppAllGroupRelVO> editAllGroupRel(@Validated @RequestBody AppAllGroupRelDTO allGroupRelDTO) {
        return Result.success(appAllGroupRelService.editAllGroupRel(allGroupRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除模块分组关联〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除模块分组关联")
    @Log(moduleName = "模块分组关联管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/all/group/rel/delete/{allGroupRelId}")
    public Result<Boolean> deleteAllGroupRel(@PathVariable(value = "allGroupRelId",required = false)Long allGroupRelId) {
        return Result.success(appAllGroupRelService.deleteAllGroupRel(allGroupRelId));
    }

}