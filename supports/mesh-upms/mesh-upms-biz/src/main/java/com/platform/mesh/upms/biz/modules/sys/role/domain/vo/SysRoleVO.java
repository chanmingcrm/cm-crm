package com.platform.mesh.upms.biz.modules.sys.role.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

                        
/**
 * @description 系统角色表(SysRole)VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="系统角色VO")
public class SysRoleVO extends BaseVO {

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
    * 初始化标识
    */
    @SchemaEnum(value = YesOrNoEnum.class, description = "初始化标识")
    private Integer initFlag;
    /**
    * 排序
    */
    @Schema(description = "排序")
    private Integer sort;
    /**
    * 创建时间
    */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    /**
    * 创建人
    */
    @Schema(description = "创建人")
    private Long createUserId;
}
