package com.platform.mesh.upms.biz.modules.sys.role.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.role.domain.dto.SysRoleDTO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.dto.SysRolePageDTO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;
import com.platform.mesh.upms.biz.modules.sys.role.service.ISysRoleService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 角色信息
 * @author 蝉鸣
 */
@Tag(description = "SysRoleController", name = "角色信息")
@RestController
public class SysRoleController extends BaseController{
    /**
     * 服务对象
     */
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 功能描述:
     * 〈获取角色列表〉
     * @param pageEntity pageEntity
     * @return 正常返回:{@link Result<MPage<SysRoleVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取角色分页")
    @PostMapping("/role/page")
    public Result<PageVO<SysRoleVO>> selectPage(@RequestBody SysRolePageDTO pageEntity) {
        return Result.success(this.sysRoleService.selectPage(pageEntity));
    }

    /**
     * 功能描述:
     * 〈通过主键查询单条数据〉
     * @param id id
     * @return 正常返回:{@link Result<SysRole>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取角色信息")
    @GetMapping("/role/info/{id}")
    public Result<SysRole> getRoleInfo(@PathVariable(value = "id") Long id) {
        return Result.success(this.sysRoleService.getById(id));
    }

    /**
     * 功能描述:
     * 〈新增角色〉
     * @param roleDTO roleDTO
     * @return 正常返回:{@link Result<SysRoleVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增角色")
    @Log(moduleName = "角色管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/role/add")
//    @PreAuthorize("@rolePermission.hasPermi('upms:role:add')")
    public Result<SysRoleVO> addRole(@Validated @RequestBody SysRoleDTO roleDTO) {
        return Result.success(this.sysRoleService.addRole(roleDTO));
    }

    /**
     * 功能描述:
     * 〈修改角色〉
     * @param roleDTO roleDTO
     * @return 正常返回:{@link Result<SysRoleVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改角色")
    @Log(moduleName = "角色管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/role/edit")
//    @PreAuthorize("@rolePermission.hasPermi('upms:role:edit')")
    public Result<SysRoleVO> editRole(@Validated @RequestBody SysRoleDTO roleDTO) {
        return Result.success(this.sysRoleService.editRole(roleDTO));
    }

    /**
     * 功能描述:
     * 〈删除角色〉
     * @param roleId roleId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除角色")
    @Log(moduleName = "角色管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/role/delete/{roleId}")
//    @PreAuthorize("@rolePermission.hasPermi('upms:role:delete')")
    public Result<Boolean> deleteRole(@PathVariable(value = "roleId",required = false)Long roleId) {
        return Result.success(this.sysRoleService.deleteRole(roleId));
    }
}
