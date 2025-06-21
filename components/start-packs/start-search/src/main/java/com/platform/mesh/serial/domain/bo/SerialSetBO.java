package com.platform.mesh.serial.domain.bo;

import com.platform.mesh.serial.enums.SerialTypeEnum;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 序列号BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="序列号BO")
public class SerialSetBO extends BaseBO {

    //数据类型
    @SchemaEnum(value = SerialTypeEnum.class, description ="数据类型")
    private Integer type;

    @Schema(description ="数据值")
    private String origin;

    @Schema(description ="数据转换条件")
    private String format;

    @Schema(description ="数据转换规则")
    private String rule;

}