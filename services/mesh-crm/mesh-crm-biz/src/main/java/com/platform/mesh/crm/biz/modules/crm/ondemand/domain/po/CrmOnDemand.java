package com.platform.mesh.crm.biz.modules.crm.ondemand.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系需求整理DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_on_demand", autoResultMap = true)
public class CrmOnDemand extends AppPO {

    /**
     * 客户ID
     */
    private Long customerId;

}