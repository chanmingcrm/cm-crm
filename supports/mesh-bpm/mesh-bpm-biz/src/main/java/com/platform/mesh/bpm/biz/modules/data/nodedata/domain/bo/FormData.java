package com.platform.mesh.bpm.biz.modules.data.nodedata.domain.bo;

import com.platform.mesh.core.application.domain.po.BasePO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 表单数据对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "表单数据对象")
public class FormData extends BasePO {


    /**
    * 字段名称
    */
    @Schema(description = "字段名称")
    private String columnName;

    /**
    * 字段标识
    */
    @Schema(description = "字段标识")
    private String columnMac;

    /**
    * 字段数据
    */
    @Schema(description = "字段数据")
    private String columnData;

}