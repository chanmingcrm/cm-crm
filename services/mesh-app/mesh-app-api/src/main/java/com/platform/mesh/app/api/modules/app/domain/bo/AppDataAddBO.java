package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @description 通用数据保存BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="通用数据保存BO")
public class AppDataAddBO extends BaseBO {

    /**
     * 模块信息
     */
    @Schema(description = "模块信息")
    private AppModuleBaseBO moduleBaseBO;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     * 实例对象
     */
    @Schema(description = "实例对象")
    private Object instObj;

    /**
     * 字段信息
     */
    @Schema(description = "字段信息")
    private List<AppFormColumnBO> columnBOS;

    /**
     * 数据
     */
    @Schema(description = "数据")
    private Map<String,Object> dataDoc;
}