package com.platform.mesh.app.biz.modules.app.modulebase.domain.dto;

import com.platform.mesh.app.api.modules.app.enums.comp.ModuleTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 列表查询对象请求VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="列表查询对象请求VO")
public class AppModuleBasePageDTO extends PageDTO {


    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private Long appId;


    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId;


    /**
     * 模块类型ModuleTypeEnum
     */
    @SchemaEnum(value = ModuleTypeEnum.class,description = "模块类型ModuleTypeEnum")
    private List<Integer> moduleType;


    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String moduleName;


    /**
     * 删除标识
     */
    @SchemaEnum(value = YesOrNoEnum.class,description = "删除标识",hidden = true)
    private Integer delFlag;


    /**
     * AI标识
     */
    @SchemaEnum(value = YesOrNoEnum.class,description = "AI标识",hidden = true)
    private Integer aiFlag;
}
