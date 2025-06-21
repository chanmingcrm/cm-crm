package com.platform.mesh.upms.biz.modules.dict.value.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 字典值DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="字典值DTO")
public class DictValuePageDTO extends PageDTO {

    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    private Long dictId;

    /**
     * 是否需要子字典
     */
    @Schema(description = "是否需要子字典")
    private Integer needChild;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;



}