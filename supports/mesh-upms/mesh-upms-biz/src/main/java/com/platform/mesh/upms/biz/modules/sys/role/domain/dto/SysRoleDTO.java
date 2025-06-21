package com.platform.mesh.upms.biz.modules.sys.role.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 系统角色表(SysRole)DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="系统角色表")
public class SysRoleDTO extends BaseDTO {

    /**
    * 角色自增ID
    */
    @Schema(description = "角色自增ID")
    private Long id;
    /**
    * 角色名
    */
    @Schema(description = "角色名")
    private String roleName;
    /**
    * 排序
    */
    @Schema(description = "排序")
    private Integer sort;
}
