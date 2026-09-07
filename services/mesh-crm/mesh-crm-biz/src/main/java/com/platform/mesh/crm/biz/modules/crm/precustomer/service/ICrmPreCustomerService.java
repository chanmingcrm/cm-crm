package com.platform.mesh.crm.biz.modules.crm.precustomer.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto.CheckDTO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.AbatractVO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo.CheckVO;
import com.platform.mesh.crm.biz.modules.crm.precustomer.enums.ConfirmFlagEnum;

import java.math.BigDecimal;
import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 客户关系客户对象信息
 * @author 蝉鸣
 */
public interface ICrmPreCustomerService extends IAppService<CrmPreCustomer> {

    /**
     * 功能描述:
     * 〈查重客户关系客户对象〉
     * @param checkDTO checkDTO
     * @author 蝉鸣
     */
    List<CheckVO> checkPreCustomer(CheckDTO checkDTO);

    /**
     * 功能描述:
     * 〈摘要〉
     * @param customerId customerId
     * @return 正常返回:{@link AbatractVO}
     * @author 蝉鸣
     */
    AbatractVO abstractPreCustomer(Long customerId);

    /**
     * 功能描述:
     * 〈修改客户成交状态〉
     * @param customerId customerId
     * @param confirmFlag confirmFlag
     * @param totalMoney totalMoney
     * @author 蝉鸣
     */
    void updateConfirmFlag(Long customerId, ConfirmFlagEnum confirmFlag, BigDecimal totalMoney);

    /**
     * 功能描述:
     * 〈更新客户金额〉
     * @param customerId customerId
     * @param customerMoney customerMoney
     * @author 蝉鸣
     */
    void updateReceivedMoney(Long customerId, BigDecimal customerMoney);
}