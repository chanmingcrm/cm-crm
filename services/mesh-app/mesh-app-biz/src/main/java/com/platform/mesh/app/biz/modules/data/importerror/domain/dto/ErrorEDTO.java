package com.platform.mesh.app.biz.modules.data.importerror.domain.dto;

import com.platform.mesh.utils.excel.dto.HeadDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 导出错误DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="导出错误DTO")
public class ErrorEDTO extends ErrorPDTO {

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    String moduleName;

    /**
     * 导出表头
     */
    @Schema(description = "导出表头")
    List<HeadDTO> headDTOS;

}
