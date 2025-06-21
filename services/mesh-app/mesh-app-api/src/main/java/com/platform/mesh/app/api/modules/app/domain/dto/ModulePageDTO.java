package com.platform.mesh.app.api.modules.app.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 模块对象分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块对象分页DTO")
public class ModulePageDTO extends PageDTO {


    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private Long appId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private List<Long> moduleIds;

    /**
     * 模块存储
     */
    @Schema(description = "模块存储")
    private List<String> moduleSchemas;

}
