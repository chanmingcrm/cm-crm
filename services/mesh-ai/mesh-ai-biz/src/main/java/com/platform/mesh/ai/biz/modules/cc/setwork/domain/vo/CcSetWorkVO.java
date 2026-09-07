package com.platform.mesh.ai.biz.modules.cc.setwork.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalTime;


/**
 * @description 排班VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="排班VO")
public class CcSetWorkVO extends BaseVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 排班名称
     */
    @Schema(description = "排班名称")
    private String setWorkName;

    /**
     * 包含的周天
     */
    @Schema(description = "包含的周天")
    private String includeWeekDays;

    /**
     * 排除的周天
     */
    @Schema(description = "排除的周天")
    private String excludeWeekDays;

    /**
     * 接待规则
     */
    @Schema(description = "接待规则")
    private Integer workRule;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalTime endTime;

}
