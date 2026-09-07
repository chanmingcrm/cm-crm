package com.platform.mesh.utils.excel.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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
     * 组件关联
     */
    @Schema(description = "组件关联")
    private String compRel;

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

    /**
     * 空值标识
     */
    @Schema(description = "空值标识")
    private Integer emptyFlag = YesOrNoEnum.NO.getValue();

    /**
     * 数据校验
     */
    @Schema(description = "数据校验")
    private List<String> headValid;


}