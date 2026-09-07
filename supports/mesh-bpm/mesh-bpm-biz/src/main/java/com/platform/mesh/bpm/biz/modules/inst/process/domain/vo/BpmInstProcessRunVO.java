package com.platform.mesh.bpm.biz.modules.inst.process.domain.vo;


import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 流程过程信息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程过程信息VO")
public class BpmInstProcessRunVO extends BaseVO {

    /**
     * 流程Hash
     */
    @Schema(description = "流程实例Id")
    private Long instProcessId;

    /**
     * 流程实例父ID
     */
    @Schema(description = "流程实例父ID")
    private Long parentProcessId;

    /**
     * 运行标识
     */
    @SchemaEnum(value = ProcessRunEnum.class, description = "运行标识")
    private Integer runFlag;

    /**
     * 运行中的节点
     */
    @Schema(description = "运行中的节点")
    private List<Long> runNodeIds;

    /**
     * 运行中的节点审批权限信息
     */
    @Schema(description = "运行中的节点审批权限信息")
    private List<BpmInstProcessAuditVO> auditNodes;
}
