package com.platform.mesh.upms.biz.modules.org.member.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @description 成员VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="成员VO")
public class OrgMemberVO extends BaseVO {

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

    /**
    * 人员ID
    */
    @Schema(description = "人员ID")
    private Long userId;

    /**
    * 层级ID
    */
    @Schema(description = "层级ID")
    private String levelId;

    /**
    * 层级名称
    */
    @Schema(description = "层级名称")
    private String levelName;

    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private String postId;

    /**
    * 岗位名称
    */
    @Schema(description = "岗位名称")
    private String postName;

}
