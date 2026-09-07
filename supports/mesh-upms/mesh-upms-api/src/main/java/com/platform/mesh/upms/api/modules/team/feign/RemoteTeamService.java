package com.platform.mesh.upms.api.modules.team.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.team.domain.bo.TeamMemberRelBO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseGetDTO;
import com.platform.mesh.upms.api.modules.team.feign.factory.RemoteTeamFallbackFactory;
import com.platform.mesh.utils.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 成员信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteTeamService", value = ServiceNameConst.SYSTEM_SERVICE,
		fallbackFactory = RemoteTeamFallbackFactory.class)
public interface RemoteTeamService {

	/**
	 * 功能描述:
	 * 〈添加团队成员信息〉
	 * @param baseDTO baseDTO
	 * @return 正常返回:{@link Result<Void>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/team/member/add", headers = HttpConst.HEADER_FROM_IN)
	Result<Void> addTeamMember(@RequestBody TeamBaseDTO baseDTO);

	/**
	 * 功能描述:
	 * 〈删除团队成员信息〉
	 * @param delDTO delDTO
	 * @return 正常返回:{@link Result<Void>}
	 * @author 蝉鸣
	 */
	@PostMapping(value = "/api/team/member/delete", headers = HttpConst.HEADER_FROM_IN)
	Result<Void> deleteTeamMember(@RequestBody TeamBaseDelDTO delDTO);

    /**
     * 功能描述:
     * 〈获取团队成员列表〉
     * @param getDTO getDTO
     * @return 正常返回:{@link Result<List<TeamMemberRelBO>>}
     * @author 蝉鸣
     */
    @PostMapping(value = "/api/team/member/get", headers = HttpConst.HEADER_FROM_IN)
    Result<List<TeamMemberRelBO>> getTeamMember(@RequestBody TeamBaseGetDTO getDTO);
}
