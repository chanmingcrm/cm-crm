package com.platform.mesh.crm.biz.modules.tmp.work.log.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.utils.format.TimeUnitEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 工作日志DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="工作日志DTO")
public class TmpWorkLogDTO extends BaseDTO {

    /**
     * 时间单位
     */
    @SchemaEnum(value = TimeUnitEnum.class, description = "时间单位")
    private Integer timeUnit;

}