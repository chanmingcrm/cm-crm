package com.platform.mesh.bpm.biz.modules.hist.node.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 流程节点信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息DTO")
public class BpmHistNodeDTO extends BaseDTO {

    /**
     * id
     */
    @Schema(description = "ID")
    private Long id;
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
