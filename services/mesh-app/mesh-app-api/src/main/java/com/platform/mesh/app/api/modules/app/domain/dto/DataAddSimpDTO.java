package com.platform.mesh.app.api.modules.app.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description 添加对象DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="添加对象DTO")
public class DataAddSimpDTO extends BaseDTO {

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
     * 流程模板ID
     */
    @Schema(description = "流程模板ID")
    private Long tempProcessId;

    /**
     * 模块数据
     */
    @Schema(description = "模块数据")
    private Map<String, Object> docData;
}