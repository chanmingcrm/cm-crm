package com.platform.mesh.app.biz.modules.app.formcolumn.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description 订阅同步数据BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="订阅同步数据BO")
public class ModuleFieldBO extends BaseBO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 模块Schema
     */
    @Schema(description = "模块Schema")
    private String moduleSchema;

    /**
     * 模块索引
     */
    @Schema(description = "模块索引")
    private String moduleIndex;

    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
    private Long columnId;

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