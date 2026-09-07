package com.platform.mesh.upms.biz.modules.org.member.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 成员删除DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="成员删除DTO")
public class OrgMemberDelDTO extends BaseDTO {

    /**
    * 成员ID
    */
    @Schema(description = "成员ID")
    private Long memberId;
    /**
    * 组织ID
    */
    @Schema(description = "组织ID")
    private Long levelId;
    /**
    * 是否强制删除
    */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否强制删除")
    private Integer forceFlag;
}
