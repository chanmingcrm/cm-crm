package com.platform.mesh.crm.biz.modules.crm.onsubproductdata.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系关联子产品数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_on_sub_product_data", autoResultMap = true)
public class CrmOnSubProductData extends AppDataPO {

    /**
     * 关联模块ID
     */
    private Long relModuleId;

    /**
     * 关联数据ID
     */
    private Long relDataId;

}