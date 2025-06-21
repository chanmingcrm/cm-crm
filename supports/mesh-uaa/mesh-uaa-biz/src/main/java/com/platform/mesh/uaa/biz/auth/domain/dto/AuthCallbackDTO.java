package com.platform.mesh.uaa.biz.auth.domain.dto;


import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
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
public class AuthCallbackDTO extends AuthCallback {

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    private Long clientId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
    * 客户端来源
    */
    @SchemaEnum(value = SourceFlagEnum.class, description = "客户端来源")
    private Integer source;
}
