package com.platform.mesh.netty.server.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 客服消息BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服消息BO")
public class CcMsgBO extends BaseBO {

    /**
     * 房间Hash
     */
    @Schema(description = "房间Hash")
    private String groupHash;

    /**
     * 用户Hash
     */
    @Schema(description = "用户Hash")
    private String userHash;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private Integer userType;

    /**
     * 协议类型
     */
    @Schema(description = "协议类型")
    private Integer protocolType;

    /**
     * 消息类型
     */
    @Schema(description = "消息")
    private Integer msgType;

    /**
     * 消息
     */
    @Schema(description = "消息")
    private String msgContent;

    /**
     * 会话链接ID
     */
    @Schema(description = "会话链接ID")
    private Long webSetId;

    private String source;
    private String sourcePage;
    private String visitorName;
    private String visitorContact;
    private String visitorCompany;
    private String visitorDemand;
}
