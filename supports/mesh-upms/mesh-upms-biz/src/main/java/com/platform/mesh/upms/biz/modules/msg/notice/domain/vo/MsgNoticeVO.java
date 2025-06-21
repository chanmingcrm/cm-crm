package com.platform.mesh.upms.biz.modules.msg.notice.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.biz.modules.msg.notice.enums.NoticeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 消息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="消息VO")
public class MsgNoticeVO extends BaseVO {

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
     * 提醒类型
     */
    @Schema(description = "提醒类型")
    private LocalDateTime noticeStartTime;

    /**
     * 提醒类型
     */
    @Schema(description = "提醒类型")
    private LocalDateTime noticeEndTime;

    /**
     * 提醒类型
     */
    @Schema(description = "提醒类型")
    private LocalDateTime noticeLastTime;

    /**
     * 提醒类型
     */
    @Schema(description = "提醒类型")
    private LocalDateTime noticeNextTime;

    /**
     * 提醒循环类型
     */
    @Schema(description = "提醒循环类型")
    private Integer noticeLoop;

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
     * 提醒人员ID
     */
    @Schema(description = "提醒人员ID")
    private Long noticeUserId;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updateUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private Long scopeOrgId;

}