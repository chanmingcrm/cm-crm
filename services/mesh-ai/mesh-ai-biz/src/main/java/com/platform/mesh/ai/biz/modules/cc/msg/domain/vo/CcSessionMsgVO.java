package com.platform.mesh.ai.biz.modules.cc.msg.domain.vo;

import com.platform.mesh.ai.biz.modules.cc.group.enums.GroupTypeEnum;
import com.platform.mesh.ai.biz.modules.cc.user.enums.UserTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.netty.server.soa.msg.enums.CcMsgTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 客服会话VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服会话VO")
public class CcSessionMsgVO extends BaseVO {


    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 群Hash
     */
    @Schema(description = "群Hash")
    private String groupHash;

    /**
     * 群类型
     */
    @SchemaEnum(value = GroupTypeEnum.class, description = "群类型")
    private Integer groupType;

    /**
     * 用户Hash
     */
    @Schema(description = "用户Hash")
    private String userHash;

    /**
     * 用户类型
     */
    @SchemaEnum(value = UserTypeEnum.class, description = "用户类型")
    private Integer userType;

    /**
     * 消息类型
     */
    @SchemaEnum(value = CcMsgTypeEnum.class, description = "消息类型")
    private Integer msgType;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String msgContent;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
