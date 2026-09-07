package com.platform.mesh.crm.biz.modules.plm.product.categorydata.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 产品大类数据
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "plm_category_data", autoResultMap = true)
public class PlmProductCategoryData extends AppDataPO {

}
