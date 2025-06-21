package com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 系统角色菜单VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="系统角色菜单VO")
public class SysRoleMenuRelVO extends BaseVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;
    /**
    * 角色ID
    */
    @Schema(description = "角色ID")
    private Long roleId;
    /**
    * 角色名
    */
    @Schema(description = "角色名")
    private String roleName;
    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long menuId;
    /**
     * 菜单名
     */
    @Schema(description = "菜单名")
    private String menuName;

}
