package com.platform.mesh.bpm.biz.modules.temp.process.domain.dto;


import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 流程过程信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程过程信息DTO")
public class BpmTempProcessPageDTO extends PageDTO {

    /**
     * 分组ID
     */
    @Schema(description = "分组ID")
    private Long groupId;

    /**
     * 分组ID
     */
    @Schema(description = "分组ID",hidden = true)
    private List<Long> groupIds;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 流程Hash
     */
    @Schema(description = "流程Hash")
    private String processHash;

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    private String processName;

    /**
     * 版本
     */
    @Schema(description = "流程版本")
    private String processVersion;
}
