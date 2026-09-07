package com.platform.mesh.app.biz.modules.app.modulebase.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 关联查询对象DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="关联查询对象DTO")
public class AppModuleRelPageDTO extends PageDTO {


    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private Long appId;


    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

}
