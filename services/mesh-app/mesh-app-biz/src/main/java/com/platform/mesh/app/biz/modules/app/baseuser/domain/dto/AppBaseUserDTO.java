package com.platform.mesh.app.biz.modules.app.baseuser.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 自定义快捷应用DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="自定义快捷应用DTO")
public class AppBaseUserDTO extends BaseDTO {


    /**
     * 用户ID
     */
    @Schema(description ="用户ID")
    private Long userId;


    /**
     * 应用主键ID
     */
    @Schema(description ="应用主键ID")
    private Long appId;


    /**
     * 父模块ID
     */
    @Schema(description ="父模块ID")
    private Long parentModuleId;


    /**
     * 模块ID
     */
    @Schema(description ="模块ID")
    private Long moduleId;


    /**
     * 排序
     */
    @Schema(description ="排序")
    private Integer sort;

}