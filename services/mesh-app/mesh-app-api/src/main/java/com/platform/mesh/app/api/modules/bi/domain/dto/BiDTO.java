package com.platform.mesh.app.api.modules.bi.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.utils.format.TimeUnitEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description BI统计DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="BI统计DTO")
public class BiDTO extends BaseDTO {

    /**
     * 当前查询批次参数
     */
    @Schema(description = "当前查询批次参数")
    private Long batchId;

    /**
     * 数据权限
     */
    @SchemaEnum(value = DataScopeEnum.class, description = "数据权限")
    private Integer dataScope;

    /**
     * 数据标识
     */
    @SchemaEnum(value = DataFlagEnum.class, description = "数据标识")
    private Integer dataFlag;

    /**
     * 数据ID
     */
    @Schema( description = "数据ID")
    private List<Long> dataIds;

    /**
     * 统计时间维度
     */
    @SchemaEnum(value = TimeUnitEnum.class, description = "统计时间维度")
    private Integer timeUnit;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    /**
     * 比较开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime relStartTime;

    /**
     * 比较结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime relEndTime;

    /**
     * 业务节点编码
     */
    @Schema(description = "业务节点编码")
    private String nodeCode;

}
