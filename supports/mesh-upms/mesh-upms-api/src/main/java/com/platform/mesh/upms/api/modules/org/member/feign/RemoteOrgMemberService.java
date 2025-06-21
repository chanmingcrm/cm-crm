package com.platform.mesh.upms.api.modules.org.member.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberUserRelBO;
import com.platform.mesh.upms.api.modules.org.member.feign.factory.RemoteOrgMemberFallbackFactory;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 成员信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteOrgMemberService", value = ServiceNameConst.SYSTEM_SERVICE,
		fallbackFactory = RemoteOrgMemberFallbackFactory.class)
public interface RemoteOrgMemberService {

	/**
	 * 功能描述:
	 * 〈通过ids获取成员信息〉
	 * @param memberIds memberIds
	 * @return 正常返回:{@link Result<List<OrgMemberBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/org/member/by/ids", headers = HttpConst.HEADER_FROM_IN)
	Result<List<OrgMemberBO>> getOrgMemberByIds(@RequestBody List<Long> memberIds);


	/**
	 * 功能描述:
	 * 〈通过ids获取成员信息〉
	 * @param userIds userIds
	 * @return 正常返回:{@link Result<List<OrgMemberBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/org/member/by/user/ids", headers = HttpConst.HEADER_FROM_IN)
	Result<List<OrgMemberBO>> getOrgMemberByUserIds(@RequestBody List<Long> userIds);

	/**
	 * 功能描述:
	 * 〈获取账户下所有的成员〉
	 * @param memberName memberName
	 * @return 正常返回:{@link Result<OrgMemberBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/org/member/by/{memberName}", headers = HttpConst.HEADER_FROM_IN)
	Result<OrgMemberBO> getOrgMemberByName(@PathVariable("memberName") String memberName);

	/**
	 * 功能描述:
	 * 〈通过ids获取组织信息〉
	 * @param levelIds levelIds
	 * @return 正常返回:{@link Result<List<SysOrgBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/org/level/by/ids", headers = HttpConst.HEADER_FROM_IN)
	Result<List<OrgLevelBO>> getOrgLevelByIds(@RequestBody List<Long> levelIds);

	/**
	 * 功能描述:
	 * 〈获取账户下所有的组织〉
	 * @param levelName levelName
	 * @return 正常返回:{@link Result<List<OrgLevelBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/org/level/by/{levelName}", headers = HttpConst.HEADER_FROM_IN)
	Result<OrgLevelBO> getOrgLevelByName(@PathVariable("levelName") String levelName);

	/**
	 * 功能描述:
	 * 〈获取成员人员关联信息〉
	 * @param memberIds memberIds
	 * @return 正常返回:{@link Result<List<OrgMemberUserRelBO>>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/org/member/user/by/member/ids", headers = HttpConst.HEADER_FROM_IN)
	Result<List<OrgMemberUserRelBO>> getOrgMemberUserRelByIds(@RequestBody List<Long> memberIds);

	/**
	 * 功能描述:
	 * 〈获取成员默认关联信息〉
	 * @param memberId memberId
	 * @return 正常返回:{@link Result<OrgMemberRelBO>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/org/member/user/default/rel/by/member/id", headers = HttpConst.HEADER_FROM_IN)
	Result<OrgMemberRelBO> getOrgMemberUserDefaultRelByMemberId(@RequestBody Long memberId);

	/**
	 * 功能描述:
	 * 〈获取成员默认关联信息〉
	 * @param userId userId
	 * @return 正常返回:{@link Result<OrgMemberRelBO>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/org/member/user/default/rel/by/user/id", headers = HttpConst.HEADER_FROM_IN)
	Result<OrgMemberRelBO> getOrgMemberUserDefaultRelByUserId(@RequestBody Long userId);

	/**
	 * 功能描述:
	 * 〈获取成员默认关联信息〉
	 * @param memberIds memberIds
	 * @return 正常返回:{@link Result<OrgMemberRelBO>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/org/member/user/default/rel/by/member/ids", headers = HttpConst.HEADER_FROM_IN)
	Result<List<OrgMemberRelBO>> getOrgMemberUserDefaultRelByIds(@RequestBody List<Long> memberIds);

	/**
	 * 功能描述:
	 * 〈获取当前账户下所有的下属人员〉
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<List<OrgMemberRelBO>>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/org/child/user/rel/by/account/id", headers = HttpConst.HEADER_FROM_IN)
	Result<List<OrgMemberRelBO>> getOrgChildUserRelByAccountId(@RequestBody Long accountId);

}
