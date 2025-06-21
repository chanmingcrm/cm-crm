package com.platform.mesh.app.biz.modules.app.formbase.domain.dto;

import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 表单列表查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="表单列表查询DTO")
public class AppFormBasePageDTO extends PageDTO {

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;


    /**
     * 表单类型FormTypeEnum
     */
    @SchemaEnum(value = FormTypeEnum.class,description = "表单类型FormTypeEnum")
    private Integer formType;

}
