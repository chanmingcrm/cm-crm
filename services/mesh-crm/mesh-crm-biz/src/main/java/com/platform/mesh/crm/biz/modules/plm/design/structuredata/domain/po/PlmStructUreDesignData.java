package com.platform.mesh.crm.biz.modules.plm.design.structuredata.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 结构设计数据
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "plm_design_struct_data", autoResultMap = true)
public class PlmStructUreDesignData extends AppDataPO {

}
