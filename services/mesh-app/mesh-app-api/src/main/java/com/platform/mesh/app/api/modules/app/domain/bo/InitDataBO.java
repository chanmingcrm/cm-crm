package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 初始化数据
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="初始化数据")
public class InitDataBO extends BaseBO {


    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Long current;

    /**
     * 模块Schema
     */
    @Schema(description = "模块Schema")
    private String moduleSchema;

    /**
     * 模块索引
     */
    @Schema(description = "模块索引")
    private String moduleIndex;

    /**
     * 是否忽略权限隔离,默认不忽略
     */
    @Schema(description = "是否忽略权限隔离")
    private Boolean ignoreScope = Boolean.FALSE;

}