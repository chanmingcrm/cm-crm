package com.platform.mesh.upms.biz.modules.msg.notice.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgTypeEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 消息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="消息DTO")
public class MsgNoticeDTO extends BaseDTO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 父模块ID
     */
    @Schema(description = "父模块ID")
    private Long parentModuleId;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String moduleName;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 消息标识
     */
    @Schema(description = "消息标识")
    private Integer msgFlag;

    /**
     * 消息类型
     */
    @Schema(description = "消息类型")
    private Integer msgType;

    /**
     * 消息标题
     */
    @Schema(description = "消息标题")
    private String msgTitle;

    /**
     * 消息主体
     */
    @Schema(description = "消息主体")
    private String msgBody;

    /**
     * 消息外链
     */
    @Schema(description = "消息外链")
    private String msgHref;

    /**
     * 提醒类型
     */
    @SchemaEnum(value = NoticeTypeEnum.class, description = "提醒类型")
    private Integer noticeType;

    /**
     * 提醒时间
     */
    @Schema(description = "提醒时间")
    private LocalDateTime noticeSetTime;

    /**
     * 提醒循环类型
     */
    @Schema(description = "提醒循环类型")
    private Integer noticeLoop;

    /**
     * 提醒开始天数:只针对循环提醒有用
     */
    @Schema(description = "提醒开始天数")
    private Integer noticePreDays;

    /**
     * 提醒结束天数:只针对循环提醒有用
     */
    @Schema(description = "提醒结束天数")
    private Integer noticeSufDays;

    /**
     * 提醒间隔值
     */
    @Schema(description = "提醒间隔值")
    private Integer noticeIntervalValue;

    /**
     * 提醒间隔单位
     */
    @Schema(description = "提醒间隔单位")
    private Integer noticeIntervalUnit;

    /**
     * 消息接收人ID
     */
    @Schema(description = "消息接收人ID")
    private List<Long> msgUserIds;
}