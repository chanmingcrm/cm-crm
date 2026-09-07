package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 表单BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="表单BO")
public class AppFormBO extends BaseBO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 表单标识
     */
    @Schema(description = "表单标识")
    private String formMac;


    /**
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;


    /**
     * 表单类型FormTypeEnum
     */
    @SchemaEnum(value = FormTypeEnum.class, description = "表单类 型FormTypeEnum")
    private Integer formType;

}