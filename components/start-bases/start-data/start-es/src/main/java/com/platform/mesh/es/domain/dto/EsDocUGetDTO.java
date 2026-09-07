package com.platform.mesh.es.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @description 联合查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="联合查询DTO")
public class EsDocUGetDTO extends PageDTO {

    /**
     * 搜索值
     */
    @Schema(description = "搜索值")
    private String searchValue;

    /**
     * 索引查询字段名称
     */
    @Schema(description = "索引查询字段名称")
    private Map<Long, List<Long>> idFieldMap;

    /**
     * 索引查询字段名称
     */
    @Schema(description = "索引查询字段名称")
    private Map<String, List<String>> indexFieldMap;

}