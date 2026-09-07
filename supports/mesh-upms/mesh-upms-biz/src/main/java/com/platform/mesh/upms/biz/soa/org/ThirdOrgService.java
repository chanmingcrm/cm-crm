package com.platform.mesh.upms.biz.soa.org;


import com.platform.mesh.uaa.api.modules.tenant.domain.TenantClientBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.soa.org.domain.bo.SyncUserBO;

import java.util.List;

/**
 * @description 来源工厂
 * @author 蝉鸣
 */
public interface ThirdOrgService {

    /**
     * 功能描述:
     * 〈平台来源〉
     * @return 正常返回:{@link SourceFlagEnum}
     * @author 蝉鸣
     */
    SourceFlagEnum sourceFlag();

    /**
     * 功能描述:
     * 〈同步部门〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    List<OrgLevel> syncDeptList(TenantClientBO clientBO);

    /**
     * 功能描述:
     * 〈同步用户〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    List<SyncUserBO> syncUserList(TenantClientBO clientBO);

}
