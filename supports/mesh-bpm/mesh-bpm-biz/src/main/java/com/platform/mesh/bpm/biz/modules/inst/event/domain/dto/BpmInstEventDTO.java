package com.platform.mesh.bpm.biz.modules.inst.event.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 动作层级DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="动作层级DTO")
public class BpmInstEventDTO extends BaseDTO {

    /**
     * id
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 父id
     */
    @Schema(description = "父ID")
    private Long parentId;
    /**
    * 层级标识
    */
    @Schema(description = "层级标识")
    private Integer levelFlag;
    /**
    * 名称
    */
    @Schema(description = "名称")
    private String name;
}
