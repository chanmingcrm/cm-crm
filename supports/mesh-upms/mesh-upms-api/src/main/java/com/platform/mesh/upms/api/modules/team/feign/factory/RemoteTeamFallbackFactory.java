package com.platform.mesh.upms.api.modules.team.feign.factory;

import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.upms.api.modules.team.domain.bo.TeamMemberRelBO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseGetDTO;
import com.platform.mesh.upms.api.modules.team.feign.RemoteTeamService;
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
public class RemoteTeamFallbackFactory implements FallbackFactory<RemoteTeamService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteTeamFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteUserService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteTeamService create(Throwable throwable) {
		log.error("团队成员服务调用失败:{}", throwable.getMessage());
		return new RemoteTeamService() {
			@Override
			public Result<Void> addTeamMember(TeamBaseDTO baseDTO) {
				return Result.error();
			}
			@Override
			public Result<Void> deleteTeamMember(TeamBaseDelDTO delDTO) {
				return Result.error();
			}
            @Override
            public Result<List<TeamMemberRelBO>> getTeamMember(TeamBaseGetDTO getDTO) {
                return Result.error();
            }
		};
	}

}
