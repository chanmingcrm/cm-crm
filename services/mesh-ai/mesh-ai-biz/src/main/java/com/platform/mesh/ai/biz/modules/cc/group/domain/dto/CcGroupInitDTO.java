package com.platform.mesh.ai.biz.modules.cc.group.domain.dto;

import com.platform.mesh.ai.biz.modules.cc.group.enums.GroupTypeEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 客服消息初始化DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服消息初始化DTO")
public class CcGroupInitDTO extends BaseDTO {


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
     * 人员名称
     */
    @Schema(description = "人员名称")
    private String userName;
}
