package com.platform.mesh.upms.biz.modules.org.member.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
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
     * 成员名称
     */
    @Schema(description = "成员名称")
    private String memberName;
}
