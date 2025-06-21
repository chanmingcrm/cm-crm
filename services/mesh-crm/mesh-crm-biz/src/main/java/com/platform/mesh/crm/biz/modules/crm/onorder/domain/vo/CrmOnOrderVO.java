package com.platform.mesh.crm.biz.modules.crm.onorder.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 客户关系订单VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客户关系订单VO")
public class CrmOnOrderVO extends AppVO {

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private Long customerId;

    /**
     * 商机ID
     */
    @Schema(description = "商机ID")
    private Long businessId;

    /**
     * 总计金额
     */
    @Schema(description = "总计金额")
    private BigDecimal totalMoney;

    /**
     * 折扣金额
     */
    @Schema(description = "折扣金额")
    private BigDecimal discountMoney;

    /**
     * 实际金额
     */
    @Schema(description = "实际金额")
    private BigDecimal realMoney;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
}