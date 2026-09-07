package com.platform.mesh.uaa.biz.auth.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.zhyd.oauth.model.AuthCallback;


/**
 * @description 授权回调DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="授权回调DTO")
public class AuthClientDTO extends AuthCallback {

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    private String clientId;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private String agentId;

    /**
     * 签名url
     */
    @Schema(description = "签名url")
    private String url;

    /**
     * 签名类型
     */
    @Schema(description = "签名类型")
    private Integer ticketType;
}
