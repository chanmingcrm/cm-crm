package com.platform.mesh.tmp.biz.modules.appr.approval.domain.po;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description OA办公审批
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "oa_approval", autoResultMap = true)
public class OaApproval extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 模块ID
    */
    private Long parentModuleId;

    /**
    * 模块ID
    */
    private Long moduleId;

    /**
    * 商机类型BusinessTypeEnum
    */
    private Integer dataType;

    /**
    * 商机标识
    */
    private String dataMac;

    /**
    * 商机名称
    */
    private String dataName;

    /**
    * 商机描述
    */
    private String dataDesc;

    /**
     * 客户序列号
     */
    private Integer dataSerial;

    /**
    * 商机期数
    */
    private Integer dataPeriod;

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