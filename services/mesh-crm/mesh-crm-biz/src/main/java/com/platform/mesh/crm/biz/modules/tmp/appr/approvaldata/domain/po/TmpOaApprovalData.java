package com.platform.mesh.crm.biz.modules.tmp.appr.approvaldata.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description OA办公审批数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "tmp_oa_approval_data", autoResultMap = true)
public class TmpOaApprovalData extends AppDataPO {

}