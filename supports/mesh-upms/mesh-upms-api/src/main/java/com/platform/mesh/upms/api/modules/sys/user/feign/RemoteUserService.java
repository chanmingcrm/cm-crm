package com.platform.mesh.upms.api.modules.sys.user.feign;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.*;
import com.platform.mesh.upms.api.modules.sys.user.feign.factory.RemoteUserFallbackFactory;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 用户信息服务
 * @author 蝉鸣
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConst.SYSTEM_SERVICE,
		fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

	/**
	 * 功能描述:
	 * 〈通过用户名查询用户〉
	 * @param accountCode accountCode
	 * @param sourceFlag sourceFlag
	 * @return 正常返回:{@link Result<SysAccountInfoBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/user/info/{accountCode}/{sourceFlag}", headers = HttpConst.HEADER_FROM_IN)
	Result<SysAccountInfoBO> getUserInfoByAccountCode(@PathVariable("accountCode") String accountCode, @PathVariable("sourceFlag") Integer sourceFlag);

	/**
	 * 功能描述:
	 * 〈通过用户ID查询用户〉
	 * @param userId userId
	 * @return 正常返回:{@link Result<SysUserBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/user/info/{userId}", headers = HttpConst.HEADER_FROM_IN)
	Result<SysUserBO> getUserInfoByUserId(@PathVariable("userId") Long userId);

	/**
	 * 功能描述:
	 * 〈通过账户ID查询账户信息〉
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<SysAccountBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/account/info/{accountId}", headers = HttpConst.HEADER_FROM_IN)
	Result<SysAccountBO> getAccountInfoByAccountId(@PathVariable("accountId") Long accountId);

	/**
	 * 功能描述:
	 * 〈第三方账户登录绑定账户〉
	 * @param accountBO accountBO
	 * @return 正常返回:{@link Result<SysAccountBO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "第三方账户登录绑定账户")
	@PostMapping(value = "/api/account/bind/third", headers = HttpConst.HEADER_FROM_IN)
	Result<SysAccountBO> thirdBindAccount(@RequestBody SysAccountBO accountBO);

	/**
	 * 功能描述:
	 * 〈通过账户ID查询角色信息〉
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<List<SysRoleBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/role/info/{accountId}", headers = HttpConst.HEADER_FROM_IN)
	Result<List<SysRoleBO>> getRoleInfoByAccountId(@PathVariable("accountId") Long accountId);

	/**
	 * 功能描述:
	 * 〈通角色ID查询角色信息〉
	 * @param roleIds roleIds
	 * @return 正常返回:{@link Result<List<SysRoleBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/sys/role/by/ids", headers = HttpConst.HEADER_FROM_IN)
	Result<List<SysRoleBO>> getRoleByIds(@RequestBody List<Long> roleIds);

	/**
	 * 功能描述:
	 * 〈通过账户ID查询菜单信息〉
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<List<SysRoleBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/menu/info/{accountId}", headers = HttpConst.HEADER_FROM_IN)
	Result<List<SysMenuBO>> getMenuInfoByAccountId(@PathVariable("accountId") Long accountId);

	/**
	 * 功能描述:
	 * 〈获取组织信息缓存〉
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<List<SysOrgBO>>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/org/info/{accountId}", headers = HttpConst.HEADER_FROM_IN)
	Result<List<SysOrgBO>> getOrgInfoByAccountId(@PathVariable("accountId") Long accountId);

	/**
	 * 功能描述:
	 * 〈获取组织信息缓存〉
	 * @param levelId levelId
	 * @return 正常返回:{@link Result<SysOrgInfoBO>}
	 * @author 蝉鸣
	 */
	@GetMapping(value = "/api/org/level/info/{levelId}", headers = HttpConst.HEADER_FROM_IN)
	Result<SysOrgInfoBO> getOrgInfoByLevelId(@PathVariable("levelId") Long levelId);

}
