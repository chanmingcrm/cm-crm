package com.platform.mesh.bpm.biz.modules.inst.node.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "节点类型枚举",enumAsRef = true)
public enum InstNodeInEnum implements BaseEnum<InstNodeInEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,0,  "元状态"),
    /**
     * 手动触发进入
     */
    HANDLE_IN(1,101,  "手动触发进入"),
    /**
     * 自动无状态进入
     */
    AUTO_IN_NO_STATUS(2,200,  "自动无状态进入"),
    /**
     * 自动变量触发进入
     */
    AUTO_IN_VAR(2,201,  "自动变量触发进入"),
    /**
     * 自动限时触发进入
     */
    AUTO_IN_TIME(2,202,  "自动限时触发进入"),
    /**
     * 自动异常触发进入
     */
    AUTO_IN_ERROR(2,203,  "自动异常触发进入"),
    ;


    @Getter
    private final Integer code;

    private final Integer value;

    private final String desc;

    InstNodeInEnum(Integer code,Integer value, String desc) {
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

}
