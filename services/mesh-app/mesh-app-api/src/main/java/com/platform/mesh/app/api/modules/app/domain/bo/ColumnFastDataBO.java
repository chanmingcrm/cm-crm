package com.platform.mesh.app.api.modules.app.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段关联VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="表单字段关联BO")
public class ColumnFastDataBO {

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnName;

    /**
     * Es类型
     */
    @Schema(description = "Es类型")
    private String esKind;

    /**
     * Es类型
     */
    @Schema(description = "字段值")
    private Object columnValue;




}