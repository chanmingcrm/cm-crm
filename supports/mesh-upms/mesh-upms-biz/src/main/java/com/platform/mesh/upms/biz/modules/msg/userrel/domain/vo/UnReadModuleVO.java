package com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
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
public class UnReadModuleVO extends BaseVO {


    /**
     * 消息标识
     */
    @SchemaEnum(value = MsgFlagEnum.class, description = "消息标识")
    private Integer msgFlag;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 未读数量
     */
    @Schema(description = "未读数量")
    private Integer unReadCount;
}