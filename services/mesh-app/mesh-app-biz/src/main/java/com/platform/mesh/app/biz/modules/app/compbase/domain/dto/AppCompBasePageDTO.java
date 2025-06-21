package com.platform.mesh.app.biz.modules.app.compbase.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 页面组件DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="页面组件DTO")
public class AppCompBasePageDTO extends PageDTO {

    /**
     * 字段标识ColumnFlagEnum
     */
    @Schema(description = "字段标识ColumnFlagEnum")
    private Integer columnFlag;


}