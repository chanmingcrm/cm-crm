package com.platform.mesh.gen.biz.modules.gen.grouprel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.enums.GenGroupRelEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 模块分组关联DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块分组关联DTO")
public class GenGroupRelDTO extends BaseDTO {


    /**
     * 分组ID
     */
    @Schema(description = "分组ID")
    private Long groupId;


    /**
     * 分组类型GroupTypeEnum
     */
    @SchemaEnum(value = GenGroupRelEnum.class, description = "分组类型")
    private Integer groupType;


    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private List<Long> dataIds;


}