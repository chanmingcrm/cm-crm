package com.platform.mesh.upms.biz.modules.sys.account.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.biz.modules.sys.account.domain.dto.*;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.AccountVO;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.SysAccountVO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 用户信息
 * @author 蝉鸣
 */
public interface ISysAccountService extends IService<SysAccount> {


    /**
     * 功能描述:
     * 〈分页信息〉
     * @param accountPageDTO accountPageDTO
     * @return 正常返回:{@link MPage<SysAccountVO>}
     * @author 蝉鸣
     */
    PageVO<SysAccountVO> selectPage(AccountPageDTO accountPageDTO);

    /**
     * 功能描述:
     * 〈根据账户名称获取账户〉
     * @param accountCode accountCode
     * @param sourceFlag sourceFlag
     * @return 正常返回:{@link SysAccount}
     * @author 蝉鸣
     */
    SysAccountBO getByAccountCode(String accountCode, Integer sourceFlag);

    /**
     * 功能描述:
     * 〈根据OpenId获取账户〉
     * @param openId openId
     * @return 正常返回:{@link SysAccount}
     * @author 蝉鸣
     */
    SysAccountVO getByOpenId(Long openId);

    /**
     * 功能描述:
     * 〈根据accountId获取账户〉
     * @param accountId accountId
     * @return 正常返回:{@link SysAccount}
     * @author 蝉鸣
     */
    SysAccountVO getByAccountId(Long accountId);

    /**
     * 功能描述:
     * 〈根据accountId获取账户〉
     * @param accountId accountId
     * @return 正常返回:{@link SysAccount}
     * @author 蝉鸣
     */
    SysAccountBO getBOByAccountId(Long accountId);

    /**
     * 功能描述:
     * 〈添加账户〉
     * @param accountAddDTO accountAddDTO
     * @return 正常返回:{@link AccountVO}
     * @author 蝉鸣
     */
    AccountVO addAccount(AccountAddDTO accountAddDTO);

    /**
     * 功能描述:
     * 〈修改账户〉
     * @param accountEditDTO accountEditDTO
     * @return 正常返回:{@link AccountVO}
     * @author 蝉鸣
     */
    AccountVO editAccount(AccountEditDTO accountEditDTO);

    /**
     * 功能描述:
     * 〈修改账户成本中心〉
     * @param changeDTO changeDTO
     * @author 蝉鸣
     */
    Boolean changeAccount(AccountChangeDTO changeDTO);

    /**
     * 功能描述:
     * 〈修改密码〉
     * @param passwordDTO passwordDTO
     * @author 蝉鸣
     */
    Boolean changePassword(AccountPasswordDTO passwordDTO);

    /**
     * 功能描述:
     * 〈重置密码〉
     * @param resetDTO resetDTO
     * @author 蝉鸣
     */
    Boolean resetPassword(AccountResetDTO resetDTO);

    /**
     * 功能描述:
     * 〈根据OpenId删除账户〉
     * @param openId openId
     * @author 蝉鸣
     */
    void deleteByOpenId(Long openId);

    /**
     * 功能描述:
     * 〈获取账户类型〉
     * @author 蝉鸣
     */
    JSONObject selectAccountSourceList();

    /**
     * 功能描述:
     * 〈第三方账户登录绑定账户〉
     * @param accountBO accountBO
     * @return 正常返回:{@link SysAccountBO}
     * @author 蝉鸣
     */
    SysAccountBO thirdBindAccount(SysAccountBO accountBO);

    /**
     * 功能描述:
     * 〈绑定租户授权账户〉
     * @param bindDTO bindDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean bindByTenantAuth(AccountBindDTO bindDTO);

    /**
     * 功能描述:
     * 〈查询无效组织的账户〉
     * @param userIds userIds
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    List<Long> getInvalidOrgAccountByUserIds(List<Long> userIds);

    /**
     * 功能描述:
     * 〈查询已经绑定的账户类型〉
     * @param userId userId
     * @param tenantId tenantId
     * @return 正常返回:{@link List<Integer>}
     * @author 蝉鸣
     */
    List<Integer> bindSourceType(Long userId);

    /**
     * 功能描述:
     * 〈解绑账户〉
     * @param sourceFlag sourceFlag
     * @param userId userId
     * @param tenantId tenantId
     * @author 蝉鸣
     */
    void unBindAccount(Integer sourceFlag, Long userId);

}
