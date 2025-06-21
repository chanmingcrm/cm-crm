package com.platform.mesh.upms.biz.modules.sys.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.user.domain.dto.SysUserDTO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.dto.SysUserPageDTO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysUserInfoVO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysUserVO;
import com.platform.mesh.upms.biz.modules.sys.user.exception.UserExceptionEnum;
import com.platform.mesh.upms.biz.modules.sys.user.mapper.SysUserMapper;
import com.platform.mesh.upms.biz.modules.sys.user.service.ISysUserService;
import com.platform.mesh.upms.biz.modules.sys.user.service.manual.SysUserServiceManual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 用户信息
 * @author 蝉鸣
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@Autowired
	private SysUserServiceManual sysUserServiceManual;

	/**
	 * 功能描述:
	 * 〈根据条件分页查询用户列表〉
	 * @param sysUserPageDTO sysUserPageDTO
	 * @return 正常返回:{@link MPage<SysUserVO>}
	 * @author 蝉鸣
	 */
	@Override
	public PageVO<SysUserVO> selectPage(SysUserPageDTO sysUserPageDTO) {
		MPage<SysUser> mPage = MPageUtil.pageEntityToMPage(sysUserPageDTO, SysUser.class);
		MPage<SysUser> page = this.getBaseMapper().selectMPage(mPage,sysUserPageDTO);
		//封装用户信息
		return sysUserServiceManual.packUserPackVO(page);
	}

	/**
	 * 通过用户名查询用户(此接口会隐藏部分信息,请对号入座使用)
	 * @param accountCode 帐户名
	 * @param sourceFlag 账户来源
	 * @return 用户对象信息
	 */
	@Override
	public SysAccountInfoBO getUserInfoByAccountCode(String accountCode, Integer sourceFlag) {
		//根据账户名称获取帐户信息
		SysAccount account = sysUserServiceManual.getByAccountCode(accountCode,sourceFlag);
		//获取用户信息
		SysUser sysUser = this.getById(account.getUserId());
		//获取VO
		return sysUserServiceManual.getLoginUserInfoBO(sysUser, account);
	}

	/**
	 * 功能描述:
	 * 〈根据用户id 获取用户信息〉
	 * @param accountId accountId
	 * @return 正常返回:{@link SysUserInfoVO}
	 * @author 蝉鸣
	 */
	@Override
	public SysUserInfoVO getUserInfoByAccountId(Long accountId) {
		//根据账户名称获取帐户信息
		SysAccount account = sysUserServiceManual.getByAccountId(accountId);
		//获取用户信息
		SysUser sysUser = this.getById(account.getUserId());
		//获取VO
		return sysUserServiceManual.getInfoVO(sysUser, account);
	}

	@Override
	public SysUser getUserById(Long userId) {
		return getById(userId);
	}

	/***
	 * 功能描述:
	 * 〈新增用户〉
	 * @param sysUserDTO sysUserDTO
	 * @return 正常返回:{@link SysUserInfoVO}
	 * @author 蝉鸣
	 * @since 2024/9/3 10:30
	 */
	@Override
	public SysUserVO addUser(SysUserDTO sysUserDTO) {
		if(ObjectUtil.isEmpty(sysUserDTO.getPhone())){
			throw UserExceptionEnum.ADD_NO_ARGS.getBaseException();
		}
		SysUser user = BeanUtil.copyProperties(sysUserDTO,SysUser.class);
		List<SysUser> sysUsers = this.lambdaQuery().eq(SysUser::getPhone, sysUserDTO.getPhone()).list();
		if(CollUtil.isEmpty(sysUsers)){
			this.save(user);
		}else{
			user = CollUtil.getFirst(sysUsers);
		}
		//添加账户
		SysAccount sysAccount = sysUserServiceManual.initAccount(user);
		return BeanUtil.copyProperties(user,SysUserVO.class);
	}

	/***
	 * 功能描述:
	 * 〈编辑用户〉
	 * @param sysUserDTO sysUserDTO
	 * @return 正常返回:{@link SysUserInfoVO}
	 * @author 蝉鸣
	 * @since 2024/9/3 10:30
	 */
	@Override
	public SysUserVO editUser(SysUserDTO sysUserDTO) {
		SysUser user = BeanUtil.copyProperties(sysUserDTO,SysUser.class);
		updateById(user);
		return BeanUtil.copyProperties(user,SysUserVO.class);
	}

	/***
	 * 功能描述:
	 * 〈删除用户〉
	 * @param userId userId
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 * @since 2024/9/3 10:31
	 */
	@Override
	public Boolean deleteSysUser(Long userId) {
		return removeById(userId);
	}

}
