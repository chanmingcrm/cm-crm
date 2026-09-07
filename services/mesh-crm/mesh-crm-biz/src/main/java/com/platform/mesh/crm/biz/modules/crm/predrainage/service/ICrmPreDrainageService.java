package com.platform.mesh.crm.biz.modules.crm.predrainage.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.dto.CrmPreDrainageGetDTO;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.po.CrmPreDrainage;
import com.platform.mesh.upms.api.modules.conf.domian.bo.ConfSysSetBO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系活动引流信息
 * @author 蝉鸣
 */
public interface ICrmPreDrainageService extends IAppService<CrmPreDrainage> {

    /**
     * 功能描述:
     * 〈查重客户关系活动引流〉
     * @param checkDTO checkDTO
     * @author 蝉鸣
     */
    List<CheckVO> checkPreDrainage(CheckDTO checkDTO);

    /**
     * 功能描述:
     * 〈同步抖音线索〉
     * @author 蝉鸣
     */
    void syncDouYinClue(ConfSysSetBO sysSetBO);

    /**
     * 功能描述:
     * 〈同步企微线索〉
     * @author 蝉鸣
     */
    void syncWxWorkContact(ConfSysSetBO sysSetBO);

    /**
     * 功能描述:
     * 〈无授权新增〉
     * @author 蝉鸣
     */
    void addDataNoScope(CrmPreDrainage appPO);

    /**
     * 功能描述:
     * 〈根据第三方ID查询线索/客户信息〉
     * @author 蝉鸣
     */
    Object getByThirdId(CrmPreDrainageGetDTO getDTO);

}