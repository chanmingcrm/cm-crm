package com.platform.mesh.upms.biz.modules.org.postdatascope.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.biz.modules.org.postdatascope.enums.DataFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 岗位权限DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="岗位权限DTO")
public class OrgPostDataScopeDTO extends BaseDTO {

    /**
    * 职位ID
    */
    @Schema(description = "岗位ID",hidden = true)
    private Long postId;
    /**
     * 数据权限类型
     */
    @SchemaEnum(value = DataScopeEnum.class, description = "数据权限类型")
    private Integer dataScope;
    /**
     * 数据关联类型
     */
    @SchemaEnum(value = DataFlagEnum.class, description = "数据关联类型")
    private Integer dataFlag;
    /**
     * 数据关联ID
     */
    @Schema(description = "数据关联ID")
    private Long dataId;
    /**
     * 数据关联名称
     */
    @Schema(description = "数据关联名称")
    private String dataName;

}

