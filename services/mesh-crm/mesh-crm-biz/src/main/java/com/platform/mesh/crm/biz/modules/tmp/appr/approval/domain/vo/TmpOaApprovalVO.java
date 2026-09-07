package com.platform.mesh.crm.biz.modules.tmp.appr.approval.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 客户关系商机跟进VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="OA办公审批VO")
public class TmpOaApprovalVO extends AppVO {


    /**
     * 父模块ID
     */
    @Schema(description = "父模块ID")
    private Long parentModuleId;

}