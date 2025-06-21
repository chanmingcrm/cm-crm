package com.platform.mesh.tmp.biz.modules.task.postrel.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务工职关系VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务工职关系VO")
public class TaskPostRelVO extends BaseVO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long taskId;


    /**
     * 人员ID
     */
    @Schema(description = "人员ID")
    private Long userId;


    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private Long postId;


    /**
     * 岗位标识
     */
    @Schema(description = "岗位标识")
    private String postMac;


    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    private String postName;


    /**
     * 岗位排序
     */
    @Schema(description = "岗位排序")
    private Integer postSort;


}