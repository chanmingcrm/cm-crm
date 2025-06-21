package com.platform.mesh.app.biz.modules.app.allgroup.controller;

import com.platform.mesh.app.biz.modules.app.allgroup.domain.dto.AppAllGroupDTO;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.po.AppAllGroup;
import com.platform.mesh.app.biz.modules.app.allgroup.domain.vo.AppAllGroupVO;
import com.platform.mesh.app.biz.modules.app.allgroup.service.IAppAllGroupService;
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
 * @description 模块分组信息
 * @author 蝉鸣
 */
@Tag(description = "AppAllGroupController", name = "模块分组")
@RestController
public class AppAllGroupController extends BaseController{
    @Autowired
    private IAppAllGroupService appAllGroupService;

    /**
	 * 功能描述:
	 * 〈获取模块分组列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppAllGroupVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取模块分组分页")
	@PostMapping("/app/all/group/page")
	public Result<PageVO<AppAllGroupVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AppAllGroup> allGroupMPage = MPageUtil.pageEntityToMPage(pageDTO, AppAllGroup.class);
        MPage<AppAllGroup> page = appAllGroupService.page(allGroupMPage);
        PageVO<AppAllGroupVO> voPage = MPageUtil.convertToVO(page, AppAllGroupVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前模块分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<AppAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分组信息")
    @GetMapping("/app/all/group/info/{allGroupId}")
    public Result<AppAllGroupVO> getAllGroupInfoById(@PathVariable("allGroupId")Long allGroupId) {
        AppAllGroupVO appAllGroupVO = appAllGroupService.getAllGroupInfoById(allGroupId);
        return Result.success(appAllGroupVO);
    }

    /**
     * 功能描述:
     * 〈新增模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<AppAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块分组")
    @Log(moduleName = "模块分组管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/all/group/add")
    public Result<AppAllGroupVO> addAllGroup(@Validated @RequestBody AppAllGroupDTO allGroupDTO) {
        return Result.success(appAllGroupService.addAllGroup(allGroupDTO));
    }

    /**
     * 功能描述:
     * 〈修改模块分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<AppAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改模块分组")
    @Log(moduleName = "模块分组管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/all/group/edit")
    public Result<AppAllGroupVO> editAllGroup(@Validated @RequestBody AppAllGroupDTO allGroupDTO) {
        return Result.success(appAllGroupService.editAllGroup(allGroupDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除模块分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除模块分组")
    @Log(moduleName = "模块分组管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/all/group/delete/{allGroupId}")
    public Result<Boolean> deleteAllGroup(@PathVariable(value = "allGroupId",required = false)Long allGroupId) {
        return Result.success(appAllGroupService.deleteAllGroup(allGroupId));
    }

}