package com.platform.mesh.crm.biz.modules.crm.precontacts.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 联系人对象DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_pre_contacts", autoResultMap = true)
public class CrmPreContacts extends AppPO {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 客户ID
     */
    private Long customerId;

}