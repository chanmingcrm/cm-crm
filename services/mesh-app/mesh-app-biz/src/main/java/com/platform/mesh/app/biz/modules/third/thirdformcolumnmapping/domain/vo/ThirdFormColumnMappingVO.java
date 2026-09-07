package com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化字段映射设置VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化字段映射设置VO")
public class ThirdFormColumnMappingVO extends BaseVO {


    /**
     * 字段来源
     */
    @Schema(description = "字段来源")
    private Integer sourceFlag;


    /**
     * 第三方字段标识
     */
    @Schema(description = "第三方字段标识")
    private String relColumnMac;


    /**
     * 第三方字段名称
     */
    @Schema(description = "第三方字段名称")
    private String relColumnName;


    /**
     * 模块Id
     */
    @Schema(description = "模块Id")
    private Long moduleId;


    /**
     * 表单Id
     */
    @Schema(description = "表单Id")
    private Long formId;


    /**
     * 系统字段ID
     */
    @Schema(description = "系统字段ID")
    private Long columnId;


    /**
     * 系统字段标识
     */
    @Schema(description = "系统字段标识")
    private String columnMac;


    /**
     * 系统字段名称
     */
    @Schema(description = "系统字段名称")
    private String columnName;

}