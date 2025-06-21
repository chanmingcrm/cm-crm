package com.platform.mesh.upms.biz.modules.org.post.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
/**
 * @description 岗位DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="岗位DTO")
public class OrgPostPageDTO extends PageDTO {

    /**
     * 层级ID
     */
    @Schema(description = "层级ID")
    private List<Long> levelIds;

    /**
     * 职位名称
     */
    @Schema(description = "职位名称")
    private String postName;

    /**
     * 是否需要子项
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否需要子项")
    private Integer needChild;
}
