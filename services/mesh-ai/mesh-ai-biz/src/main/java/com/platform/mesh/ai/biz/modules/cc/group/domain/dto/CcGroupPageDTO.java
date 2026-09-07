package com.platform.mesh.ai.biz.modules.cc.group.domain.dto;

import com.platform.mesh.ai.biz.modules.cc.group.enums.CcGroupStatusEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 客服群分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服群分页DTO")
public class CcGroupPageDTO extends PageDTO {


    /**
     * 人员Hash
     */
    @Schema(description = "人员Hash")
    private String userHash;

    /**
     * 会话状态
     */
    @SchemaEnum(value = CcGroupStatusEnum.class, description = "会话状态")
    private Integer status;

}
