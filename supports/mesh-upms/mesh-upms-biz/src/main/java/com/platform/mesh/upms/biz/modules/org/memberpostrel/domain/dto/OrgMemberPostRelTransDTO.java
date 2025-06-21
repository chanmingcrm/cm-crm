package com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 成员-岗位DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="成员-岗位DTO")
public class OrgMemberPostRelTransDTO extends BaseDTO {

    /**
    * 成员ID
    */
    @Schema(description="成员ID")
    private List<Long> memberIds;

    /**
    * 源岗位ID
    */
    @Schema(description="源岗位ID")
    private Long sourceId;

    /**
    * 目标岗位ID
    */
    @Schema(description="目标岗位ID")
    private Long targetId;

}

