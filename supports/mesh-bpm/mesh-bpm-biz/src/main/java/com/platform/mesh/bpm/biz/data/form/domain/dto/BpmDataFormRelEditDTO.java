package com.platform.mesh.bpm.biz.data.form.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
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
