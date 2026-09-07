package com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 人员排班DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="人员排班分页DTO")
public class CcUserWorkRelPageDTO extends PageDTO {

    /**
     * 客服人员名称
     */
    @Schema(description = "客服人员名称")
    private String ccUserName;


    /**
     * 排班ID
     */
    @Schema(description = "排班ID")
    private Long ccWorkId;


}
