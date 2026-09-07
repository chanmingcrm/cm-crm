package com.platform.mesh.utils.excel.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description 需要处理转换对象组件枚举
 * @author 蝉鸣
 */
@Schema(description = "需要处理转换对象组件枚举",enumAsRef = true)
public enum CompTypeEnum implements BaseEnum<CompTypeEnum, Integer> {

    /**
     * 需要处理组件
     */
    CHECKBOX(1,  "checkbox",  "id","name",DataTypeEnum.JSON_ARRAY.getValue()),
    RADIO(2,  "radio",  "id","name",DataTypeEnum.JSON_ARRAY.getValue()),
    SELECT(3,  "select",  "id","name",DataTypeEnum.JSON_ARRAY.getValue()),
    MAP(4,  "map",  "","name",DataTypeEnum.JSON_OBJECT.getValue()),
    USER(5,  "user",  "id","name",DataTypeEnum.JSON_ARRAY.getValue()),
    DEP(6,  "dep",  "id","name",DataTypeEnum.JSON_ARRAY.getValue()),
    RELEVANCE(7,  "relevance",  "id","name",DataTypeEnum.JSON_ARRAY.getValue()),
    RELEVANCE_ALL_FIELD(8,  "related_all_field",  "id","name",DataTypeEnum.JSON_ARRAY.getValue()),
    FILE(9,  "file",  "id","name",DataTypeEnum.JSON_ARRAY.getValue()),
    ;
    private final Integer value;

    private final String desc;

    @Getter
    private final String idMac;

    @Getter
    private final String nameMac;

    @Getter
    private final Integer dataType;

    CompTypeEnum(Integer value, String desc, String idMac, String nameMac,Integer dataType) {
        this.value = value;
        this.desc = desc;
        this.idMac = idMac;
        this.nameMac = nameMac;
        this.dataType = dataType;
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
