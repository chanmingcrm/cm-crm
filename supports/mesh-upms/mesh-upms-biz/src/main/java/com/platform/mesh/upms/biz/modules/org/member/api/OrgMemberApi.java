package com.platform.mesh.upms.biz.modules.org.member.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 成员信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "OrgMemberApi", name = "成员信息API")
@RestController
public class OrgMemberApi extends BaseController {

	@Autowired
	private IOrgMemberService orgMemberService;

	/**
	 * 功能描述:
	 * 〈通过ids获取组织成员信息〉
	 * @param memberIds memberIds
	 * @return 正常返回:{@link Result<List<OrgMemberBO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取成员信息缓存")
	@PostMapping("/api/org/member/by/ids")
	public Result<List<OrgMemberBO>> getOrgMemberByIds(@RequestBody List<Long> memberIds) {
		List<OrgMemberBO> orgMembers = orgMemberService.getOrgMemberByIds(memberIds);
		return Result.success(BeanUtil.copyToList(orgMembers,OrgMemberBO.class));
	}

	/**
	 * 功能描述:
	 * 〈通过ids获取组织成员信息〉
	 * @param userIds userIds
	 * @return 正常返回:{@link Result<List<OrgMemberBO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "根据用户ID获取成员信息缓存")
	@PostMapping("/api/org/member/by/user/ids")
	public Result<List<OrgMemberBO>> getOrgMemberByUserIds(@RequestBody List<Long> userIds) {
		List<OrgMember> orgMembers = orgMemberService.getOrgMemberByUserIds(userIds);
		return Result.success(BeanUtil.copyToList(orgMembers,OrgMemberBO.class));
	}

	/**
	 * 功能描述:
	 * 〈获取账户下所有的成员〉
	 * @param memberName memberName
	 * @return 正常返回:{@link Result<OrgMemberBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取成员信息缓存")
	@GetMapping("/api/org/member/by/{memberName}")
	public Result<OrgMemberBO> getOrgMemberByNameFirst(@PathVariable("memberName") String memberName){
		OrgMemberBO orgMember = orgMemberService.getOrgMemberByNameFirst(memberName);
		return Result.success(orgMember);
	}

	/**
	 * 功能描述:
	 * 〈获取根据userId获取成员信息〉
	 * @param tenantId tenantId
	 * @param userId userId
	 * @return 正常返回:{@link Result<OrgMemberBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取成员信息缓存")
	@GetMapping("/api/org/member/by/user/{userId}")
	public Result<OrgMemberBO> getOrgMemberByUserIdFirst(@PathVariable("userId") Long userId){
		OrgMemberBO orgMember = orgMemberService.getOrgMemberByUserIdFirst(userId);
		return Result.success(orgMember);
	}

	/**
	 * 功能描述:
	 * 〈获取直属上级〉
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<OrgMemberRelBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取直属上级")
	@GetMapping("/api/org/member/leader/direct/by/account/id")
	public Result<OrgMemberBO> getLeaderDirect(@RequestParam("accountId") Long accountId) {
		OrgMemberBO leader = orgMemberService.getLeaderDirect(accountId);
		return Result.success(leader);
	}

	/**
	 * 功能描述:
	 * 〈获取多层上级〉
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<List<OrgMemberRelBO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取多层上级")
	@GetMapping("/api/org/member/leader/loop/by/account/id")
	public Result<List<OrgMemberBO>> getLeaderLoop(@RequestParam("accountId") Long accountId) {
		List<OrgMemberBO> leaders = orgMemberService.getLeaderLoop(accountId);
		return Result.success(leaders);
	}
}
