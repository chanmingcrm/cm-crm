package com.platform.mesh.utils.excel.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 导出对象DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="导出对象DTO")
public class HeadDTO extends BaseDTO {


    /**
     * 组件类型
     */
    @Schema(description = "组件类型")
    private String compMac;

    /**
     * 列头标识
     */
    @Schema(description = "列头标识")
    private String headMac;

    /**
     * 数据名称
     */
    @Schema(description = "列头标识")
    private String headName;


}