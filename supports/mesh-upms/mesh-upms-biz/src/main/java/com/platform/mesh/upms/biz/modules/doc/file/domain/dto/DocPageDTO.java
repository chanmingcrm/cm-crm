package com.platform.mesh.upms.biz.modules.doc.file.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 文件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="文件DTO")
public class DocPageDTO extends PageDTO {

    /**
     * 文件夹ID
     */
    @Schema(description = "文件夹ID")
    private Long dirId;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;


}