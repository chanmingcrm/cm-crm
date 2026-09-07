package com.platform.mesh.upms.biz.modules.team.link.domain.vo;


import com.platform.mesh.core.application.domain.vo.TreeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 团队数据关系信息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="团队VO")
public class TeamLinkVO extends TreeVO<TeamLinkVO> {

    /**
     * 团队ID
     */
    @Schema(description="团队ID")
    private Long teamId;

}
