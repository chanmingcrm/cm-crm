package com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 成员-用户DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="成员-岗位DTO")
public class OrgMemberUserRelDTO extends BaseDTO {

    /**
    * 成员ID
    */
    @Schema(description="成员ID")
    private Long memberId;

    /**
    * 用户ID
    */
    @Schema(description="用户ID")
    private Long userId;

}

