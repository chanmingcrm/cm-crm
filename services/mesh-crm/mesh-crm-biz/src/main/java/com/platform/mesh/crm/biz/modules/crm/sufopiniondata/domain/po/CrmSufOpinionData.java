package com.platform.mesh.crm.biz.modules.crm.sufopiniondata.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系意见评价数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_suf_opinion_data", autoResultMap = true)
public class CrmSufOpinionData extends AppDataPO {

}