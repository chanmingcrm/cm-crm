package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @description 订阅同步数据BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="订阅同步数据BO")
public class SyncDataBO extends BaseBO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

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
     * 字段信息:字段column_mac
     */
    @Schema(description = "字段信息")
    private List<String> filedList;
}