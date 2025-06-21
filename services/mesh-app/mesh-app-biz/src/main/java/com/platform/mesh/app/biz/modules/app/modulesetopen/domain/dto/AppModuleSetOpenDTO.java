package com.platform.mesh.app.biz.modules.app.modulesetopen.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块开放DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块开放DTO")
public class AppModuleSetOpenDTO extends BaseDTO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 前负责人领取天数规则
     */
    @Schema(description = "前负责人领取天数规则")
    private Integer userDayRule;


    /**
     * 每天领取个数规则
     */
    @Schema(description = "每天领取个数规则")
    private Integer numDayRule;

}