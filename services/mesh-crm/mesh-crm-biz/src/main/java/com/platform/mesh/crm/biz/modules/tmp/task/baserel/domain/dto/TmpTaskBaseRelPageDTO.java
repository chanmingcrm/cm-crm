package com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务数据关联分页DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务数据关联分页DTO")
public class TmpTaskBaseRelPageDTO extends PageDTO {


    /**
     * 关联模块ID
     */
    @Schema(description = "关联模块ID")
    private Long relModuleId;


    /**
     * 关联数据ID
     */
    @Schema(description = "关联数据ID")
    private Long relDataId;

}