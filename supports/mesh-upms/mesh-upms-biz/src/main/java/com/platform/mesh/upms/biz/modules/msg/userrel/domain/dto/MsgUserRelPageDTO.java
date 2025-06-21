package com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 消息接收分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="消息接收分页DTO")
public class MsgUserRelPageDTO extends PageDTO {


    /**
     * 用户ID
     */
    @Schema(description = "用户ID",hidden = true)
    private Long userId;

    /**
     * 消息标识
     */
    @SchemaEnum(value = MsgFlagEnum.class, description = "消息标识")
    private Integer msgFlag;

    /**
     * 已读标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "已读标识")
    private Integer readFlag;

    /**
     * 删除标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "删除标识")
    private Integer delFlag;

}