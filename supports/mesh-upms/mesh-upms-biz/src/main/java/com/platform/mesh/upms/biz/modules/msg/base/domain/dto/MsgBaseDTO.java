package com.platform.mesh.upms.biz.modules.msg.base.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgTypeEnum;
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
public class MsgBaseDTO extends BaseDTO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;


    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;


    /**
     * 消息标识
     */
    @SchemaEnum(value = MsgFlagEnum.class, description = "消息标识")
    private Integer msgFlag;


    /**
     * 消息类型
     */
    @SchemaEnum(value = MsgTypeEnum.class, description = "消息类型")
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
     * 提醒时间
     */
    @Schema(description = "提醒时间")
    private LocalDateTime noticeTime;

    /**
     * 消息接收人ID
     */
    @Schema(description = "消息接收人ID")
    private List<Long> msgUserIds;
}