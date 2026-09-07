package com.platform.mesh.app.api.modules.init.db.service;

import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransResBO;
import com.platform.mesh.app.api.modules.init.db.domain.dto.DbTransDTO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberTransBO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description Db服务
 * @author 蝉鸣
 */
public interface IDbService {

    /**
     * 功能描述:
     * 〈获取自定义模块数据库表名称〉
     * @return 正常返回:{@link List<String>}
     * @author 蝉鸣
     */
    List<String> initEsDbTables();

    /**
     * 功能描述:
     * 〈转换数据〉
     * @author 蝉鸣
     */
    void transDbData();

    /**
     * 功能描述:
     * 〈转换数据〉
     * @author 蝉鸣
     */
    DbTransResBO transDbData(DbTransDTO transDTO);

    /**
     * 功能描述:
     * 〈同步人员名称〉
     * @param memberBO memberBO
     * @author 蝉鸣
     */
    void syncUserName(OrgMemberBO memberBO);

    /**
     * 功能描述:
     * 〈同步组织名称〉
     * @param levelBO levelBO
     * @author 蝉鸣
     */
    void syncOrgName(OrgLevelBO levelBO);

    /**
     * 功能描述:
     * 〈转移组织数据处理〉
     * @param transBO transBO
     * @author 蝉鸣
     */
    void transOrgData(OrgMemberTransBO transBO);

}
