package com.platform.mesh.app.biz.modules.app.search.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 分页查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="分页查询DTO")
public class AppSearchPageDTO extends PageDTO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 隐藏标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "隐藏标识")
    private Integer hideFlag;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID",hidden = true)
    private Long scopeUserId;

}