package com.platform.mesh.upms.biz.modules.org.post.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 成员-用户DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="岗位VO")
public class OrgPostSimpVO extends BaseVO {

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

}
