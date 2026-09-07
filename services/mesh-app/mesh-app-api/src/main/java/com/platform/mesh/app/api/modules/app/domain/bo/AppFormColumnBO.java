package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.vo.TreeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 单字段关联VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="表单字段关联BO")
public class AppFormColumnBO extends TreeVO<AppFormColumnBO> {

    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
    private Long id;

    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;


    /**
     * 组件类型
     */
    @Schema(description = "组件类型")
    private Integer compType;


    /**
     * 组件标识
     */
    @Schema(description = "组件标识")
    private String compMac;


    /**
     * 字段标识
     */
    @Schema(description = "字段标识")
    private String columnMac;


    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnName;


    /**
     * 字段描述
     */
    @Schema(description = "字段描述")
    private String columnDesc;


    /**
     * 输入提示
     */
    @Schema(description = "输入提示")
    private String columnTips;


    /**
     * 前缀值
     */
    @Schema(description = "前缀值")
    private String prefixValue;


    /**
     * 后缀值
     */
    @Schema(description = "后缀值")
    private String suffixValue;


    /**
     * 默认值
     */
    @Schema(description = "默认值")
    private Object defaultValue;


    /**
     * 默认值数据类型
     */
    @Schema(description = "默认值数据类型")
    private Integer defaultDataType;


    /**
     * 最大值
     */
    @Schema(description = "最大值")
    private Integer maxValue;


    /**
     * 最小值
     */
    @Schema(description = "最小值")
    private Integer minValue;


    /**
     * 精度值
     */
    @Schema(description = "精度值")
    private Integer degreeValue;


    /**
     * 关联数据类型标识DataTypeEnum
     */
    @Schema(description = "关联数据类型标识DataTypeEnum")
    private Integer setDataType;


    /**
     * 关联数据值
     */
    @Schema(description = "关联数据值")
    private Object setDataValue;


    /**
     * 关联数据类型标识DataTypeEnum
     */
    @Schema(description = "关联数据类型标识DataTypeEnum")
    private Integer relDataType;


    /**
     * 关联数据值
     */
    @Schema(description = "关联数据值")
    private Object relDataValue;


    /**
     * 关联数据转换标识DataTypeEnum
     */
    @Schema(description = "关联数据转换标识DataTypeEnum")
    private Integer relTransDataType;


    /**
     * 关联数据转换目标值
     */
    @Schema(description = "关联数据转换目标值")
    private Object relTransDataValue;


    /**
     * 唯一标识UniqueFlagEnum
     */
    @Schema(description = "唯一标识UniqueFlagEnum")
    private Integer uniqueFlag;


    /**
     * 空值标识EmptyFlagEnum
     */
    @Schema(description = "空值标识EmptyFlagEnum")
    private Integer emptyFlag;


    /**
     * 多选标识MutiFlag
     */
    @Schema(description = "多选标识MutiFlag")
    private Integer multiFlag;


    /**
     * 隐藏标识HiddenFlagEnum
     */
    @Schema(description = "隐藏标识HiddenFlagEnum")
    private Integer hiddenFlag;


    /**
     * 删除标识YesOrNoEnum
     */
    @Schema(description = "删除标识YesOrNoEnum")
    private Integer deleteFlag;


    /**
     * 横坐标
     */
    @Schema(description = "横坐标")
    private Integer xAddr;


    /**
     * 纵坐标
     */
    @Schema(description = "纵坐标")
    private Integer yAddr;


    /**
     * 样式百分比%
     */
    @Schema(description = "样式百分比%")
    private Integer stylePercent;


    /**
     * 样式脚本
     */
    @Schema(description = "样式脚本")
    private String styleCss;


    /**
     * 样式svg
     */
    @Schema(description = "样式svg")
    private String styleSvg;


    /**
     * 子节点
     */
    @Schema(description = "子节点")
    private List<AppFormColumnBO> children;
}