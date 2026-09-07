package com.platform.mesh.upms.biz.modules.doc.online.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.biz.modules.doc.dir.enums.DocFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 在线文档DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="在线文档DTO")
public class DocOnlineLastDTO extends BaseDTO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 上/下标识
     */
    @Schema(description = "上/下标识")
    private Integer lastFlag;

    /**
     * 文档类型
     */
    @SchemaEnum(value = DocFlagEnum.class, description = "文档标识")
    private Integer docFlag;
}