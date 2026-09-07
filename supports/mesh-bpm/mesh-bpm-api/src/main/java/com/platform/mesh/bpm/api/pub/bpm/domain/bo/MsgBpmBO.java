package com.platform.mesh.bpm.api.pub.bpm.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description 审批结果BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="审批结果BO")
public class MsgBpmBO extends BaseBO {

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private Long id;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private Long appId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 模块空间
     */
    @Schema(description = "模块空间")
    private String moduleSchema;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 流程类型
     */
    @Schema(description = "流程类型")
    private Integer bpmAction;

    /**
     * 实例流程ID
     */
    @Schema(description = "实例流程ID")
    private Long instProcessId;

    /**
     * 审批标识
     */
    @Schema(description = "审批标识")
    private Integer processPass;

    /**
     * 流程扩展数据
     */
    @Schema(description = "流程扩展数据")
    private Map<String,Object> extendMap;

    /**
     * 经办人ID
     */
    @Schema(description = "经办人ID")
    private Long handleUserId;

}