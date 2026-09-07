package com.platform.mesh.netty.server.domain.bo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 客服消息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服消息VO")
public class CcMsgVO extends BaseVO {

    /**
     * 提醒类型
     */
    @Schema(description = "提醒类型")
    private Integer noticeType;

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
     * 数据
     */
    @Schema(description = "数据")
    private Object data;

}
