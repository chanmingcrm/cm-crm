package com.platform.mesh.tmp.biz.modules.task.post.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务工职VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务工职VO")
public class TaskPostVO extends BaseVO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 主导标识
     */
    @Schema(description = "主导标识")
    private Integer masterFlag;


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