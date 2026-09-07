package com.platform.mesh.uaa.biz.modules.tenant.client.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 授权客户端租户关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="授权客户端租户关系DTO")
public class TenantClientEditDTO extends TenantClientAddDTO {


    /**
     * id
     */
    @Schema(description = "id")
    private String id;

}