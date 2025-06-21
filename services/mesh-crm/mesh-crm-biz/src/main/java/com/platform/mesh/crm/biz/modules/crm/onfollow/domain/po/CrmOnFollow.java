package com.platform.mesh.crm.biz.modules.crm.onfollow.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系跟进拜访DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_on_follow", autoResultMap = true)
public class CrmOnFollow extends AppPO {


    /**
     * 关联模块ID
     */
    private Long relModuleId;

    /**
     * 关联数据ID
     */
    private Long relDataId;

}