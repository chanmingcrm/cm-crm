package com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务分组关系VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务分组关系VO")
public class TaskAllGroupRelVO extends BaseVO {



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