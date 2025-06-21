package com.platform.mesh.crm.biz.modules.bi.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description BI统计BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="BI统计BO")
public class BiTimeBO extends BaseBO {


    /**
     * 时间维度
     */
    @Schema(description = "时间维度")
    private LocalDateTime dateTime;

    /**
     * 数值
     */
    @Schema(description = "数值")
    private BigDecimal value;

    /**
     * 扩展数据
     */
    @Schema(description = "扩展数据")
    private Object extend;

}