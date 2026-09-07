package com.platform.mesh.upms.biz.modules.doc.dir.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.biz.modules.doc.dir.enums.DocFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 文件目录DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="文件目录DTO")
public class DocDirPageDTO extends PageDTO {

    /**
     * 目录标识
     */
    @SchemaEnum(value = DocFlagEnum.class, description = "目录标识")
    private Integer dirFlag;

}