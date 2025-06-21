package com.platform.mesh.tmp.biz.modules.bi.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description BI统计VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="BI统计VO")
public class BiSimpVO extends BaseVO {


    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 数值
     */
    @Schema(description = "数值")
    private Object value;

    /**
     * 扩展值
     */
    @Schema(description = "扩展值")
    private Object extend;

}