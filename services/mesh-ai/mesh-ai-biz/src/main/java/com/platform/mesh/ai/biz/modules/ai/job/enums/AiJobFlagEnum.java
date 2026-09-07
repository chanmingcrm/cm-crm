package com.platform.mesh.ai.biz.modules.ai.job.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 文件标识枚举
 * @author 蝉鸣
 */
@Schema(description = "Ai任务类型枚举",enumAsRef = true)
public enum AiJobFlagEnum implements BaseEnum<AiJobFlagEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 生成文章
     */
    GEN_ARTICLE(1,  "生成文章"),
    ;


    private final Integer value;

    private final String desc;

    AiJobFlagEnum(Integer value, String desc) {
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
