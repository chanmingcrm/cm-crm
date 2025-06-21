package com.platform.mesh.upms.api.modules.sys.account.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "菜单类型枚举",enumAsRef = true)
public enum MenuTypeEnum implements BaseEnum<MenuTypeEnum, Integer> {

    /**
     * 平台
     */
    PLATFORM(1,  "平台"),
    /**
     * 客户端
     */
    CLIENT(2,  "客户端"),
    /**
     * 应用
     */
    APP(3,  "应用"),
    /**
     * 模块
     */
    MODULE(4,  "模块"),
    /**
     * 菜单
     */
    MENU(5,  "菜单"),
    /**
     * 分类
     */
    CATEGORY(6,  "分类"),
    /**
     * 组件
     */
    COMPONENT(7,  "组件"),
    ;


    private final Integer value;

    private final String desc;

    MenuTypeEnum(Integer value, String desc) {
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
