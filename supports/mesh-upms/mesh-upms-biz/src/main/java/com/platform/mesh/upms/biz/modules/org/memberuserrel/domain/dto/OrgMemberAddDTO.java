package com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
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
@Schema(description="成员DTO")
public class OrgMemberAddDTO extends BaseDTO {

    /**
     * 根组织ID
     */
    @Schema(description = "根组织ID")
    private Long levelRootId;


    /**
     * 组织ID
     */
    @Schema(description = "组织ID")
    private Long levelId;


    /**
     * 成员名称
     */
    @Schema(description = "成员名称")
    private Long postId;


    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private List<Long> userIds;

}
