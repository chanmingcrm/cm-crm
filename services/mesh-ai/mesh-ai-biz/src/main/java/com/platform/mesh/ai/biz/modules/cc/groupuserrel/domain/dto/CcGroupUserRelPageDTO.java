package com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 会话群人员关系分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="会话群人员关系分页DTO")
public class CcGroupUserRelPageDTO extends PageDTO {

    /**
     * 群Hash
     */
    @Schema(description = "群Hash")
    private String groupHash;

    /**
     * 群名称
     */
    @Schema(description = "群名称")
    private String groupName;

}
