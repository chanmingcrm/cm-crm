package com.platform.mesh.douyin.domain.feiyu.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="飞鱼DTO对象")
public class DouyinAppDTO extends BaseDTO {

    @Schema(description ="key")
    private String clientKey;

    @Schema(description ="密钥")
    private String clientSecret;

    @Schema(description ="授权类型")
    private String grantType;

    @Schema(description ="账户ID")
    private String accountId;


}
