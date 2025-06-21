package com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 单字段排序VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="单字段排序VO")
public class AppFormColumnSortingVO extends BaseVO {


    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long formId;

    /**
     * 父字段ID
     */
    @Schema(description = "父字段ID")
    private Long parentColumnId;

    /**
     * 字段ID
     */
    @Schema(description = "字段ID")
    private Long columnId;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String columnName;

    /**
     * 横坐标
     */
    @Schema(description = "横坐标")
    private Integer xAddr;

    /**
     * 纵坐标
     */
    @Schema(description = "纵坐标")
    private Integer yAddr;

}