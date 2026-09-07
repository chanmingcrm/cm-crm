package com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
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
@Schema(description ="人员排班BO")
public class CcUserWorkRelBO extends BaseBO {

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
     * 人员名称
     */
    @Schema(description = "人员名称")
    private String userName;

    /**
     * 人员类型
     */
    @Schema(description = "人员类型")
    private Integer userType;

    /**
     * 智能体ID
     */
    @Schema(description = "智能体ID")
    private Long agentId;

    /**
     * 客服状态
     */
    @Schema(description = "客服状态")
    private Integer userFlag;

    /**
     * 当前接待数
     */
    @Schema(description = "当前接待数")
    private Integer activeReception;

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
     * 排班ID
     */
    @Schema(description = "排班ID")
    private Long ccWorkId;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sortNum;

    /**
     * 上次分配标识
     */
    @Schema(description = "上次分配标识")
    private Integer last;

}
