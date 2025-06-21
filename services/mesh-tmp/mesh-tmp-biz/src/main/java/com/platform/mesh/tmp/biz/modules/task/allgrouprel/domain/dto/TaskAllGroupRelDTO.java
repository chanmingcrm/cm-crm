package com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务分组关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务分组关系DTO")
public class TaskAllGroupRelDTO extends BaseDTO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 分组ID
     */
    @Schema(description = "分组ID")
    private Long groupId;


    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long taskId;


}