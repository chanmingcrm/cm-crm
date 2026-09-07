package com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段排序DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="表单字段排序DTO")
public class AppFormColumnSortingDTO extends BaseDTO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     * 父字段ID
     */
    @Schema(description = "父字段ID")
    private Long parentColumnId;

    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
    private Long columnId;

    /**
     * 批次ID
     */
    @Schema(description = "批次ID")
    private Long batchId;

    /**
     * 横坐标
     */
    @Schema(description = "横坐标")
    private Integer xAddr;

    /**
     * 纵坐标
     */
    @Schema(description = "纵坐标")
    private Integer yAddr;


}