package com.platform.mesh.crm.biz.modules.crm.allgoal.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 客户关系目标分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系目标分页DTO")
public class CrmAllGoalPageDTO extends PageDTO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

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

}