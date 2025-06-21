package com.platform.mesh.tmp.biz.modules.task.userrel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务人员VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务人员VO")
public class TaskUserRelVO extends BaseVO {



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


}