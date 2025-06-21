package com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 任务人员历史DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务人员历史DTO")
public class TaskUserRelHisDTO extends BaseDTO {



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
     * 参与状态
     */
    @Schema(description = "参与状态")
    private Integer inFlag;


    /**
     * 加入时间
     */
    @Schema(description = "加入时间")
    private LocalDateTime startTime;


    /**
     * 退出时间
     */
    @Schema(description = "退出时间")
    private LocalDateTime endTime;


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