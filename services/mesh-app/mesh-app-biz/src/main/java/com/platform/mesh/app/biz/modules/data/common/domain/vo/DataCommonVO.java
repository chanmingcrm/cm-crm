package com.platform.mesh.app.biz.modules.data.common.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段数据VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段数据VO")
public class DataCommonVO extends AppVO {

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;


    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
    private Long columnId;


    /**
     * 组件类型
     */
    @Schema(description = "组件类型")
    private Integer compType;


    /**
     * 组件标识
     */
    @Schema(description = "组件标识")
    private String compMac;


    /**
     * 字段标识
     */
    @Schema(description = "字段标识")
    private String columnMac;


    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnName;
}