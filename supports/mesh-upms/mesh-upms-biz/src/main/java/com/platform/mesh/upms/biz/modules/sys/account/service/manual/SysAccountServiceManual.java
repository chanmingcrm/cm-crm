package com.platform.mesh.upms.biz.modules.sys.account.service.manual;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.symmetric.AES;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.SmsFlagEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.po.ConfSysSafe;
import com.platform.mesh.upms.biz.modules.conf.syssafe.service.IConfSysSafeService;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.account.exception.AccountExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class SysAccountServiceManual {

	private static final Logger log = LoggerFactory.getLogger(SysAccountServiceManual.class);


	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private IOrgLevelService orgLevelService;

	@Autowired
	private IConfSysSafeService confSysSafeService;

	/**
	 * 功能描述:
	 * 〈获取系统安全配置〉
	 * @param scopeRootId scopeRootId
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	public List<ConfSysSafe> getConfSysSafe(Long scopeRootId){
		List<ConfSysSafe> sysSafes = confSysSafeService.lambdaQuery()
				.eq(ConfSysSafe::getScopeOrgId,scopeRootId)
				.orderByDesc(ConfSysSafe::getCreateTime).list();
		if(CollUtil.isEmpty(sysSafes)){
			return CollUtil.newArrayList();
		}
		return sysSafes;
	}

	/**
	 * 功能描述:
	 * 〈校验校验码〉
	 * @param sysAccount sysAccount
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	public Boolean checkSmsCode(SysAccount sysAccount,String phone,String smsCode){
		List<ConfSysSafe> sysSafes = this.getConfSysSafe(sysAccount.getScopeRootId());
		if(CollUtil.isEmpty(sysSafes)){
			return Boolean.TRUE;
		}
		ConfSysSafe sysSafe = CollUtil.getFirst(sysSafes);
		//无安全规则限制 获取 非密码登录
		if(ObjectUtil.isNull(sysSafe) || !YesOrNoEnum.YES.getValue().equals(sysSafe.getPassResetSms())){
			return  Boolean.TRUE;
		}
		//校验验证码是否有效
		//验证码缓存KEY
		String phoneKey = CacheConstants.SMS_PHONE_CACHE.concat(SymbolConst.COLON).concat(phone).concat(SymbolConst.COLON).concat(SmsFlagEnum.PASSWORD.getDesc());
		//验证码是否过期
		if(!RedissonUtil.hasKey(phoneKey)){
			throw AccountExceptionEnum.ACCOUNT_SMS_CODE_INVALID.getBaseException();
		}
		//获取验证码
		Object cacheObject = RedissonUtil.getCacheObject(phoneKey);
		if(smsCode.equals(cacheObject.toString())){
			return  Boolean.TRUE;
		}else{
			throw AccountExceptionEnum.ACCOUNT_SMS_CODE_ERROR.getBaseException();
		}
    }
	/**
	 * 功能描述:
	 * 〈校验校验码〉
	 * @param sysAccount sysAccount
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	public Boolean checkAccountCheckCode(SysAccount sysAccount){
		List<ConfSysSafe> sysSafes = this.getConfSysSafe(sysAccount.getScopeRootId());
		if(CollUtil.isEmpty(sysSafes)){
			return Boolean.TRUE;
		}
		ConfSysSafe sysSafe = CollUtil.getFirst(sysSafes);
		//无安全规则限制 获取 非密码登录
		if(ObjectUtil.isNull(sysSafe) || !SourceFlagEnum.SYSTEM.getValue().equals(sysAccount.getSourceFlag())){
			return Boolean.TRUE;
		}
		String checkCode = sysAccount.getCheckCode();
		//强制:密码长度校验
		if(checkCode.length() < sysSafe.getPassLength()){
			return Boolean.FALSE;
		}
		//强制:密码数字校验
		boolean numCheck = checkCode.replaceAll(SymbolConst.PATTERN_NUM, SymbolConst.BLANK).length() < sysSafe.getPassNum();
		if(numCheck){
			return Boolean.FALSE;
		}
		//强制:密码小写字母校验
		boolean lowCheck = checkCode.replaceAll(SymbolConst.PATTERN_L_CHAR, SymbolConst.BLANK).length() < sysSafe.getPassLChar();
		if(lowCheck){
			return Boolean.FALSE;
		}
		//强制:密码大写字母校验
		boolean upCheck = checkCode.replaceAll(SymbolConst.PATTERN_U_CHAR, SymbolConst.BLANK).length() < sysSafe.getPassUChar();
		if(upCheck){
			return Boolean.FALSE;
		}
		//强制:密码特殊字母校验
		boolean speCheck = checkCode.replaceAll(SymbolConst.PATTERN_S_CHAR, SymbolConst.BLANK).length() < sysSafe.getPassSChar();
		if(speCheck){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}


	/**
	 * 功能描述:
	 * 〈获取组织ID〉
	 * @param levelId levelId
	 * @return 正常返回:{@link OrgLevel}
	 * @author 蝉鸣
	 */
    public OrgLevel getLevelById(Long levelId) {
		return orgLevelService.getById(levelId);
    }

	/**
	 * 功能描述:
	 * 〈校验校验码〉
	 * @param password password
	 * @param encryptCode encryptCode
	 * @return 正常返回:{@link Boolean}
	 * @author 蝉鸣
	 */
	public String decryptWebPassword(String password, String encryptCode) {
		//base64解密
		String decodeStr = Base64.decodeStr(encryptCode);
		//判断校验码格式是否正确
		if(!decodeStr.startsWith(StrConst.BEARER)){
			throw AccountExceptionEnum.RESULT_NO_DATA.getBaseException();
		}
		String replaced = decodeStr.replace(StrConst.BEARER, SymbolConst.BLANK);
		byte[] bytes = replaced.getBytes();
		if (bytes.length < NumberConst.NUM_16) {
			throw AccountExceptionEnum.ACCOUNT_CHECK_CODE.getBaseException();
		}
		byte[] iv = Arrays.copyOfRange(bytes, NumberConst.NUM_0, NumberConst.NUM_16); // 前 16 字节是 IV
		byte[] key = Arrays.copyOfRange(bytes, NumberConst.NUM_16, bytes.length);
		//AES解密密码
		AES aes = new AES(Mode.CBC.name(), "PKCS7Padding", key, iv);
		String encryptBase64 = aes.encryptBase64("123456");
		String decryptStr = aes.decryptStr(encryptBase64);

		return aes.decryptStr(password);
	}
}
