package com.platform.mesh.upms.biz.modules.doc.online.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
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
public class DocOnlinePageDTO extends PageDTO {

    /**
     * 文档目录Id
     */
    @Schema(description = "文档目录Id")
    private Long docDirId;

    /**
     * 文档类型
     */
    @SchemaEnum(value = DocFlagEnum.class, description = "文档标识")
    private Integer docFlag;

    /**
     * 文档标题
     */
    @Schema(description = "文档标题")
    private String docTitle;

    /**
     * 文档关键字
     */
    @Schema(description = "文档关键字")
    private String docKeyword;
    /**
     * 是否需要内容
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否需要内容")
    private Integer needContent;

}