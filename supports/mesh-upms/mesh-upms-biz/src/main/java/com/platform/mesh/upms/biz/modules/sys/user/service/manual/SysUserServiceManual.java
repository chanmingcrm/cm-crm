package com.platform.mesh.upms.biz.modules.sys.user.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.api.modules.sys.user.enums.ActiveFlagEnum;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import com.platform.mesh.upms.api.pub.upms.enums.UpmsActionEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.service.IOrgLevelPostRelService;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto.OrgMemberAddDTO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto.OrgMemberUserDTO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.service.IOrgMemberUserRelService;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.AccountVO;
import com.platform.mesh.upms.biz.modules.sys.account.service.ISysAccountService;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.po.SysMenu;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuVO;
import com.platform.mesh.upms.biz.modules.sys.menu.service.ISysMenuService;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;
import com.platform.mesh.upms.biz.modules.sys.role.service.ISysRoleService;
import com.platform.mesh.upms.biz.modules.sys.user.domain.dto.SysUserDTO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.*;
import com.platform.mesh.upms.biz.modules.sys.user.exception.UserExceptionEnum;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto.SysUserRoleRelDTO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.service.ISysUserRoleRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class SysUserServiceManual  {

	private static final Logger log = LoggerFactory.getLogger(SysUserServiceManual.class);

	@Autowired
	private ISysAccountService sysAccountService;

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysUserRoleRelService sysUserRoleRelService;

	@Autowired
	private ISysMenuService sysMenuService;

	@Autowired
	private IOrgLevelService orgLevelService;

	@Autowired
	private IOrgMemberService orgMemberService;

	@Autowired
	private IOrgLevelPostRelService orgLevelPostRelService;

	@Autowired
	private IOrgMemberUserRelService orgMemberUserRelService;


	/**
	 * 功能描述:
	 * 〈添加用户时初始化账户信息〉
	 * @param user user
	 * @author 蝉鸣
	 */
	public List<SysAccount> initAccount(SysUser user,OrgLevel level) {
		//开启取消租户隔离设定
		List<SysAccount> sysAccounts = sysAccountService.lambdaQuery()
				.eq(SysAccount::getUserId, user.getUserId())
				.eq(SysAccount::getSourceFlag, SourceFlagEnum.SMS.getValue())
				.eq(SysAccount::getDelFlag, YesOrNoEnum.YES.getValue())
				.orderByDesc(SysAccount::getCreateTime)
				.list();
		if(CollUtil.isEmpty(sysAccounts)) {
			List<SysAccount> accounts = CollUtil.newArrayList();
			//初始化短信账户
			accounts.add(initAccountSms(user, level));
			//初始化密码账户
			accounts.add(initAccountSys(user, level));
			return accounts;
		}
		return sysAccounts;
	}

	/**
	 * 功能描述:
	 * 〈添加账户与当前租户关系〉
	 * @param user user
	 * @param level level
	 * @author 蝉鸣
	 */
   public SysAccount initAccountSms(SysUser user,OrgLevel level) {
	   //需要初始化,默认手机号验证码注册，无需密码
	   SysAccount sysAccount = new SysAccount();
	   sysAccount.setUserId(user.getUserId());
	   sysAccount.setAccountCode(user.getPhone());
	   sysAccount.setAccountFlag(ActiveFlagEnum.USING.getValue());
	   sysAccount.setDelFlag(YesOrNoEnum.YES.getValue());
	   sysAccount.setNickName(user.getNickName());
	   sysAccount.setSourceFlag(SourceFlagEnum.SMS.getValue());
	   sysAccount.setCreateTime(LocalDateTime.now());
	   sysAccount.setUpdateTime(LocalDateTime.now());
	   sysAccountService.save(sysAccount);
	   return sysAccount;
   }
	/**
	 * 功能描述:
	 * 〈添加账户与当前租户关系〉
	 * @param user user
	 * @param level level
	 * @author 蝉鸣
	 */
   public SysAccount initAccountSys(SysUser user,OrgLevel level) {
	   //需要初始化,默认手机号验证码注册，无需密码
	   SysAccount sysAccount = new SysAccount();
	   sysAccount.setUserId(user.getUserId());
	   sysAccount.setAccountCode(user.getPhone());
	   String encryptedPassword = SecurityUtils.encryptPassword("123456a");
	   sysAccount.setCheckCode(SecurityUtils.removePrefix(encryptedPassword));
	   sysAccount.setAccountFlag(ActiveFlagEnum.USING.getValue());
	   sysAccount.setDelFlag(YesOrNoEnum.YES.getValue());
	   sysAccount.setNickName(user.getNickName());
	   sysAccount.setSourceFlag(SourceFlagEnum.SYSTEM.getValue());
	   sysAccount.setCreateTime(LocalDateTime.now());
	   sysAccount.setUpdateTime(LocalDateTime.now());
	   sysAccountService.save(sysAccount);
	   return sysAccount;
   }

	/**
	 * 功能描述:
	 * 〈通过Code获取账户信息〉
	 * @param accountCode accountCode
	 * @param sourceFlag sourceFlag
	 * @return 正常返回:{@link SysAccount}
	 * @author 蝉鸣
	 */
	public SysAccountBO getByAccountCode(String accountCode, Integer sourceFlag) {
		return sysAccountService.getByAccountCode(accountCode,sourceFlag);
	}

	/**
	 * 功能描述:
	 * 〈通过Id获取账户信息〉
	 * @param accountId accountId
	 * @return 正常返回:{@link SysAccount}
	 * @author 蝉鸣
	 */
	public SysAccount getByAccountId(Long accountId) {
		return sysAccountService.getById(accountId);
	}

	/**
	 * 功能描述:
	 * 〈获取当前用户信息〉
	 * @param sysUser sysUser
	 * @param accountBO accountBO
	 * @return 正常返回:{@link SysAccountInfoBO}
	 * @author 蝉鸣
	 */
	public SysAccountInfoBO getLoginUserInfoBO(SysUser sysUser, SysAccountBO accountBO) {
		SysAccountInfoBO userInfoBO = new SysAccountInfoBO();
		if(ObjectUtil.isEmpty(sysUser) && ObjectUtil.isEmpty(accountBO)){
			return userInfoBO;
		}
		// 用户信息
		if(ObjectUtil.isNotEmpty(sysUser)){
			SysUserBO sysUserBO = BeanUtil.copyProperties(sysUser, SysUserBO.class);
			userInfoBO.setSysUserBO(sysUserBO);
		}
		// 账户信息
		if(ObjectUtil.isNotEmpty(accountBO)){
			userInfoBO.setAccountBO(accountBO);
		}
		//角色组
		userInfoBO.setRoleBOS(CollUtil.newArrayList());
		//菜单组
		userInfoBO.setMenuBOS(CollUtil.newArrayList());
		//组织组
		userInfoBO.setOrgBOS(CollUtil.newArrayList());
		return userInfoBO;
	}

	/**
	 * 功能描述:
	 * 〈获取当前用户信息〉
	 * @param sysUser sysUser
	 * @param account account
	 * @return 正常返回:{@link SysUserInfoVO}
	 * @author 蝉鸣
	 */
	public SysUserInfoVO getInfoVO(SysUser sysUser, SysAccount account) {
		SysUserInfoVO sysUserInfoVO = new SysUserInfoVO();
		if(ObjectUtil.isEmpty(sysUser) && ObjectUtil.isEmpty(account)){
			return sysUserInfoVO;
		}
		// 用户信息
		if(ObjectUtil.isNotEmpty(sysUser)){
            SysUserVO sysUserVO = BeanUtil.copyProperties(sysUser, SysUserVO.class);
            sysUserVO.setUserName(sysUser.getNickName());
            sysUserInfoVO.setSysUserVO(sysUserVO);
		}
		// 账户信息
		if(ObjectUtil.isNotEmpty(account)){
			AccountVO accountVO = BeanUtil.copyProperties(account, AccountVO.class);
			sysUserInfoVO.setAccountVO(accountVO);
		}
		//角色组
		List<SysRole> sysRoles = sysRoleService.getRoleInfoByUserId(sysUser.getUserId());
		sysUserInfoVO.setRoleVOS(BeanUtil.copyToList(sysRoles, SysRoleVO.class));
		//组织组
		List<SysOrgBO> sysOrgBOS = orgMemberService.getMemberInfoByUserId(sysUser.getUserId());
		sysUserInfoVO.setOrgVOS(BeanUtil.copyToList(sysOrgBOS, SysOrgVO.class));
		//成员组
		List<SysMemberVO> memberVOS = orgMemberService.getMemberVOByUserId(sysUser.getUserId());
		sysUserInfoVO.setMemberVOS(memberVOS);
		//菜单组
		//如果当前账户开启预览模式或者没有所属部门则不能进行任何操作
        // || ObjectUtil.isEmpty(account.getScopeOrgId())
        if(YesOrNoEnum.YES.getValue().equals(account.getPreviewFlag())|| CollUtil.isEmpty(sysOrgBOS)){
			sysUserInfoVO.setMenuVOS(CollUtil.newArrayList());
		}else{
			List<Integer> filterMenuTypes = CollUtil.newArrayList();
			filterMenuTypes.add(MenuTypeEnum.COMPONENT.getValue());
			List<SysMenu> sysMenus = sysMenuService.getMenuInfoByAccountId(account.getAccountId(),filterMenuTypes,CollUtil.newArrayList());
			sysUserInfoVO.setMenuVOS(BeanUtil.copyToList(sysMenus, SysMenuVO.class));
		}
		return sysUserInfoVO;
	}

    /**
     * 功能描述:
     * 〈获取用户对象〉
     * @param sysUser sysUser
     * @return 正常返回:{@link SysUserVO}
     * @author 蝉鸣
     */
    public SysUserVO getUserVO(SysUser sysUser) {
        //
        SysUserVO sysUserVO = BeanUtil.copyProperties(sysUser, SysUserVO.class);
        sysUserVO.setUserName(sysUser.getNickName());
		//获取角色
		List<SysRole> sysRoles = sysRoleService.getRoleInfoByUserId(sysUser.getUserId());
		sysUserVO.setRoleVOS(BeanUtil.copyToList(sysRoles, SysRoleVO.class));
		//岗位信息
		List<SysOrgBO> sysOrgBOS = orgMemberService.getMemberOrgByUserId(sysUser.getUserId());
		sysUserVO.setOrgVOS(BeanUtil.copyToList(sysOrgBOS, UserOrgVO.class));
        return sysUserVO;
    }

	/**
	 * 功能描述:
	 * 〈获取用户对象〉
	 * @param userVOS userVOS
	 * @author 蝉鸣
	 */
	public void packUserVO(List<SysUserVO> userVOS) {
		if(CollUtil.isEmpty(userVOS)){
			return;
		}
		for (SysUserVO userVO : userVOS) {
			//获取角色
			List<SysRole> sysRoles = sysRoleService.getRoleInfoByUserId(userVO.getUserId());
			userVO.setRoleVOS(BeanUtil.copyToList(sysRoles, SysRoleVO.class));
			//岗位信息
			List<SysOrgBO> sysOrgBOS = orgMemberService.getMemberOrgByUserId(userVO.getUserId());
			userVO.setOrgVOS(BeanUtil.copyToList(sysOrgBOS, UserOrgVO.class));
		}
	}

    /**
     * 功能描述:
     * 〈删除用户关联账户〉
     * @param userId userId
     * @author 蝉鸣
     */
    public void delAccount(Long userId) {
		sysAccountService.lambdaUpdate().eq(SysAccount::getUserId, userId).remove();
    }

	/**
	 * 功能描述:
	 * 〈删除用户关联成员〉
	 * @param userId userId
	 * @author 蝉鸣
	 */
	public void delMember(Long userId) {
		List<OrgMemberUserRel> listed = orgMemberUserRelService.lambdaQuery()
				.eq(OrgMemberUserRel::getUserId, userId).list();
		if(CollUtil.isEmpty(listed)){
			return;
		}
		List<Long> memberIds = listed.stream().map(OrgMemberUserRel::getMemberId).toList();
		//删除关系
		orgMemberUserRelService.lambdaUpdate().eq(OrgMemberUserRel::getUserId, userId).remove();
		//删除成员
		orgMemberService.removeBatchByIds(memberIds);
	}

	/**
	 * 功能描述:
	 * 〈用户关联角色〉
	 * @param userId userId
	 * @param roleIds roleIds
	 * @author 蝉鸣
	 */
	public void initUserRoleRel(Long userId, List<Long> roleIds) {
		if(CollUtil.isEmpty(roleIds)){
			return;
		}
		SysUserRoleRelDTO roleRelDTO = new SysUserRoleRelDTO();
		roleRelDTO.setUserId(userId);
		roleRelDTO.setRoleIds(roleIds);
		sysUserRoleRelService.addUserRole(roleRelDTO);
	}

	/**
	 * 功能描述:
	 * 〈获取默认部门〉
	 * @param postIds postIds
	 * @author 蝉鸣
	 */
	public OrgLevel getDefaultLevel(List<Long> postIds) {
		List<OrgLevelPostRel> postRelList = orgLevelPostRelService.lambdaQuery().in(OrgLevelPostRel::getPostId, postIds).list();
		if(CollUtil.isEmpty(postRelList)){
			return null;
		}
		Long levelId = CollUtil.getFirst(postRelList).getLevelId();
		return orgLevelService.getById(levelId);
	}

	/**
	 * 功能描述:
	 * 〈用户关联岗位〉
	 * @param userId userId
	 * @param postIds postIds
	 * @author 蝉鸣
	 */
	public void initUserPostRel(Long userId, List<Long> postIds) {
		if(CollUtil.isEmpty(postIds)) {
			return;
		}
		List<OrgLevelPostRel> postRelList = orgLevelPostRelService.lambdaQuery().in(OrgLevelPostRel::getPostId, postIds).list();
		if(CollUtil.isEmpty(postRelList)){
			return;
		}
		OrgMemberUserDTO userDTO = new OrgMemberUserDTO();
		userDTO.setUserId(userId);
		for (OrgLevelPostRel postRel : postRelList) {
			OrgMemberAddDTO orgMemberAddDTO = new OrgMemberAddDTO();
			orgMemberAddDTO.setLevelRootId(postRel.getLevelRootId());
			orgMemberAddDTO.setLevelId(postRel.getLevelId());
			orgMemberAddDTO.setPostId(postRel.getPostId());
			orgMemberAddDTO.setUserDTOS(CollUtil.newArrayList(userDTO));
			orgMemberUserRelService.addMemberUser(orgMemberAddDTO);
		}
	}

	/**
	 * 功能描述:
	 * 〈修改手机号〉
	 * @param userId userId
	 * @param phone phone
	 * @author 蝉鸣
	 */
	public void editPhone(Long userId, String phone) {
		//修改账户昵称
		sysAccountService.lambdaUpdate()
				.set(SysAccount::getAccountCode,phone)
				.eq(SysAccount::getSourceFlag, SourceFlagEnum.SMS.getValue())
				.eq(SysAccount::getUserId, userId)
				.update();
	}

	/**
	 * 功能描述:
	 * 〈用户关联岗位〉
	 * @param userId userId
	 * @param userName userName
	 * @author 蝉鸣
	 */
	public void editNickName(Long userId, String userName) {
		//修改账户昵称
		sysAccountService.lambdaUpdate()
				.set(SysAccount::getNickName,userName)
				.eq(SysAccount::getUserId, userId)
				.update();
		//修改成员昵称
		List<OrgMember> orgMembers = orgMemberService.getOrgMemberByUserIds(CollUtil.newArrayList(userId));
		if(CollUtil.isEmpty(orgMembers)){
			return;
		}
		List<String> names = orgMembers.stream().map(OrgMember::getMemberName).toList();
		List<Long> ids = orgMembers.stream().map(OrgMember::getId).toList();
		//名称相同则不修改
		if(names.contains(userName)){
			return;
		}
		orgMemberService.lambdaUpdate()
				.set(OrgMember::getMemberName, userName)
				.in(OrgMember::getId, ids)
				.update();
		//批量同步修改已存数据
//		syncUserName(userId,userName);
	}

}
