package com.platform.mesh.upms.biz.modules.sys.menu.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "路由类型枚举",enumAsRef = true)
public enum MenuArgTypeEnum implements BaseEnum<MenuArgTypeEnum, Integer> {

    /**
     * RouteItem
     */
    USING(1,  "RouteItem"),
    /**
     * RouteMate
     */
    STOPED(2,  "RouteMate"),
    ;


    private final Integer value;

    private final String desc;

    MenuArgTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

}
