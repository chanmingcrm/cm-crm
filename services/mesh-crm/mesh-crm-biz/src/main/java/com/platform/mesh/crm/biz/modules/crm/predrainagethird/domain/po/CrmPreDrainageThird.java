package com.platform.mesh.crm.biz.modules.crm.predrainagethird.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 客户关系活动引流数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "crm_pre_drainage_third", autoResultMap = true)
public class CrmPreDrainageThird extends BasePO {

    /**
     * 主键ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 数据来源
     */
    private Integer sourceFlag;

    /**
     * 模块ID
     */
    private Long moduleId;

    /**
     * 数据ID
     */
    private Long dataId;

    /**
     * 第三方ID
     */
    private String thirdId;

    /**
     * 第三方数据
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object thirdData;

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

}
