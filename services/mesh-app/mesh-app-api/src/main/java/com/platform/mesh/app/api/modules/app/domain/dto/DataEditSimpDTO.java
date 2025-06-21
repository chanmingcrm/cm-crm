package com.platform.mesh.app.api.modules.app.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 编辑对象DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="编辑对象DTO")
public class DataEditSimpDTO extends DataAddSimpDTO {

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;
}