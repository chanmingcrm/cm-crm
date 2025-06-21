package com.platform.mesh.app.biz.modules.data.common.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 通用数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "data_common_data", autoResultMap = true)
public class DataCommon extends AppPO {


    /**
    * 表单ID
    */
    private Long formId;


    /**
    * 字段ID
    */
    private Long columnId;


    /**
    * 组件类型
    */
    private Integer compType;


    /**
    * 组件标识
    */
    private String compMac;


    /**
    * 字段标识
    */
    private String columnMac;


    /**
    * 字段名称
    */
    private String columnName;

}