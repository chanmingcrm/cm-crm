package com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 授权客户端系统关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="授权客户端系统关系DTO")
public class TenantClientAddDTO extends BaseDTO {


    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * 代理ID
     */
    @Schema(description = "代理ID")
    private String agentId;

    /**
     * 客户端id
     */
    @Schema(description = "客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;

    /**
     * 客户端来源
     */
    @SchemaEnum(value = SourceFlagEnum.class, description = "客户端来源")
    private Integer clientSource;

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    private String redirectUri;

}