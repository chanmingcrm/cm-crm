package com.platform.mesh.upms.biz.modules.log.update.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.LoginTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 更新日志)DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="更新日志")
public class LogUpdatePageDTO extends PageDTO {

    /**
     * 日志类型
     */
    @SchemaEnum(value = LoginTypeEnum.class, description = "日志类型")
    private Integer logFlag;

}
