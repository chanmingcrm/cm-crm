package com.platform.mesh.tmp.biz.modules.task.base.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 任务VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务VO")
public class TaskBaseVO extends AppVO {

    /**
     * 父任务ID
     */
    @Schema(description = "父任务ID")
    private Long parentId;

    /**
     * 预计开始时间
     */
    @Schema(description = "预计开始时间")
    private LocalDateTime estStartTime;


    /**
     * 预计结束时间
     */
    @Schema(description = "预计结束时间")
    private LocalDateTime estEndTime;


    /**
     * 实际开始时间
     */
    @Schema(description = "实际开始时间")
    private LocalDateTime actStartTime;


    /**
     * 实际结束时间
     */
    @Schema(description = "实际结束时间")
    private LocalDateTime actEndTime;

}