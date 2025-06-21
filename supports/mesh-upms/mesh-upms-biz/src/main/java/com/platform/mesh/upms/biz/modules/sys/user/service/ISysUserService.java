package com.platform.mesh.upms.biz.modules.sys.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.dto.SysUserDTO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.dto.SysUserPageDTO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysUserInfoVO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysUserVO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 用户信息
 * @author 蝉鸣
 */
public interface ISysUserService extends IService<SysUser> {

	/**
	 * 功能描述:
	 * 〈通过用户列表〉
	 * @param sysUserPageDTO sysUserPageDTO
	 * @return 正常返回:{@link MPage<SysUserVO>}
	 * @author 蝉鸣
	 */
	PageVO<SysUserVO> selectPage(SysUserPageDTO sysUserPageDTO);

	/**
	 * 通过用户名查询用户
	 * @param accountId accountId
	 * @return 用户对象信息
	 */
	SysUserInfoVO getUserInfoByAccountId(Long accountId);


	/**
	 * 功能描述:
	 * 〈根据主键id查询用户信息〉
	 * @param userId userId
	 * @return 正常返回:{@link SysUser}
	 * @author 蝉鸣
	 * @since 2024/9/3 13:46
	 */
	SysUser getUserById(Long userId);

	/**
	 * 通过用户名查询用户(此接口会隐藏部分信息,请对号入座使用)
	 * @param accountCode 帐户名
	 * @param sourceFlag 账户来源
	 * @return 用户对象信息
	 */
	SysAccountInfoBO getUserInfoByAccountCode(String accountCode, Integer sourceFlag);

	/***
	 * 功能描述:
	 * 〈新增用户信息〉
	 * @param sysUserDTO sysUserDTO
	 * @return 正常返回:{@link SysUserInfoVO}
	 * @author 蝉鸣
	 * @since 2024/9/3 9:54
	 */
	SysUserVO addUser(SysUserDTO sysUserDTO);

	/***
	 * 功能描述:
	 * 〈编辑用户〉
	 * @param sysUserDTO sysUserDTO
	 * @return 正常返回:{@link SysUserInfoVO}
	 * @author 蝉鸣
	 * @since 2024/9/3 9:55
	 */
	SysUserVO editUser(SysUserDTO sysUserDTO);

	/***
	 * 功能描述:
	 * 〈删除用户〉
	 * @param userId userId
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 * @since 2024/9/3 10:27
	 */
	Boolean deleteSysUser(Long userId);

}
