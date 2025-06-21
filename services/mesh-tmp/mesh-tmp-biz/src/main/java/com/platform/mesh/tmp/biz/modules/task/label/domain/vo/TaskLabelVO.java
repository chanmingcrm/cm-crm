package com.platform.mesh.tmp.biz.modules.task.label.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务标签VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="任务标签VO")
public class TaskLabelVO extends BaseVO {



    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;


    /**
     * 标签标识
     */
    @Schema(description = "标签标识")
    private String labelMac;


    /**
     * 标签名称
     */
    @Schema(description = "标签名称")
    private String labelName;


    /**
     * 标签排序
     */
    @Schema(description = "标签排序")
    private Integer labelSort;


    /**
     * 标签颜色
     */
    @Schema(description = "标签颜色")
    private String labelColor;


}