package com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 组织成员层级VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="组织成员层级VO")
public class OrgMemberLevelVO extends BaseVO {


    /**
     * 顶层ID
     */
    @Schema(description="顶层ID")
    private Long rootId;

    /**
     * 顶层名称
     */
    @Schema(description="顶层名称")
    private String rootName;

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
}

