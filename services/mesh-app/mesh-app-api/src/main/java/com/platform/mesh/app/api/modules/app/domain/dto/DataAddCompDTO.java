package com.platform.mesh.app.api.modules.app.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 添加对象DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="添加对象DTO")
public class DataAddCompDTO extends BaseDTO {

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 模块索引
     */
    @Schema(description = "模块索引")
    private String moduleIndex;

    /**
     * 父模块ID
     */
    @Schema(description = "父模块ID")
    private Long parentModuleId;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     * 模块数据
     */
    @Schema(description = "模块数据")
    private List<AppFormColumnAddDTO> docDataList;
}