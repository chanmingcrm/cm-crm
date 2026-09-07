package com.platform.mesh.upms.api.modules.org.member.feign.factory;

import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 成员信息服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteOrgMemberFallbackFactory implements FallbackFactory<RemoteOrgMemberService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteOrgMemberFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteUserService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteOrgMemberService create(Throwable throwable) {
		log.error("成员服务调用失败:{}", throwable.getMessage());
		return new RemoteOrgMemberService() {
			@Override
			public Result<List<OrgMemberBO>> getOrgMemberByIds(List<Long> memberIds) {
				return Result.error();
			}

			@Override
			public Result<List<OrgMemberBO>> getOrgMemberByUserIds(List<Long> userIds) {
				return Result.error();
			}

			@Override
			public Result<OrgMemberBO> getOrgMemberByName(String memberName) {
				return Result.error();
			}

			@Override
			public Result<OrgMemberBO> getOrgMemberByUserId(Long userId) {
				return Result.error();
			}

			@Override
			public Result<List<OrgLevelBO>> getOrgLevelByIds(List<Long> levelIds) {
				return Result.error();
			}

			@Override
			public Result<OrgLevelBO> getOrgLevelByName(String levelName) {
				return Result.error();
			}

			@Override
			public Result<OrgMemberRelBO> getOrgMemberUserDefaultRelByUserId(Long userId) {
				return null;
			}

			@Override
			public Result<OrgMemberRelBO> getOrgMemberUserDefaultRelByMemberId(Long memberId) {
				return null;
			}

			@Override
			public Result<List<OrgMemberRelBO>> getOrgMemberUserDefaultRelByIds(List<Long> memberIds) {
				return Result.error();
			}

			@Override
			public Result<List<OrgMemberRelBO>> getOrgChildUserRelByAccountId(Long accountId) {
				return Result.error();
			}

			@Override
			public Result<OrgMemberBO> getLeaderDirect(Long accountId) {
				return Result.error();
			}

			@Override
			public Result<List<OrgMemberBO>> getLeaderLoop(Long accountId) {
				return Result.error();
			}
		};
	}

}
