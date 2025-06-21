package com.platform.mesh.tmp.biz.modules.task.statusrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务状态关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务状态关系DTO")
public class TaskStatusRelDTO extends BaseDTO {



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
     * 状态ID
     */
    @Schema(description = "状态ID")
    private Long statusId;


}