package com.platform.mesh.upms.biz.modules.sys.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.enums.ActiveFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.sys.account.domain.dto.*;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.AccountVO;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.SysAccountVO;
import com.platform.mesh.upms.biz.modules.sys.account.exception.AccountExceptionEnum;
import com.platform.mesh.upms.biz.modules.sys.account.mapper.SysAccountMapper;
import com.platform.mesh.upms.biz.modules.sys.account.service.ISysAccountService;
import com.platform.mesh.upms.biz.modules.sys.account.service.manual.SysAccountServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 用户信息
 * @author 蝉鸣
 */
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements ISysAccountService {

	private static final Logger log = LoggerFactory.getLogger(SysAccountServiceImpl.class);

	@Autowired
	private SysAccountServiceManual sysAccountServiceManual;


	/**
	 * 功能描述:
	 * 〈获取账户分页〉
	 * @param accountPageDTO accountPageDTO
	 * @return 正常返回:{@link MPage<SysAccountVO>}
	 * @author 蝉鸣
	 */
	@Override
	public PageVO<SysAccountVO> selectPage(AccountPageDTO accountPageDTO) {
		MPage<SysAccount> accountMPage = MPageUtil.pageEntityToMPage(accountPageDTO, SysAccount.class);
		accountPageDTO.setDelFlag(YesOrNoEnum.NO.getValue());
		MPage<SysAccountVO> pageVOS = this.getBaseMapper().selectMPage(accountMPage,accountPageDTO);
		return MPageUtil.convertToVO(pageVOS, SysAccountVO.class);
	}

	/**
	 * 功能描述:
	 * 〈获取当前帐户信息〉
	 * @param accountCode accountCode
	 * @param sourceFlag sourceFlag
	 * @return 正常返回:{@link SysAccount}
	 * @author 蝉鸣
	 */
	@Override
	public SysAccount getByAccountCode(String accountCode, Integer sourceFlag) {
		//开启取消数据隔离设定
		DataScopeHandler.setEnableDataScope(Boolean.FALSE);
		List<SysAccount> sysAccounts = this.lambdaQuery().eq(SysAccount::getAccountCode, accountCode).eq(SysAccount::getSourceFlag, sourceFlag).list();
		//取消数据权限隔离
		DataScopeHandler.unEnableDataScope();
		if(CollUtil.isEmpty(sysAccounts)){
			return new SysAccount();
		}
		return CollUtil.getFirst(sysAccounts);
	}

	/**
	 * 功能描述:
	 * 〈获取当前帐户信息〉
	 * @param openId openId
	 * @return 正常返回:{@link SysAccount}
	 * @author 蝉鸣
	 */
	@Override
	public SysAccountVO getByOpenId(Long openId) {
		SysAccount sysAccount = this.lambdaQuery()
				.eq(SysAccount::getAccountId, openId)
				.ne(SysAccount::getDelFlag, YesOrNoEnum.NO.getValue())
				.one();
		if(ObjectUtil.isEmpty(sysAccount)){
			//获取字段名称
			String accountCode = ObjFieldUtil.getFieldName(SysAccount::getAccountCode);
			throw AccountExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(accountCode));
		}
		SysAccountVO accountVO = BeanUtil.toBean(sysAccount, SysAccountVO.class);
		accountVO.setOpenId(openId);
		return accountVO;
	}

	/**
	 * 功能描述:
	 * 〈添加账户〉
	 * @param accountAddDTO accountAddDTO
	 * @return 正常返回:{@link AccountVO}
	 * @author 蝉鸣
	 */
	@Override
	public AccountVO addAccount(AccountAddDTO accountAddDTO) {
		SysAccount sysAccount = BeanUtil.copyProperties(accountAddDTO, SysAccount.class);
		//解密后的密码
		String decrypted = sysAccountServiceManual.decryptWebPassword(accountAddDTO.getCheckCode(), accountAddDTO.getEncryptCode());
		sysAccount.setCheckCode(decrypted);
		//检测校验码
		Boolean checkCode = sysAccountServiceManual.checkAccountCheckCode(sysAccount);
		if(!checkCode){
			throw AccountExceptionEnum.ACCOUNT_CHECK_CODE.getBaseException();
		}
		//加密校验码
		String encryptedPassword = SecurityUtils.encryptPassword(sysAccount.getCheckCode());
		sysAccount.setCheckCode(SecurityUtils.removePrefix(encryptedPassword));
		sysAccount.setDelFlag(YesOrNoEnum.YES.getValue());
		sysAccount.setCreateTime(LocalDateTime.now());
		sysAccount.setUpdateTime(LocalDateTime.now());
		//保存账户信息
		this.save(sysAccount);
		return BeanUtil.copyProperties(sysAccount, AccountVO.class);
	}

	/**
	 * 功能描述:
	 * 〈修改账户〉
	 * @param accountEditDTO accountEditDTO
	 * @return 正常返回:{@link AccountVO}
	 * @author 蝉鸣
	 */
	@Override
	public AccountVO editAccount(AccountEditDTO accountEditDTO) {
		SysAccount sysAccount = getById(accountEditDTO.getAccountId());
		if(ObjectUtil.isEmpty(sysAccount)){
			throw AccountExceptionEnum.RESULT_NO_DATA.getBaseException();
		}
		BeanUtil.copyProperties(accountEditDTO, sysAccount);
		sysAccount.setUpdateTime(LocalDateTime.now());
		this.updateById(sysAccount);
		return BeanUtil.copyProperties(sysAccount, AccountVO.class);
	}

	/**
	 * 功能描述:
	 * 〈根据OpenId删除账户〉
	 * @param changeDTO changeDTO
	 * @author 蝉鸣
	 */
	@Override
	public Boolean changeAccount(AccountChangeDTO changeDTO) {
		if(ObjectUtil.isEmpty(changeDTO.getAccountId())){
			return Boolean.FALSE;
		}
		SysAccount sysAccount = getById(changeDTO.getAccountId());
		//切换成本中心
		if(ObjectUtil.isNotEmpty(changeDTO.getScopeOrgId())){
			//获取顶层成本中心
			OrgLevel orgLevel = sysAccountServiceManual.getLevelById(changeDTO.getScopeOrgId());
			if(ObjectUtil.isNotEmpty(orgLevel)){
				sysAccount.setScopeRootId(orgLevel.getRootId());
				sysAccount.setScopeOrgId(changeDTO.getScopeOrgId());
			}
		}
		sysAccount.setUpdateTime(LocalDateTime.now());
		updateById(sysAccount);
		return Boolean.TRUE;
	}

	/**
	 * 功能描述:
	 * 〈修改密码〉
	 * @param passwordDTO passwordDTO
	 * @author 蝉鸣
	 */
	@Override
	@Transactional(rollbackFor = BaseException.class)
	public Boolean changePassword(AccountPasswordDTO passwordDTO) {
		//判断当前账户是否有效
		SysAccount sysAccount = this.getById(passwordDTO.getAccountId());
		if(ObjectUtil.isEmpty(sysAccount)){
			throw AccountExceptionEnum.RESULT_NO_DATA.getBaseException();
		}
		//查询当前密码账户
		List<SysAccount> passAccounts = this.lambdaQuery().eq(SysAccount::getUserId, sysAccount.getUserId())
				.eq(SysAccount::getSourceFlag, SourceFlagEnum.SYSTEM.getValue()).list();
		if(CollUtil.isEmpty(passAccounts)){
			throw AccountExceptionEnum.RESULT_NO_DATA.getBaseException();
		}
		SysAccount passAccount = CollUtil.getFirst(passAccounts);
		//解密后的旧密码
		String oldPassword = sysAccountServiceManual.decryptWebPassword(passwordDTO.getOldPassword(), passwordDTO.getEncryptCode());
		boolean matchesPassword = SecurityUtils.matchesPassword(oldPassword, passAccount.getCheckCode());
		if(!matchesPassword){
			throw AccountExceptionEnum.ACCOUNT_CHECK_CODE_INVALID.getBaseException();
		}
		//解密后的新密码
		String newPassword = sysAccountServiceManual.decryptWebPassword(passwordDTO.getNewPassword(), passwordDTO.getEncryptCode());
		//校验密码是否符合强度
		//加密当前密码
		String encryptedPassword = SecurityUtils.encryptPassword(newPassword);
		String removePrefix = SecurityUtils.removePrefix(encryptedPassword);
		passAccount.setCheckCode(removePrefix);
		//检测校验码
		Boolean checkCode = sysAccountServiceManual.checkAccountCheckCode(passAccount);
		if(!checkCode){
			throw AccountExceptionEnum.ACCOUNT_CHECK_CODE.getBaseException();
		}
		for (SysAccount account : passAccounts) {
			account.setCheckCode(removePrefix);
			account.setUpdateTime(LocalDateTime.now());
		}
		this.updateBatchById(passAccounts);
		return Boolean.TRUE;
	}

	/**
	 * 功能描述:
	 * 〈重置密码〉
	 * @param resetDTO resetDTO
	 * @author 蝉鸣
	 */
	@Override
	@Transactional(rollbackFor = BaseException.class)
	public Boolean resetPassword(AccountResetDTO resetDTO) {
		//判断当前账户是否有效
		SysAccount sysAccount = this.getById(resetDTO.getAccountId());
		if(ObjectUtil.isEmpty(sysAccount)){
			throw AccountExceptionEnum.RESULT_NO_DATA.getBaseException();
		}
		//查询配置,修改密码是否需要验证码校验
		Boolean checkSmsCode = sysAccountServiceManual.checkSmsCode(sysAccount,resetDTO.getPhone(),resetDTO.getSmsCode());
		if(!checkSmsCode){
			throw AccountExceptionEnum.ACCOUNT_SMS_CODE_ERROR.getBaseException();
		}
		//查询当前密码账户
		List<SysAccount> passAccounts = this.getBaseMapper().getPassAccountByPhone(resetDTO.getPhone(),SourceFlagEnum.SYSTEM.getValue());
		if(CollUtil.isEmpty(passAccounts)){
			throw AccountExceptionEnum.RESULT_NO_DATA.getBaseException();
		}
		//解密后的新密码
		String newPassword = sysAccountServiceManual.decryptWebPassword(resetDTO.getNewPassword(), resetDTO.getEncryptCode());
		SysAccount passAccount = CollUtil.getFirst(passAccounts);
		//校验密码是否符合强度
		//加密当前密码
		String encryptedPassword = SecurityUtils.encryptPassword(newPassword);
		String removePrefix = SecurityUtils.removePrefix(encryptedPassword);
		passAccount.setCheckCode(removePrefix);
		//检测校验码
		Boolean checkCode = sysAccountServiceManual.checkAccountCheckCode(passAccount);
		if(!checkCode){
			throw AccountExceptionEnum.ACCOUNT_CHECK_CODE.getBaseException();
		}
		for (SysAccount account : passAccounts) {
			account.setCheckCode(removePrefix);
			account.setUpdateTime(LocalDateTime.now());
		}
		this.updateBatchById(passAccounts);
		return Boolean.TRUE;
	}

	/**
	 * 功能描述:
	 * 〈删除当前帐户信息〉
	 * @param openId openId
	 * @author 蝉鸣
	 */
	@Override
	public void deleteByOpenId(Long openId) {
		this.lambdaUpdate()
				.set(SysAccount::getDelFlag,YesOrNoEnum.NO.getValue())
				.set(SysAccount::getUpdateTime,LocalDateTime.now())
				.eq(SysAccount::getAccountId, openId)
				.update();
	}

	/**
	 * 功能描述:
	 * 〈获取账户类型〉
	 * @author 蝉鸣
	 */
	@Override
	public JSONObject selectAccountSourceList() {
		JSONObject jsonObject = JSONUtil.createObj();
		Arrays.stream(SourceFlagEnum.values()).forEach(item->{
			jsonObject.putIfAbsent(item.getValue().toString(),item.getDesc());
		});
		return jsonObject;
	}

	/**
	 * 功能描述:
	 * 〈第三方账户登录绑定账户〉
	 * @param accountBO accountBO
	 * @return 正常返回:{@link SysAccountBO}
	 * @author 蝉鸣
	 */
	@Override
	public SysAccountBO thirdBindAccount(SysAccountBO accountBO) {
		//查询账户是否存在
		boolean exists = this.lambdaQuery()
				.eq(SysAccount::getAccountCode, accountBO.getAccountCode())
				.eq(SysAccount::getSourceFlag, accountBO.getSourceFlag()).exists();
		//如果存在
		if (exists) {
			SysAccount sysAccount = getByAccountCode(accountBO.getAccountCode(), accountBO.getSourceFlag());
			return BeanUtil.copyProperties(sysAccount, SysAccountBO.class);
		}
		//不存在则新增
		SysAccount sysAccount = BeanUtil.copyProperties(accountBO, SysAccount.class);
		//加密校验码
		if(ObjectUtil.isNotEmpty(accountBO.getCheckCode())){
			String encryptedPassword = SecurityUtils.encryptPassword(accountBO.getCheckCode());
			sysAccount.setCheckCode(encryptedPassword);
		}
		sysAccount.setAccountFlag(ActiveFlagEnum.USING.getValue());
		sysAccount.setCreateTime(LocalDateTime.now());
		sysAccount.setUpdateTime(LocalDateTime.now());
		//保存账户信息
		this.save(sysAccount);
		BeanUtil.copyProperties(sysAccount, accountBO);
		return accountBO;
	}

}
