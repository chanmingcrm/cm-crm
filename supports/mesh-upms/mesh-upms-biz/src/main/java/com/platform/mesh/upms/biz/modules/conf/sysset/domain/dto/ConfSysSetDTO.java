package com.platform.mesh.upms.biz.modules.conf.sysset.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 配置系统DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="配置系统DTO")
public class ConfSysSetDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 配置来源
     */
    @SchemaEnum(value = ConfSourceEnum.class, description = "配置来源")
    private Integer confSource;

    /**
     * 配置识别
     */
    @Schema(description = "配置识别")
    private String confMac;

    /**
     * 配置名称
     */
    @Schema(description = "配置名称")
    private String confName;

    /**
     * 配置值
     */
    @Schema(description = "配置值")
    private Object confValue;

    /**
     * 配置描述
     */
    @Schema(description = "配置描述")
    private String confDesc;

    /**
     * 配置密文
     */
    @Schema(description = "创建者ID")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者ID
     */
    @Schema(description = "更新者ID")
    private Long updateUserId;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 数据权限用户ID
     */
    @Schema(description = "数据权限用户ID")
    private Long scopeUserId;

    /**
     * 数据权限机构ID
     */
    @Schema(description = "数据权限机构ID")
    private Long scopeOrgId;

}
