package com.platform.mesh.crm.biz.modules.crm.suffeedback.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系市场反馈DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_suf_feedback", autoResultMap = true)
public class CrmSufFeedback extends AppPO {


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