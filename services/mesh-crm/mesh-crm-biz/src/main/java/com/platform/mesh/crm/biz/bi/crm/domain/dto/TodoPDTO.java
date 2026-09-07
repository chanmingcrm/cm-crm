package com.platform.mesh.crm.biz.bi.crm.domain.dto;

import com.platform.mesh.core.application.domain.dto.QueryDTO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.crm.biz.bi.crm.enums.TodoTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description ES分页查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="待办查询DTO")
public class TodoPDTO extends QueryDTO {


    /**
     * 当前查询批次参数
     */
    @Schema(description = "当前查询批次参数")
    private Long batchId;

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
     * 待办类型
     */
    @SchemaEnum(value = TodoTypeEnum.class, description = "待办类型")
    private Integer todoType = TodoTypeEnum.TODO.getValue();

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
     * 忽略的数据
     */
    @Schema(description = "忽略的数据")
    private List<Long> ignoreIds;

}
