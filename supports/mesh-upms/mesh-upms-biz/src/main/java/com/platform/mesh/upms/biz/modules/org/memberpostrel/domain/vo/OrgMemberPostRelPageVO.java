package com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
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
@Schema(description="成员-岗位VO")
public class OrgMemberPostRelPageVO extends BaseVO {

    /**
     * 主键ID
     */
    @Schema(description="主键ID")
    private Long id;

    /**
     * 层级ID
     */
    @Schema(description="层级ID")
    private Long levelId;

    /**
     * 层级名称
     */
    @Schema(description="层级名称")
    private String levelName;

    /**
    * 成员ID
    */
    @Schema(description="成员ID")
    private Long memberId;

    /**
     * 成员名称
     */
    @Schema(description="成员名称")
    private String memberName;

    /**
    * 岗位ID
    */
    @Schema(description="岗位ID")
    private Long postId;

    /**
     * 岗位名称
     */
    @Schema(description="岗位名称")
    private String postName;


}

