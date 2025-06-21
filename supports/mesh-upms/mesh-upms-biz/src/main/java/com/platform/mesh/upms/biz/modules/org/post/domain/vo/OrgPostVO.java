package com.platform.mesh.upms.biz.modules.org.post.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.upms.biz.modules.org.postdatascope.domain.vo.OrgPostDataScopeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 成员-用户DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="岗位VO")
public class OrgPostVO extends BaseVO {

    /**
    * 职位ID
    */
    @Schema(description = "职位ID")
    private Long id;

    /**
     * 职位名称
     */
    @Schema(description = "职位名称")
    private String postName;

    /**
    * 层级ID
    */
    @Schema(description = "层级ID")
    private Long levelId;

    /**
    * 层级名称
    */
    @Schema(description = "层级名称")
    private String levelName;

    /**
    * 层级岗位关系ID
    */
    @Schema(description = "层级岗位关系ID")
    private Long levelPostRelId;

    /**
    * 岗位数据权限
    */
    @Schema(description = "岗位数据权限")
    private List<OrgPostDataScopeVO> postDataScopeVOS;


}
