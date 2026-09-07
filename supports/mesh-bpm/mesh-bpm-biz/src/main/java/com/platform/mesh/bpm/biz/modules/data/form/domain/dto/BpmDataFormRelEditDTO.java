package com.platform.mesh.bpm.biz.modules.data.form.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 数据流程表单DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="数据流程表单修改DTO")
public class BpmDataFormRelEditDTO extends BpmDataFormRelAddDTO {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;
}
