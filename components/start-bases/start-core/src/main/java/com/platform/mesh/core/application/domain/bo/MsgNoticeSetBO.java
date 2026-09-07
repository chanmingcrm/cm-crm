package com.platform.mesh.core.application.domain.bo;

import com.platform.mesh.core.constants.NumberConst;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 消息提醒设置BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="消息提醒设置BO")
public class MsgNoticeSetBO extends BaseBO {


    /**
     * 提醒类型
     */
    @Schema(description = "提醒类型")
    private Integer noticeType = NumberConst.NUM_2;

    /**
     * 前置时间
     */
    @Schema(description = "前置时间")
    private Integer noticePreDays = NumberConst.NUM_0;

    /**
     * 后置时间
     */
    @Schema(description = "后置时间")
    private Integer noticeSufDays = NumberConst.NUM_0;

    /**
     * 提醒循环类型 默认1次提醒
     */
    @Schema(description = "提醒循环类型")
    private Integer noticeLoop = NumberConst.NUM_1;

    /**
     * 提醒间隔值 默认1天
     */
    @Schema(description = "提醒间隔值")
    private Integer noticeIntervalValue = NumberConst.NUM_1;

    /**
     * 提醒间隔单位  默认天
     */
    @Schema(description = "提醒间隔单位")
    private Integer noticeIntervalUnit = NumberConst.NUM_5;

}