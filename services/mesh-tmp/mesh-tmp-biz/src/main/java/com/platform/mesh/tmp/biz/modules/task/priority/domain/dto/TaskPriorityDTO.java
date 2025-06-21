package com.platform.mesh.tmp.biz.modules.task.priority.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 任务优先级DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务优先级DTO")
public class TaskPriorityDTO extends BaseDTO {



    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long id;


    /**
     * 优先级标识
     */
    @Schema(description = "优先级标识")
    private String priorityMac;


    /**
     * 优先级名称
     */
    @Schema(description = "优先级名称")
    private String priorityName;


    /**
     * 优先级排序
     */
    @Schema(description = "优先级排序")
    private Integer prioritySort;


    /**
     * 优先级状态PriorityFlagEnum
     */
    @Schema(description = "优先级状态PriorityFlagEnum")
    private Integer priorityFlag;


    /**
     * 优先级初始状态
     */
    @Schema(description = "优先级初始状态")
    private Integer iniFlag;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;


    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;


}