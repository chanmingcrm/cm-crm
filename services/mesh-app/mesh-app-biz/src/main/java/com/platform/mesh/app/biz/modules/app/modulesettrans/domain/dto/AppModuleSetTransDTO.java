package com.platform.mesh.app.biz.modules.app.modulesettrans.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化设置DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化设置DTO")
public class AppModuleSetTransDTO extends BaseDTO {


    /**
     * id
     */
    @Schema(description = "id")
    private Long id;


    /**
     * 模块来源ID
     */
    @Schema(description = "模块来源ID")
    private Long moduleFromId;


    /**
     * 模块目标ID
     */
    @Schema(description = "模块目标ID")
    private Long moduleToId;


    /**
     * 是否删除来源数据
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否删除来源数据")
    private Integer delFrom;

}