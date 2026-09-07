package com.platform.mesh.bpm.biz.modules.data.form.domain.dto;


import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 数据流程表单分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="数据流程表单分页DTO")
public class BpmDataFormRelPageDTO extends PageDTO {

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;
}
