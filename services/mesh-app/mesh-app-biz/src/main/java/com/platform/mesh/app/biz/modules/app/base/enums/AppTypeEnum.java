package com.platform.mesh.app.biz.modules.app.base.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "应用类型枚举",enumAsRef = true)
public enum AppTypeEnum implements BaseEnum<AppTypeEnum, Integer> {

    /**
     * 虚拟应用：模块信息保存在mesh_app,数据统一保存在app_form_column_data
     */
    VIRTUAL(1,  "VIRTUAL"),
    /**
     * 实例应用：模块信息保存在mesh_app,数据统一保存在业务独立数据库中
     */
    INSTANCE(2,  "INSTANCE"),
    /**
     * 私有应用：模块信息保存在独立数据库中,数据统一保存在业务独立数据库中
     */
    STAND(2,  "STAND"),
    ;


    private final Integer value;

    private final String desc;

    AppTypeEnum(Integer value, String desc) {
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
