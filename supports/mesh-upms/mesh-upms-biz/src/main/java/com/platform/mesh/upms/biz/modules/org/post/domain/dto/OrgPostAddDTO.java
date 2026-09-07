package com.platform.mesh.upms.biz.modules.org.post.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.dto.OrgPostDataScopeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 岗位DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="岗位DTO")
public class OrgPostAddDTO extends BaseDTO {

    /**
    * 层级ID
    */
    @Schema(description = "层级ID")
    private Long levelId;


    /**
    * 职位名称
    */
    @Schema(description = "职位名称")
    private String postName;


    /**
     * 决策岗位
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "决策岗位")
    private Integer leadFlag = YesOrNoEnum.NO.getValue();


    /**
    * 职位数据权限
    */
    @Schema(description = "职位数据权限")
    private List<OrgPostDataScopeDTO> postDataScopes;
}
