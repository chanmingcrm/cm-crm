package com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化自动化设置分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化自动化设置分页DTO")
public class AppModuleSetTransAutoPageDTO extends PageDTO {


    /**
     * 转化配置ID
     */
    @Schema(description = "转化配置ID")
    private Long transId;

}