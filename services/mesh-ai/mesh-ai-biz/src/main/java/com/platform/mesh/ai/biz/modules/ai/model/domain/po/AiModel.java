package com.platform.mesh.ai.biz.modules.ai.model.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description AI模型PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_model", autoResultMap = true)
public class AiModel extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 模型名称
    */
    private String modelName;

    /**
    * 模型标识
    */
    private Integer modelFlag;

    /**
    * 模型类别
    */
    private Integer modelType;

    /**
    * 模型描述
    */
    private String modelDesc;

    /**
    * 模型基础地址
    */
    private String baseUrl;

    /**
    * 模型密钥
    */
    private String apiKey;

    /**
    * 随机性
    */
    private BigDecimal temperature;

    /**
    * 最大token数
    */
    private Integer maxTokens;

    /**
    * 核采样阈值
    */
    private BigDecimal topP;

    /**
    * 惩罚重复出现的
    */
    private BigDecimal frequencyPenalty;

    /**
    * 惩罚新出现的
    */
    private BigDecimal presencePenalty;

    /**
    * 设置停止词
    */
    private String stopKey;

    /**
    * 是否启用流式响应
    */
    private Integer withStream;

    /**
    * 删除标识
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