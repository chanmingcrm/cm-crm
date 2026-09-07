package com.platform.mesh.ai.biz.modules.cc.user.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 客服会话VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服会话VO")
public class CcUserVO extends BaseVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 人员类型
     */
    @Schema(description = "人员类型")
    private Integer userType;

    /**
     * 人员Hash
     */
    @Schema(description = "人员Hash")
    private String userHash;

    /**
     * 人员名称
     */
    @Schema(description = "人员名称")
    private String userName;

    /**
     * 客服状态
     */
    @Schema(description = "客服状态")
    private Integer userFlag;

    /**
     * 最大接待数
     */
    @Schema(description = "最大接待数")
    private Integer maxReception;

    /**
     * 技能组
     */
    @Schema(description = "技能组")
    private String skillGroup;

    /**
     * 智能体ID
     */
    @Schema(description = "智能体ID")
    private Long agentId;
}
