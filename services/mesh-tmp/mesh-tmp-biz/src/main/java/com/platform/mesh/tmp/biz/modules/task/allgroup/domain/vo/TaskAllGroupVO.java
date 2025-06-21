package com.platform.mesh.tmp.biz.modules.task.allgroup.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务分组VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务分组VO")
public class TaskAllGroupVO extends BaseVO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 状态标识
     */
    @Schema(description = "状态标识")
    private String groupMac;


    /**
     * 状态名称
     */
    @Schema(description = "状态名称")
    private String groupName;


    /**
     * 状态排序
     */
    @Schema(description = "状态排序")
    private Integer groupSort;


}