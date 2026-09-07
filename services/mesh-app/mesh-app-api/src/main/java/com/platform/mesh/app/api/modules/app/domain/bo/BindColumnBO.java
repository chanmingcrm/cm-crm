package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 字段设定绑定BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="字段设定绑定BO")
public class BindColumnBO extends BaseBO {

    /**
     * 绑定参数
     */
    @Schema(description = "绑定参数")
    private BindParamsBO bindParams;

    /**
     * 替换参数
     */
    @Schema(description = "替换参数")
    private List<String> replaceList;

}