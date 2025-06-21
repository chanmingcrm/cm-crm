package com.platform.mesh.tmp.biz.modules.task.status.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务状态DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务状态DTO")
public class TaskStatusDTO extends BaseDTO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long dataId;


    /**
     * 状态标识
     */
    @Schema(description = "状态标识")
    private String statusMac;


    /**
     * 状态名称
     */
    @Schema(description = "状态名称")
    private String statusName;


    /**
     * 状态排序
     */
    @Schema(description = "状态排序")
    private Integer statusSort;


    /**
     * 初始标识
     */
    @Schema(description = "初始标识")
    private Integer iniFlag;


}