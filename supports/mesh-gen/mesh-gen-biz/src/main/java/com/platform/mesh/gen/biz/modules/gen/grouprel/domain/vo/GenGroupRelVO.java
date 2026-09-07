package com.platform.mesh.gen.biz.modules.gen.grouprel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.enums.GenGroupRelEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块分组关联VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块分组关联VO")
public class GenGroupRelVO extends BaseVO {


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
     * 分组名称
     */
    @Schema(description = "分组名称")
    private String groupName;


    /**
     * 分组类型GroupTypeEnum
     */
    @SchemaEnum(value = GenGroupRelEnum.class, description = "分组类型")
    private Integer groupType;


    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 数据名称
     */
    @Schema(description = "数据名称")
    private String dataName;


}