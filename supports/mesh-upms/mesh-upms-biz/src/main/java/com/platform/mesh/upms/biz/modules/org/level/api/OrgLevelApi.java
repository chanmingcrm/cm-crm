package com.platform.mesh.upms.biz.modules.org.level.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgInfoBO;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 组织信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "OrgLevelApi", name = "组织信息API")
@RestController
public class OrgLevelApi extends BaseController {

	@Autowired
	private IOrgLevelService orgLevelService;

	/**
	 * 功能描述:
	 * 〈获取组织信息缓存〉
	 * @param levelId levelId
	 * @return 正常返回:{@link Result<SysOrgInfoBO>}
	 * @author 蝉鸣
	 */
	@AuthIgnore
	@Operation(summary = "获取组织信息缓存")
	@GetMapping("/api/org/level/info/{levelId}")
	public Result<SysOrgInfoBO> getOrgInfoByLevelId(@PathVariable("levelId") Long levelId) {
		SysOrgInfoBO sysOrgInfoBO = orgLevelService.getOrgInfoByLevelId(levelId);
		return Result.success(sysOrgInfoBO);
	}

	/**
	 * 功能描述:
	 * 〈通过ids获取组织信息〉
	 * @param levelIds levelIds
	 * @return 正常返回:{@link Result<List<OrgLevelBO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取组织信息缓存")
	@PostMapping("/api/org/level/by/ids")
	public Result<List<OrgLevelBO>> getOrgLevelByIds(@RequestBody List<Long> levelIds) {
		List<OrgLevel> orgLevels = orgLevelService.listByIds(levelIds);
		return Result.success(BeanUtil.copyToList(orgLevels,OrgLevelBO.class));
	}

	/**
	 * 通过账户ID查询用户组织信息
	 * @param accountId 帐户ID
	 * @return 正常返回:{@link Result<List<SysOrgBO>>}
	 */
	@Operation(summary = "通过账户ID查询用户组织信息")
	@GetMapping("/api/org/info/{accountId}")
	public Result<List<SysOrgBO>> getOrgInfoByAccountId(@PathVariable("accountId") Long accountId) {
		List<SysOrgBO> orgBOS = orgLevelService.getOrgInfoByAccountId(accountId);
		return Result.success(orgBOS);
	}

	/**
	 * 功能描述:
	 * 〈获取账户下所有的组织〉
	 * @param levelName levelName
	 * @return 正常返回:{@link Result<OrgLevelBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "通过账户ID查询用户组织信息")
	@GetMapping("/api/org/level/by/{levelName}")
	public Result<OrgLevelBO> getOrgLevelByName(@PathVariable("levelName") String levelName) {
		OrgLevel orgLevel = orgLevelService.lambdaQuery().eq(OrgLevel::getLevelName,levelName).list().getFirst();
		return Result.success(BeanUtil.copyProperties(orgLevel,OrgLevelBO.class));
	}

}
