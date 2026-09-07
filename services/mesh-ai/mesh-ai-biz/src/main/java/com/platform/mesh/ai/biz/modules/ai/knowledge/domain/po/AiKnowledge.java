package com.platform.mesh.ai.biz.modules.ai.knowledge.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description AI知识库PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_knowledge", autoResultMap = true)
public class AiKnowledge extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 模型ID
    */
    private Long modelId;

    /**
     * 向量库存储类型
     */
    private Integer storeFlag;

    /**
    * 知识库名称
    */
    private String knowledgeName;

    /**
    * 欢迎语
    */
    private String knowledgeWelcome;

    /**
    * 描述
    */
    private String knowledgeDesc;

    /**
    * 知识库排序
    */
    private Integer knowledgeSort;

    /**
    * 是否公开知识库（1 是 2否）
    */
    private Integer knowledgeShare;

    /**
    * 是否删除 1：正常，2：删除
    */
    private Integer delFlag;

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