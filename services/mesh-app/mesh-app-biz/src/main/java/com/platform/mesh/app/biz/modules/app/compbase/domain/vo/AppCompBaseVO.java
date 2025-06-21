package com.platform.mesh.app.biz.modules.app.compbase.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 页面组件VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="页面组件VO")
public class AppCompBaseVO extends BaseVO {



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
     * 是否初始化ES
     */
    @Schema(description = "是否初始化ES")
    private Integer esInit;


    /**
     * ES kind类型 {@link co.elastic.clients.elasticsearch._types.mapping.Property.Kind}
     */
    @Schema(description = "ES kind类型")
    private String esKind;


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