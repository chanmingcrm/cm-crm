package com.platform.mesh.upms.biz.modules.doc.file.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 文件标识枚举
 * @author 蝉鸣
 */
@Schema(description = "文件标识枚举",enumAsRef = true)
public enum FileFlagEnum implements BaseEnum<FileFlagEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 验证码
     */
    CAPTCHA(1,  "captcha"),
    ;


    private final Integer value;

    private final String desc;

    FileFlagEnum(Integer value, String desc) {
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
