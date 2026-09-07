package com.platform.mesh.upms.biz.modules.org.member.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 成员DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="成员DTO")
public class OrgMemberPageDTO extends PageDTO {

    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private List<Long> levelIds;

    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private List<Long> postIds;

    /**
     * 人员ID
     */
    @Schema(description = "人员ID")
    private List<Long> userIds;

    /**
     * 快捷查询
     */
    @Schema(description = "快捷查询")
    private String searchValue;

    /**
     * 权限查询
     */
    @SchemaEnum(value = DataScopeEnum.class, description = "权限查询")
    private Integer dataScope = DataScopeEnum.CUSTOM.getValue();

    /**
     * 数据标识
     */
    @SchemaEnum(value = DataFlagEnum.class, description = "数据标识")
    private Integer dataFlag;

    /**
     * 数据ID
     */
    @Schema( description = "数据ID")
    private List<Long> dataIds;

}
