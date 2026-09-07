package com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 第三方字段
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "third_form_column", autoResultMap = true)
public class ThirdFormColumn extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 字段来源
    */
    private Integer sourceFlag;


    /**
    * 来源组件标识
    */
    private String compMac;


    /**
    * 来源字段标识
    */
    private String columnMac;


    /**
    * 来源字段名称
    */
    private String columnName;


    /**
    * 来源字段选项
    */
    private Object columnOption;


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

}
