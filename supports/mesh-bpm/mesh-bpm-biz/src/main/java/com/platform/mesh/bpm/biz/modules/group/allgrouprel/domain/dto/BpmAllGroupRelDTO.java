package com.platform.mesh.bpm.biz.modules.group.allgrouprel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块分组关联DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块分组关联DTO")
public class BpmAllGroupRelDTO extends BaseDTO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;


    /**
     * 分组ID
     */
    @Schema(description = "分组ID")
    private Long groupId;


    /**
     * 分组类型GroupTypeEnum
     */
    @Schema(description = "分组类型GroupTypeEnum")
    private Integer groupType;


    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;


}