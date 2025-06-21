package com.platform.mesh.app.biz.modules.app.base.domain.dto;

import com.platform.mesh.app.biz.modules.app.modulebase.enums.CopyTypeEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块应用DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块应用DTO")
public class AppBaseCopyDTO extends BaseDTO {

    /**
     * 源模块ID
     */
    @Schema(description = "源模块ID")
    private Long sourceId;


    /**
     * 目标模块ID
     */
    @Schema(description = "目标模块ID")
    private Long targetId;


    /**
     * 复制类型
     */
    @SchemaEnum(value = CopyTypeEnum.class, description = "复制类型")
    private Integer copyType;


    /**
     * 强制覆盖
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "强制覆盖")
    private Integer forceOver;

}