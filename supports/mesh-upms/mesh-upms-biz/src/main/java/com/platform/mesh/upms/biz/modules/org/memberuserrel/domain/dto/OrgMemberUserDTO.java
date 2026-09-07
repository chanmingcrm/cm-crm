package com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
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
@Schema(description="成员-岗位DTO")
public class OrgMemberUserDTO extends BaseDTO {

    /**
    * 用户ID
    */
    @Schema(description="用户ID")
    private Long userId;

    /**
    * 领导标识
    */
    @Schema(description="领导标识")
    private Integer leadFlag = YesOrNoEnum.NO.getValue();

    /**
    * 默认岗位标识
    */
    @Schema(description="默认岗位标识")
    private Integer defaultPostFlag = YesOrNoEnum.NO.getValue();

}

