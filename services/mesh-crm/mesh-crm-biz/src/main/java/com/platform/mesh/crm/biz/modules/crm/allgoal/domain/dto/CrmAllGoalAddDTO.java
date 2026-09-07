package com.platform.mesh.crm.biz.modules.crm.allgoal.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.application.domain.dto.DataDTO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description 客户关系目标DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系目标DTO")
public class CrmAllGoalAddDTO extends BaseDTO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String moduleName;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private List<DataDTO> dataList;

    /**
     * 数据标识
     */
    @SchemaEnum(value = DataFlagEnum.class, description = "数据标识")
    private Integer dataFlag;

    /**
     * 数据标识
     */
    @Schema(description = "数据标识")
    private Integer yearTime;

    /**
     * 年度目标
     */
    @Schema(description = "年度目标")
    private BigDecimal yearGoal;

    /**
     * 天目标
     */
    @Schema(description = "天目标")
    private BigDecimal dayGoal;

    /**
     * 一月目标
     */
    @Schema(description = "一月目标")
    private BigDecimal janGoal;

    /**
     * 二月目标
     */
    @Schema(description = "二月目标")
    private BigDecimal febGoal;

    /**
     * 三月目标
     */
    @Schema(description = "三月目标")
    private BigDecimal marGoal;

    /**
     * 四月目标
     */
    @Schema(description = "四月目标")
    private BigDecimal aprGoal;

    /**
     * 五月目标
     */
    @Schema(description = "五月目标")
    private BigDecimal mayGoal;

    /**
     * 六月目标
     */
    @Schema(description = "六月目标")
    private BigDecimal junGoal;

    /**
     * 七月目标
     */
    @Schema(description = "七月目标")
    private BigDecimal julGoal;

    /**
     * 八月目标
     */
    @Schema(description = "八月目标")
    private BigDecimal augGoal;

    /**
     * 九月目标
     */
    @Schema(description = "九月目标")
    private BigDecimal sepGoal;

    /**
     * 十月目标
     */
    @Schema(description = "十月目标")
    private BigDecimal octGoal;

    /**
     * 十一月目标
     */
    @Schema(description = "十一月目标")
    private BigDecimal novGoal;

    /**
     * 十二月目标
     */
    @Schema(description = "十二月目标")
    private BigDecimal decGoal;

}