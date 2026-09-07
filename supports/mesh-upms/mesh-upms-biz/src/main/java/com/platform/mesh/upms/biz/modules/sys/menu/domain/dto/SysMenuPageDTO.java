package com.platform.mesh.upms.biz.modules.sys.menu.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 菜单页面列表查询请求对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "菜单分页信息")
public class SysMenuPageDTO extends PageDTO {

    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId;

    /**
     * 菜单类型(应用，菜单，页面，按钮)
     */
    @Schema(description = "菜单类型")
    private Integer menuType;

    /**
     * 命名路由
     */
    @Schema(description = "命名路由")
    private String name;

    /**
     * 是否需要子项
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否需要子项")
    private Integer needChild;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID",hidden = true)
    private List<Long> menuIds;

    /**
     * 是否管理员
     */
    @Schema(description = "是否管理员",hidden = true)
    private Boolean isAdmin;

    /**
     * 当前登录人员ID
     */
    @Schema(description = "当前登录人员ID",hidden = true)
    private Long userId;

    /**
     * 删除标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "删除标识",hidden = true)
    private Integer delFlag;

}
