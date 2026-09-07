package com.platform.mesh.app.api.modules.app.enums.comp;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

/**
 * 表单类型枚举
 * @description 单字段关联
 * @author 蝉鸣
 */
@Schema(description = "表单类型枚举",enumAsRef = true)
public enum FormTypeEnum implements BaseEnum<FormTypeEnum, Integer> {

    FORM_ADD(1,101,"新增表单"),
    FORM_INFO(1,102,"详情表单"),
    FORM_EDIT(1,103,"编辑表单"),
    FORM_STAGE(1,104,"阶段表单"),
    FORM_PROCESS(1,105,"审批表单"),
    HEAD_LIST(2,201,"列表表头"),
    HEAD_EXPORT(2,202,"导出表头"),
    HEAD_IMPORT(2,203,"导入表头"),
    PAGE_LIST(3,301,"列表页面"),
    PAGE_INFO(3,302,"详情页面"),
    BI_PANEL(4,401,"BI页面"),
    ;


    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    FormTypeEnum(Integer code, Integer value, String desc) {
        this.code = code;
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

    public static List<Integer> getValueByCode(Integer code) {
        List<Integer> valueList = CollUtil.newArrayList();
        for (FormTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                valueList.add(value.getValue());
            }
        }
        return valueList;
    }
}
