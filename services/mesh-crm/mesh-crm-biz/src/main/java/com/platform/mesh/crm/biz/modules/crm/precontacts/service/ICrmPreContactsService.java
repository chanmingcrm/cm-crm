package com.platform.mesh.crm.biz.modules.crm.precontacts.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.crm.precontacts.domain.po.CrmPreContacts;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系联系人信息
 * @author 蝉鸣
 */
public interface ICrmPreContactsService extends IAppService<CrmPreContacts> {

    /**
     * 功能描述:
     * 〈查重客户关系联系人〉
     * @param checkDTO checkDTO
     * @author 蝉鸣
     */
    List<CheckVO> checkPreContacts(CheckDTO checkDTO);
}