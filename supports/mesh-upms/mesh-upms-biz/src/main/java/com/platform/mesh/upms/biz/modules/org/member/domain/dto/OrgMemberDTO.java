package com.platform.mesh.upms.biz.modules.org.member.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 成员DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="成员DTO")
public class OrgMemberDTO extends BaseDTO {

    /**
    * 成员ID
    */
    @Schema(description = "成员ID")
    private Long id;
    /**
    * 成员名称
    */
    @Schema(description = "成员名称")
    private String memberName;
}
