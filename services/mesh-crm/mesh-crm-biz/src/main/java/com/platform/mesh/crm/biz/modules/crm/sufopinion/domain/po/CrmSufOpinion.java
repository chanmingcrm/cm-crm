package com.platform.mesh.crm.biz.modules.crm.sufopinion.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系意见评价DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_suf_opinion", autoResultMap = true)
public class CrmSufOpinion extends AppPO {


    /**
     * 客户ID
     */
    private Long customerId;


    /**
     * 合同ID
     */
    private Long contractId;


    /**
     * 订单ID
     */
    private Long orderId;

}