package com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 人员排班VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="人员排班VO")
public class CcUserWorkRelVO extends BaseVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 客服人员ID
     */
    @Schema(description = "客服人员ID")
    private Long ccUserId;

    /**
     * 人员Hash
     */
    @Schema(description = "人员Hash")
    private String userHash;

    /**
     * 人员类型
     */
    @Schema(description = "人员类型")
    private Integer userType;

    /**
     * 排班ID
     */
    @Schema(description = "排班ID")
    private Long ccWorkId;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sortNum;

}
