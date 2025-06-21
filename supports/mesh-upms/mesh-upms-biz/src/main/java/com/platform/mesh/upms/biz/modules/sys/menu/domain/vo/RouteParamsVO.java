package com.platform.mesh.upms.biz.modules.sys.menu.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 路由参数
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "RouteItem Param信息")
public class RouteParamsVO {
    @Schema(description = "key")
    private String key;
    @Schema(description = "value")
    private String value;
}
