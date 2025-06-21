package com.platform.mesh.bpm.biz.modules.inst.node.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "节点类型枚举",enumAsRef = true)
public enum InstNodeOutEnum implements BaseEnum<InstNodeOutEnum, Integer> {
    /**
     * 元状态
     */
    INIT(0,0,  "元状态"),
    /**
     * 手动触发流出
     */
    HANDLE_OUT(1,101,  "手动触发流出"),
    /**
     * 自动无状态流出
     */
    AUTO_OUT_NO_STATUS(2,200,  "自动无状态流出"),
    /**
     * 自动变量触发流出
     */
    AUTO_OUT_VAR(2,201,  "自动变量触发流出"),
    /**
     * 自动限时触发流出
     */
    AUTO_OUT_TIME(2,202,  "自动限时触发流出"),
    /**
     * 自动异常触发流出
     */
    AUTO_OUT_ERROR(2,203,  "自动异常触发流出"),
    ;


    private final Integer code;

    private final Integer value;

    private final String desc;

    InstNodeOutEnum(Integer code,Integer value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
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
