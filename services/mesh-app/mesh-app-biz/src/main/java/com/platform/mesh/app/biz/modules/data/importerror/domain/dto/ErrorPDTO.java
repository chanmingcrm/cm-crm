package com.platform.mesh.app.biz.modules.data.importerror.domain.dto;

import com.platform.mesh.core.application.domain.dto.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 导入错误DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="导入错误DTO")
public class ErrorPDTO extends QueryDTO {

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 当前查询批次参数
     */
    @Schema(description = "当前查询批次参数")
    private Long batchId;

}
