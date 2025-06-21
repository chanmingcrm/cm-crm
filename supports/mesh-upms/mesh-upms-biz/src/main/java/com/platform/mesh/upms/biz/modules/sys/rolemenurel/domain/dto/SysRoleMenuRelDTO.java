package com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 角色菜单关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "角色菜单关系DTO")
public class SysRoleMenuRelDTO extends BaseDTO {

    /**
    * 角色ID
    */
    @Schema(description = "角色ID")
    private Long roleId;

    /**
    * 根菜单ID
    */
    @Schema(description = "根菜单ID")
    private Long rootMenuId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private List<Long> menuIds;


}

