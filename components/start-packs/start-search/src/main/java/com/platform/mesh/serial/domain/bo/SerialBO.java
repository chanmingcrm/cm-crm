package com.platform.mesh.serial.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
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
public class SerialBO extends BaseBO {

    @Schema(description ="序列值")
    private Integer dataSerial;

    @Schema(description ="标识值")
    private String dataMac;

}