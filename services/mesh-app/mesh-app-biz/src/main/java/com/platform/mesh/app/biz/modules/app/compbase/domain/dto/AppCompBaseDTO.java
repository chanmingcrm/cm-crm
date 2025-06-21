package com.platform.mesh.app.biz.modules.app.compbase.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 页面组件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="页面组件DTO")
public class AppCompBaseDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 组件类型CompTypeEnum
     */
    @Schema(description = "组件类型CompTypeEnum")
    private Integer compType;


    /**
     * 组件标识
     */
    @Schema(description = "组件标识")
    private String compMac;


    /**
     * 组件名称
     */
    @Schema(description = "组件名称")
    private String compName;


    /**
     * 组件图标
     */
    @Schema(description = "组件图标")
    private String compSvg;


    /**
     * 字段标识ColumnFlagEnum
     */
    @Schema(description = "字段标识ColumnFlagEnum")
    private Integer columnFlag;


    /**
     * 初始标识InitFlagEnum
     */
    @Schema(description = "初始标识InitFlagEnum")
    private Integer initFlag;


    /**
     * 隐藏标识HiddenFlagEnum
     */
    @Schema(description = "隐藏标识HiddenFlagEnum")
    private Integer hiddenFlag;


    /**
     * 删除标识YesOrNoEnum
     */
    @Schema(description = "删除标识YesOrNoEnum")
    private Integer delFlag;


}