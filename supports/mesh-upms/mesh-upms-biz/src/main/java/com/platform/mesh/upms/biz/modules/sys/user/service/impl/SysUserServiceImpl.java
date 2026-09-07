package com.platform.mesh.upms.biz.modules.sys.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
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
		MPage<SysUserVO> page = this.getBaseMapper().selectMPage(mPage,sysUserPageDTO);
		//封装用户信息
		sysUserServiceManual.packUserVO(page.getRecords());
        return MPageUtil.convertToVO(page, SysUserVO.class);
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
		SysAccountBO accountBO = sysUserServiceManual.getByAccountCode(accountCode,sourceFlag);
		if(ObjectUtil.isEmpty(accountBO)){
			return null;
		}
		//获取用户信息
		SysUser sysUser = this.getById(accountBO.getUserId());
		//获取VO
		return sysUserServiceManual.getLoginUserInfoBO(sysUser, accountBO);
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
        if(ObjectUtil.isEmpty(account)){
            return new SysUserInfoVO();
        }
		//获取用户信息
		SysUser sysUser = this.getById(account.getUserId());
		//获取VO
		return sysUserServiceManual.getInfoVO(sysUser, account);
	}

	@Override
	public SysUserVO getUserById(Long userId) {
        SysUser sysUser = getById(userId);
        return sysUserServiceManual.getUserVO(sysUser);
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
            user.setNickName(sysUserDTO.getUserName());
			this.save(user);
		}else{
			user = CollUtil.getFirst(sysUsers);
            user.setNickName(sysUserDTO.getUserName());
            this.updateById(user);
		}
		//添加账户
		OrgLevel level = sysUserServiceManual.getDefaultLevel(sysUserDTO.getPostIds());
		List<SysAccount> sysAccounts = sysUserServiceManual.initAccount(user, level);
		//添加人员与角色关系
		sysUserServiceManual.initUserRoleRel(user.getUserId(),sysUserDTO.getRoleIds());
		//添加人员与组织关系
		sysUserServiceManual.initUserPostRel(user.getUserId(),sysUserDTO.getPostIds());
        SysUserVO sysUserVO = BeanUtil.copyProperties(user, SysUserVO.class);
        sysUserVO.setUserName(user.getNickName());
        return sysUserVO;
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
        user.setNickName(sysUserDTO.getUserName());
		updateById(user);
		//同步修改账户，手机号
		sysUserServiceManual.editPhone(user.getUserId(),sysUserDTO.getPhone());
		//同步修改账户，成员昵称
		sysUserServiceManual.editNickName(user.getUserId(),sysUserDTO.getUserName());
		//添加人员与角色关系
		sysUserServiceManual.initUserRoleRel(user.getUserId(),sysUserDTO.getRoleIds());
		//添加人员与组织关系
		sysUserServiceManual.initUserPostRel(user.getUserId(),sysUserDTO.getPostIds());
		//清除缓存
		UserCacheUtil.clearSysAccountInfoCache(UserCacheUtil.getAccountId());
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
        //删除账号
        sysUserServiceManual.delAccount(userId);
        //删除成员
        sysUserServiceManual.delMember(userId);
        //设置人员账号为已删除
        return lambdaUpdate().set(SysUser::getDelFlag, YesOrNoEnum.NO.getValue()).eq(SysUser::getUserId,userId).update();
	}

	@Override
	public List<Long> getUserIdsByModules(List<Long> moduleIds) {
		if (CollUtil.isEmpty(moduleIds)) {
			return CollUtil.newArrayList();
		}
		return getBaseMapper().getUserIdsByModules(moduleIds);
	}

}
