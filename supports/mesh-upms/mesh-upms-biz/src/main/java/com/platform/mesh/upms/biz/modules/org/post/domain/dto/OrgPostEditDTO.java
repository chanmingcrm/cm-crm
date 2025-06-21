package com.platform.mesh.upms.biz.modules.org.post.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 岗位DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="岗位DTO")
public class OrgPostEditDTO extends OrgPostAddDTO {

    /**
    * 岗位ID
    */
    @Schema(description = "岗位ID")
    private Long postId;
}
