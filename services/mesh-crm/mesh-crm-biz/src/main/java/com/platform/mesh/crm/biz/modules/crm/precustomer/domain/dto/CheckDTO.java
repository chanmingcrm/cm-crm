package com.platform.mesh.crm.biz.modules.crm.precustomer.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 查重DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="查重DTO")
public class CheckDTO extends BaseDTO {


    /**
     * 类型
     */
    @Schema(description = "类型")
    private Integer type;

    /**
     * 值
     */
    @Schema(description = "值")
    private String checkValue;

}