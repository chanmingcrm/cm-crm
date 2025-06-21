package com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto;


import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 成员-岗位DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="成员-岗位DTO")
public class OrgMemberPostRelPageDTO extends PageDTO {

    /**
    * 层级ID
    */
    @Schema(description="层级ID")
    private Long levelId;

    /**
    * 成员ID
    */
    @Schema(description="成员ID")
    private Long memberId;

    /**
    * 岗位ID
    */
    @Schema(description="岗位ID")
    private Long postId;

}

