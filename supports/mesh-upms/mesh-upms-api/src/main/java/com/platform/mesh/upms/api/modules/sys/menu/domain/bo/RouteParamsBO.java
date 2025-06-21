package com.platform.mesh.upms.api.modules.sys.menu.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description RouteItem Param信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "RouteItem Param信息")
public class RouteParamsBO {
    @Schema(description = "key")
    private String key;
    @Schema(description = "value")
    private String value;
}
