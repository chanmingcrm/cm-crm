package com.platform.mesh.upms.api.modules.sys.user.feign.factory;

import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.*;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 用户信息服务降级处理
 * @author 蝉鸣
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

	private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

	/**
	 * 功能描述:
	 * 〈创建实例〉
	 * @param throwable throwable
	 * @return 正常返回:{@link RemoteUserService}
	 * @author 蝉鸣
	 */
	@Override
	public RemoteUserService create(Throwable throwable) {
		log.error("用户服务调用失败:{}", throwable.getMessage());
		return new RemoteUserService() {
			@Override
			public Result<SysAccountInfoBO> getUserInfoByAccountCode(String accountCode, Integer sourceFlag) {
				return Result.error();
			}

			@Override
			public Result<SysUserBO> getUserInfoByUserId(Long userId) {
				return Result.error();
			}

			@Override
			public Result<SysAccountBO> getAccountInfoByAccountId(Long accountId) {
				return Result.error();
			}

			@Override
			public Result<SysAccountBO> thirdBindAccount(SysAccountBO accountBO) {
				return Result.error();
			}

			@Override
			public Result<List<SysRoleBO>> getRoleInfoByAccountId(Long accountId) {
				return Result.error();
			}

			@Override
			public Result<List<SysRoleBO>> getRoleByIds(List<Long> roleIds) {
				return Result.error();
			}

			@Override
			public Result<List<SysMenuBO>> getMenuInfoByAccountId(Long accountId) {
				return Result.error();
			}

			@Override
			public Result<List<SysOrgBO>> getOrgInfoByAccountId(Long accountId) {
				return Result.error();
			}

			@Override
			public Result<SysOrgInfoBO> getOrgInfoByLevelId(Long levelId) {
				return Result.error();
			}

			@Override
			public Result<List<Long>> getUserIdsByModules(UserMenuBO userMenuBO) {
				return Result.error();
			}

			@Override
			public Result<List<Long>> getAppModules() {
				return Result.error();
			}

		};
	}

}
