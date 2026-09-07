package com.platform.mesh.app.biz.modules.app.formcolumnrole.controller;

import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.dto.AppFormColumnRoleAddDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.dto.AppFormColumnRolePageDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.po.AppFormColumnRole;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.domain.vo.AppFormColumnRoleVO;
import com.platform.mesh.app.biz.modules.app.formcolumnrole.service.IAppFormColumnRoleService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 表单字段权限信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnRoleController", name = "表单字段权限")
@RestController
public class AppFormColumnRoleController extends BaseController {

    @Autowired
    private IAppFormColumnRoleService appFormColumnRoleService;

    /**
     * 功能描述:
     * 【获取表单字段权限分页】
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<AppFormColumnRoleVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取表单字段权限分页")
    @PostMapping("/app/form/column/role/page")
    public Result<PageVO<AppFormColumnRoleVO>> selectPage(@RequestBody AppFormColumnRolePageDTO pageDTO) {
        MPage<AppFormColumnRole> formColumnRoleMPage = MPageUtil.pageEntityToMPage(pageDTO, AppFormColumnRole.class);
        MPage<AppFormColumnRole> page = appFormColumnRoleService
                .lambdaQuery()
                .eq(AppFormColumnRole::getModuleId,pageDTO.getModuleId())
                .eq(AppFormColumnRole::getFormId,pageDTO.getFormId())
                .eq(AppFormColumnRole::getRoleId,pageDTO.getRoleId())
                .page(formColumnRoleMPage);
        PageVO<AppFormColumnRoleVO> voPage = MPageUtil.convertToVO(page, AppFormColumnRoleVO.class);
        return Result.success(voPage);
    }

    /**
     * 功能描述:
     * 【新增表单字段权限】
     * @param addDTO addDTO
     * @return 正常返回:{@link Result<AppFormColumnRoleVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增表单字段权限")
    @PostMapping("/app/form/column/role/add")
    public Result<Boolean> addFormColumnRole(@Validated @RequestBody AppFormColumnRoleAddDTO addDTO) {
        return Result.success(appFormColumnRoleService.addFormColumnRole(addDTO));
    }

    /**
     * 功能描述:
     * 【删除表单字段权限】
     * @param roleId roleId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除表单字段权限")
    @PostMapping("/app/form/column/role/delete")
    public Result<Boolean> deleteFormColumnRole(@RequestParam(value = "moduleId", required = false) Long moduleId,
    @RequestParam(value = "formId", required = false) Long formId,
    @RequestParam(value = "roleId", required = false) Long roleId
    ) {
        return Result.success(appFormColumnRoleService.deleteFormColumnRole(moduleId,formId,roleId));
    }

}
