package com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto;

import com.platform.mesh.app.api.modules.app.enums.comp.ColumnTypeEnum;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.dto.AppFormColumnSetActionDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.dto.AppFormColumnSetEventDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.domain.dto.AppFormColumnSetProcessDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.dto.AppFormColumnSetRequireDTO;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 单字段关联DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段关联DTO")
public class AppFormColumnDTO extends BaseDTO {



    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
    private Long id;

    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId = NumberConst.NUM_0.longValue();

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
     * 字段hash
     */
    @Schema(description = "字段hash")
    private String columnHash;


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
     * 字段类型ColumnTypeEnum
     */
    @SchemaEnum(value = ColumnTypeEnum.class,description = "字段类型ColumnTypeEnum")
    private Integer columnType;


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
     * 默认值数据类型
     */
    @SchemaEnum(value = DataTypeEnum.class,description = "默认值数据类型")
    private Integer defaultDataType;


    /**
     * 默认值
     */
    @Schema(description = "默认值")
    private Object defaultDataValue;


    /**
     * 关联数据类型标识DataTypeEnum
     */
    @SchemaEnum(value = DataTypeEnum.class, description = "关联数据类型标识")
    private Integer setDataType;


    /**
     * 配置数据值
     */
    @Schema(description = "配置数据值")
    private Object setDataValue;


    /**
     * 关联数据类型标识DataTypeEnum
     */
    @SchemaEnum(value = DataTypeEnum.class, description = "关联数据类型标识")
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
     * 动作集合
     */
    @Schema(description = "动作集合")
    private List<AppFormColumnSetActionDTO> actionList;


    /**
     * 事件集合
     */
    @Schema(description = "事件集合")
    private List<AppFormColumnSetEventDTO> eventList;


    /**
     * 流程集合
     */
    @Schema(description = "流程集合")
    private List<AppFormColumnSetProcessDTO> processList;


    /**
     * 请求集合
     */
    @Schema(description = "请求集合")
    private List<AppFormColumnSetRequireDTO> requireList;


    /**
     * 子节点
     */
    @Schema(description = "子节点")
    private List<AppFormColumnDTO> children;
}