package com.platform.mesh.upms.biz.modules.org.postdatascope.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.upms.biz.modules.org.postdatascope.service.IOrgPostDataScopeService;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.dto.OrgPostDataScopeDTO;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.po.OrgPostDataScope;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 岗位信息
 * @author 蝉鸣
 */
@Tag(description = "OrgPostController", name = "岗位权限信息")
@RestController
public class OrgPostDataScopeController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IOrgPostDataScopeService orgPostDataScopeService;

    /**
     * 功能描述:
     * 〈通过岗位Id查询岗位权限〉
     * @param postId postId
     * @return 正常返回:{@link Result<List<OrgPostDataScope>>}
     * @author 蝉鸣
     */
    @GetMapping("/post/data/scope/{postId}")
    public Result<List<OrgPostDataScope>> selectByPostId(@PathVariable(value = "postId")Long postId) {
        return Result.success(this.orgPostDataScopeService.selectByPostId(postId));
    }



    /**
     * 功能描述:
     * 〈修改岗位-权限〉
     * @param orgPostDataScopeDTO orgPostDataScopeDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改岗位权限")
     @Log(moduleName = "岗位-权限管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/post/data/scope/edit")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:edit')")
    public Result<Boolean> editPostDataScope(@Validated @RequestBody List<OrgPostDataScopeDTO> orgPostDataScopeDTO) {
        return Result.success(orgPostDataScopeService.editPostDataScope(orgPostDataScopeDTO));
    }
}
