package com.platform.mesh.upms.api.modules.conf.domian.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 字典基础BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="字典基础BO")
public class ConfSysSetBO extends BaseBO {


    /**
     * 配置来源
     */
    @SchemaEnum(value = ConfSourceEnum.class, description = "配置来源")
    private Integer confSource;

    /**
     * 配置组
     */
    @Schema(description = "配置组")
    private String confBatch;

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
    @Schema(description = "配置密文")
    private String confSecret;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}