package com.platform.mesh.ai.biz.modules.cc.msg.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 客服消息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服消息分页DTO")
public class CcSessionMsgPageDTO extends PageDTO {

    /**
     * 群Hash
     */
    @Schema(description = "群Hash")
    private String groupHash;

}
