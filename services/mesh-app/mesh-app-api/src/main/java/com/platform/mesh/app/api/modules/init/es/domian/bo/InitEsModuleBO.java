package com.platform.mesh.app.api.modules.init.es.domian.bo;

import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 初始化Es BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="初始化Es BO")
public class InitEsModuleBO extends BaseBO {

    /**
     * 是否忽略权限隔离,默认不忽略
     */
    @Schema(description = "是否忽略权限隔离")
    private Boolean ignoreScope = Boolean.FALSE;

    /**
     * 父主键ID
     */
    @Schema(description = "父主键ID")
    private AppModuleBaseBO moduleBaseBO;

}