package com.platform.mesh.upms.biz.modules.org.memberuserrel.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberUserRelBO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.service.IOrgMemberUserRelService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 成员信息
 * @author 蝉鸣
 */
@Hidden
@RestController
@Tag(description = "OrgMemberUserRelApi", name = "成员用户关系信息")
public class OrgMemberUserRelApi extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IOrgMemberUserRelService orgMemberUserRelService;



    /**
     * 功能描述:
     * 〈获取成员人员关联信息〉
     * @param memberIds memberIds
     * @return 正常返回:{@link Result<List<OrgMemberUserRelBO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取成员人员关联信息")
    @PostMapping("/api/org/member/user/by/member/ids")
    public Result<List<OrgMemberUserRelBO>> getOrgMemberUserRelByIds(@RequestBody List<Long> memberIds) {
        List<OrgMemberUserRel> orgMemberUserRels = orgMemberUserRelService.lambdaQuery().in(OrgMemberUserRel::getMemberId, memberIds).list();
        return Result.success(BeanUtil.copyToList(orgMemberUserRels,OrgMemberUserRelBO.class));
    }

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param userId userId
     * @return 正常返回:{@link Result<OrgMemberRelBO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取成员默认关联信息")
    @PostMapping("/api/org/member/user/default/rel/by/user/id")
    public Result<OrgMemberRelBO> getOrgMemberUserDefaultRelByUserId(@RequestBody Long userId) {
        OrgMemberRelBO orgMemberRelBO = orgMemberUserRelService.getOrgMemberUserDefaultRelByUserId(userId);
        return Result.success(orgMemberRelBO);
    }

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param memberId memberId
     * @return 正常返回:{@link Result< OrgMemberRelBO >}
     * @author 蝉鸣
     */
    @Operation(summary = "获取成员默认关联信息")
    @PostMapping("/api/org/member/user/default/rel/by/member/id")
    public Result<OrgMemberRelBO> getOrgMemberUserDefaultRelByMemberId(@RequestBody Long memberId) {
        OrgMemberRelBO orgMemberRelBO = orgMemberUserRelService.getOrgMemberUserDefaultRelByMemberId(memberId);
        return Result.success(orgMemberRelBO);
    }

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param memberIds memberIds
     * @return 正常返回:{@link Result<List<OrgMemberRelBO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取成员默认关联信息")
    @PostMapping("/api/org/member/user/default/rel/by/member/ids")
    public Result<List<OrgMemberRelBO>> getOrgMemberUserDefaultRelByIds(@RequestBody List<Long> memberIds) {
        List<OrgMemberRelBO> orgMemberRelBOS = orgMemberUserRelService.getOrgMemberUserDefaultRelByIds(memberIds);
        return Result.success(orgMemberRelBOS);
    }

    /**
     * 功能描述:
     * 〈获取当前账户下所有的下属人员〉
     * @param accountId accountId
     * @return 正常返回:{@link Result<List<OrgMemberRelBO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前账户下所有的下属人员")
    @PostMapping("/api/org/child/user/rel/by/account/id")
    public Result<List<OrgMemberRelBO>> getOrgChildUserRelByAccountId(@RequestBody Long accountId) {
        List<Long> levelIds = orgMemberUserRelService.getOrgChildLevelByAccountId(accountId);
        List<OrgMemberRelBO> orgMemberRelBOS = orgMemberUserRelService.getOrgChildUserRelByLevelIds(levelIds);
        return Result.success(orgMemberRelBOS);
    }

    /**
     * 功能描述:
     * 〈获取当前部门下所有的下属人员〉
     * @param levelIds levelIds
     * @return 正常返回:{@link Result<List<OrgMemberRelBO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前部门下所有的下属人员")
    @PostMapping("/api/org/child/user/rel/by/level/ids")
    public Result<List<OrgMemberRelBO>> getOrgChildUserRelByLevelIds(@RequestBody List<Long> levelIds) {
        List<OrgMemberRelBO> orgMemberRelBOS = orgMemberUserRelService.getOrgChildUserRelByLevelIds(levelIds);
        return Result.success(orgMemberRelBOS);
    }
}
