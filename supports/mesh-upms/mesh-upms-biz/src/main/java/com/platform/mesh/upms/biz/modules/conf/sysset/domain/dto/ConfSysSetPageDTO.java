package com.platform.mesh.upms.biz.modules.conf.sysset.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 配置系统DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="配置系统DTO")
public class ConfSysSetPageDTO extends PageDTO {

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


}