package com.platform.mesh.app.biz.modules.app.modulesettrans.domain.vo;

import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleBaseVO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化设置VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化设置VO")
public class AppModuleSetTransVO extends BaseVO {


    /**
     * id
     */
    @Schema(description = "id")
    private Long id;


    /**
     * 模块来源ID
     */
    @Schema(description = "模块来源")
    private AppModuleBaseVO moduleFrom;


    /**
     * 模块目标
     */
    @Schema(description = "模块目标")
    private AppModuleBaseVO moduleTo;


    /**
     * 是否删除来源数据
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否删除来源数据")
    private Integer delFrom;

}