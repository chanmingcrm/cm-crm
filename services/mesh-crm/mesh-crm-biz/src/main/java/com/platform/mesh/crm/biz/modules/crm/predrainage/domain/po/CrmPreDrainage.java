package com.platform.mesh.crm.biz.modules.crm.predrainage.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系活动引流DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_pre_drainage", autoResultMap = true)
public class CrmPreDrainage extends AppPO {

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 最新的流程实例ID
     */
    private Long instProcessId;

    /**
     * 最新的流程实例审批状态
     */
    private Integer processPass;

}