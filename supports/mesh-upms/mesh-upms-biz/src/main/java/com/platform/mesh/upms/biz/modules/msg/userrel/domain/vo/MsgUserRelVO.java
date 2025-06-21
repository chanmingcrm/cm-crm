package com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 消息接收VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="消息接收VO")
public class MsgUserRelVO extends BaseVO {


    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private Long msgId;


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
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;


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

}