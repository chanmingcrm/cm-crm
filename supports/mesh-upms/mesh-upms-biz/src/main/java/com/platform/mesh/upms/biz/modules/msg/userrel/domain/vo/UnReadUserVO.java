package com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 消息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="消息VO")
public class UnReadUserVO extends BaseVO {

    /**
     * 人员ID
     */
    @Schema(description = "人员ID")
    private Long userId;

    /**
     * 未读数量
     */
    @Schema(description = "未读数量")
    private Long unReadCount;
}