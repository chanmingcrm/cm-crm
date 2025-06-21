package com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 消息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="消息DTO")
public class MsgReadDTO extends BaseDTO {

    /**
     * 全部已读
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "全部已读")
    private Integer all;

    /**
     * ID
     */
    @Schema(description = "ID")
    private List<Long> ids;
}