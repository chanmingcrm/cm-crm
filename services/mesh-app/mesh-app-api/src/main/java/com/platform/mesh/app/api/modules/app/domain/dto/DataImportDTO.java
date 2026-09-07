package com.platform.mesh.app.api.modules.app.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description 应用导入DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="应用导入DTO")
public class DataImportDTO extends BaseDTO {
    
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long moduleId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long formId;

    /**
     * 重复数据处理
     */
    @Schema(description = "重复数据处理")
    private Integer skipOrOver = YesOrNoEnum.YES.getValue();

    /**
     * 数据类型
     */
    @Schema(description = "数据类型")
    private MultipartFile file;

}