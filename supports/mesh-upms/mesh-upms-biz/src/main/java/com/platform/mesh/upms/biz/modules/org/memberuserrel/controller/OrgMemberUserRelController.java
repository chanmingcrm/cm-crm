package com.platform.mesh.upms.biz.modules.org.memberuserrel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto.OrgMemberAddDTO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.service.IOrgMemberUserRelService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 成员信息
 * @author 蝉鸣
 */
@Tag(description = "OrgMemberController", name = "成员用户关系信息")
@RestController
public class OrgMemberUserRelController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IOrgMemberUserRelService orgMemberUserRelService;


    /**
     * 功能描述:
     * 〈新增成员-用户关系〉
     * @param addDTO addDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增成员-人员关系")
    @Log(moduleName = "成员-人员关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/member/user/add")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:add')")
    public Result<Boolean> addMemberUser(@Validated @RequestBody OrgMemberAddDTO addDTO) {
        return Result.success(orgMemberUserRelService.addMemberUser(addDTO));
    }
}
