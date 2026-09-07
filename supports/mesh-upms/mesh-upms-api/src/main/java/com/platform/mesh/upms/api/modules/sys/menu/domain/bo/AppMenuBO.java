package com.platform.mesh.upms.api.modules.sys.menu.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 应用菜单BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "App模块-菜单信息")
public class AppMenuBO extends BaseBO {

    @SchemaEnum(value = OperateTypeEnum.class,description= "操作类型")
    private Integer operateType;

    @Schema(description = "模块id")
    private Long moduleId;

    @Schema(description = "父级模块id")
    private Long parentModuleId;

    @Schema(description = "关联模块id")
    private Long relId;

    @SchemaEnum(value = MenuTypeEnum.class,description = "菜单类型")
    private Integer menuType;

    @Schema(description = "菜单名称")
    private String title;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "RouteItem Param信息")
    private List<RouteParamsBO> params;

}
