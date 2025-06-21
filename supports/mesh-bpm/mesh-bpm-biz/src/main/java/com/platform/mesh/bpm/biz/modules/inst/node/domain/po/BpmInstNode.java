package com.platform.mesh.bpm.biz.modules.inst.node.domain.po;



import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 流程节点信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_inst_node")
public class BpmInstNode extends BasePO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 流程模板ID
     */
    private Long tempProcessId;

    /**
     * 流程实例ID
     */
    private Long instProcessId;

    /**
     * 流程模板节点ID
     */
    private Long tempNodeId;

    /**
    * 节点标识
    */
    private Integer nodeFlag;

    /**
     * 节点图标
     */
    private String nodeSvg;

    /**
     * 节点主题
     */
    private String nodeTheme;

    /**
     * 运行标识
     */
    private Integer runFlag;

    /**
     * 进入节点标识
     */
    private Integer inFlag;

    /**
     * 流出节点标识
     */
    private Integer outFlag;

    /**
     * pass标识
     */
    private Integer passFlag;

    /**
     * 循环次数
     */
    private Integer loopNum;

    /**
     * 审批类型
     */
    private Integer auditFlag;

    /**
     * 审批数据类型
     */
    private Integer auditDataType;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}

