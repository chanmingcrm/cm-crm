package com.platform.mesh.upms.biz.modules.sys.user.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.api.modules.sys.user.enums.ActiveFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.AccountVO;
import com.platform.mesh.upms.biz.modules.sys.account.service.ISysAccountService;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.po.SysMenu;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuVO;
import com.platform.mesh.upms.biz.modules.sys.menu.service.ISysMenuService;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;
import com.platform.mesh.upms.biz.modules.sys.role.service.ISysRoleService;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysMemberVO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysOrgVO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysUserInfoVO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
	private ISysMenuService sysMenuService;

	@Autowired
	private IOrgLevelService orgLevelService;

	@Autowired
	private IOrgMemberService orgMemberService;


	/**
	 * 功能描述:
	 * 〈添加用户时初始化账户信息〉
	 * @param user user
	 * @author 蝉鸣
	 */
	public SysAccount initAccount(SysUser user) {
		List<SysAccount> sysAccounts = sysAccountService.lambdaQuery()
				.eq(SysAccount::getUserId, user.getUserId())
				.eq(SysAccount::getSourceFlag, SourceFlagEnum.SMS.getValue())
				.orderByAsc(SysAccount::getCreateTime)
				.list();
		SysAccount sysAccount;
		if(CollUtil.isEmpty(sysAccounts)) {
			//需要初始化,默认手机号验证码注册，无需密码
			sysAccount = new SysAccount();
			sysAccount.setUserId(user.getUserId());
			sysAccount.setAccountCode(user.getPhone());
			sysAccount.setAccountFlag(ActiveFlagEnum.USING.getValue());
			sysAccount.setDelFlag(YesOrNoEnum.YES.getValue());
			sysAccount.setNickName(user.getPhone());
			sysAccount.setSourceFlag(SourceFlagEnum.SMS.getValue());
			sysAccount.setCreateTime(LocalDateTime.now());
			sysAccount.setUpdateTime(LocalDateTime.now());
			sysAccountService.save(sysAccount);
		}else{
			sysAccount = CollUtil.getFirst(sysAccounts);
		}
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
	public SysAccount getByAccountCode(String accountCode, Integer sourceFlag) {
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
	 * @param account account
	 * @return 正常返回:{@link SysAccountInfoBO}
	 * @author 蝉鸣
	 */
	public SysAccountInfoBO getLoginUserInfoBO(SysUser sysUser, SysAccount account) {
		SysAccountInfoBO userInfoBO = new SysAccountInfoBO();
		if(ObjectUtil.isEmpty(sysUser) && ObjectUtil.isEmpty(account)){
			return userInfoBO;
		}
		// 用户信息
		if(ObjectUtil.isNotEmpty(sysUser)){
			SysUserBO sysUserBO = BeanUtil.copyProperties(sysUser, SysUserBO.class);
			userInfoBO.setSysUserBO(sysUserBO);
		}
		// 账户信息
		if(ObjectUtil.isNotEmpty(account)){
			userInfoBO.setAccountBO(BeanUtil.copyProperties(account, SysAccountBO.class));
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
			sysUserInfoVO.setSysUserVO(BeanUtil.copyProperties(sysUser, SysUserVO.class));
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
		if(YesOrNoEnum.YES.getValue().equals(account.getPreviewFlag()) || ObjectUtil.isEmpty(account.getScopeOrgId()) || CollUtil.isEmpty(sysOrgBOS)){
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
	 * 〈封装列表用户信息〉
	 * @param userPage userPage
	 * @return 正常返回:{@link MPage<SysUserVO>}
	 * @author 蝉鸣
	 */
	public PageVO<SysUserVO> packUserPackVO(MPage<SysUser> userPage) {
		if(CollUtil.isEmpty(userPage.getRecords())){
			return new PageVO<>();
		}
		//
        return MPageUtil.convertToVO(userPage, SysUserVO.class);
	}

}
