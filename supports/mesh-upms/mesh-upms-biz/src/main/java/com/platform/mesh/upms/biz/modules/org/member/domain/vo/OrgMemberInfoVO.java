package com.platform.mesh.upms.biz.modules.org.member.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberPostRelPageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 成员VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="成员VO")
public class OrgMemberInfoVO extends BaseVO {

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
    * 关联信息
    */
    @Schema(description = "关联信息")
    private List<OrgMemberPostRelPageVO> relList;

}
