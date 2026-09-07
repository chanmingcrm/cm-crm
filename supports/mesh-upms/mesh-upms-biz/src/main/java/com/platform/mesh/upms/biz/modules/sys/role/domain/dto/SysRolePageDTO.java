package com.platform.mesh.upms.biz.modules.sys.role.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 系统角色表DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="系统角色表")
public class SysRolePageDTO extends PageDTO {

    /**
     * 角色名
     */
    @Schema(description = "角色名")
    private String roleName;

}
