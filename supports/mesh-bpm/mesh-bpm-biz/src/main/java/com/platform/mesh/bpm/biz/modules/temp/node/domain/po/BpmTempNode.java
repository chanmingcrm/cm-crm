package com.platform.mesh.bpm.biz.modules.temp.node.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 流程节点信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_temp_node")
public class BpmTempNode extends BasePO {

    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 节点Hash
     */
    private String nodeHash;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 流程模板ID
     */
    private Long tempProcessId;

    /**
     * 流程模板Hash
     */
    private String tempProcessHash;

    /**
     * 节点标识
     */
    private Integer nodeFlag;

    /**
     * 节点x坐标
     */
    private Integer nodeX;

    /**
     * 节点y坐标
     */
    private Integer nodeY;

    /**
     * 节点宽度
     */
    private Integer nodeWidth;

    /**
     * 节点高度
     */
    private Integer nodeHeight;

    /**
     * 节点图标
     */
    private String nodeSvg;

    /**
     * 节点主题
     */
    private String nodeTheme;

    /**
     * 文本x坐标
     */
    private Integer textX;

    /**
     * 文本y坐标
     */
    private Integer textY;

    /**
     * 节点文本描述
     */
    private String textDesc;

    /**
     * 审批类型
     */
    private Integer auditFlag;

    /**
     * 审批数据类型
     */
    private Integer auditDataType;

    /**
     * 审批数据ID
     */
    private String auditDataIds;

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

