package com.platform.mesh.crm.biz.modules.crm.onprogramme.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系方案输出DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_on_programme", autoResultMap = true)
public class CrmOnProgramme extends AppPO {

    /**
     * 客户ID
     */
    private Long customerId;

}