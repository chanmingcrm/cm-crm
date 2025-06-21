package com.platform.mesh.uaa.biz.auth.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 授权渲染DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="授权渲染DTO")
public class AuthRenderDTO extends BaseDTO {

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    private Long clientId;

    /**
    * 客户端来源
    */
    @SchemaEnum(value = SourceFlagEnum.class, description = "客户端来源")
    private Integer source;
}
