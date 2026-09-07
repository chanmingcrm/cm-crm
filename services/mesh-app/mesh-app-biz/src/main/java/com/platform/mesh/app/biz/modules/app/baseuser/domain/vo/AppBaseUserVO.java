package com.platform.mesh.app.biz.modules.app.baseuser.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 自定义快捷应用VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="自定义快捷应用VO")
public class AppBaseUserVO extends BaseVO {


    /**
     * 快捷ID
     */
    @Schema(description ="快捷ID")
    private Long id;


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