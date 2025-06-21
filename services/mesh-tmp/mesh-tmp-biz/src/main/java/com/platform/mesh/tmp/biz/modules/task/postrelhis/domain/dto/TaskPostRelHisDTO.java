package com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 任务工职关系历史DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务工职关系历史DTO")
public class TaskPostRelHisDTO extends BaseDTO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long taskId;


    /**
     * 人员ID
     */
    @Schema(description = "人员ID")
    private Long userId;


    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private Long postId;


    /**
     * 岗位标识
     */
    @Schema(description = "岗位标识")
    private String postMac;


    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    private String postName;


    /**
     * 岗位排序
     */
    @Schema(description = "岗位排序")
    private Integer postSort;


    /**
     * 在岗标识
     */
    @Schema(description = "在岗标识")
    private Integer onFlag;


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


}