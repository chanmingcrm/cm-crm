package com.platform.mesh.crm.biz.modules.crm.precustomer.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description 摘要VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="摘要VO")
public class AbatractVO extends BaseVO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 跟进次数
     */
    @Schema(description = "跟进次数")
    private Long followNum;

    /**
     * 未跟进时长
     */
    @Schema(description = "未跟进时长")
    private Long unfollowDays;

    /**
     * 商机数量
     */
    @Schema(description = "商机数量")
    private Long businessNum;

    /**
     * 商机金额
     */
    @Schema(description = "商机金额")
    private BigDecimal businessMoney;

    /**
     * 成交次数
     */
    @Schema(description = "成交次数")
    private Long contractNum;

    /**
     * 成交金额
     */
    @Schema(description = "成交金额")
    private BigDecimal contractMoney;

    /**
     * 回款金额
     */
    @Schema(description = "回款金额")
    private BigDecimal receivedMoney;

    /**
     * 未回款金额
     */
    @Schema(description = "未回款金额")
    private BigDecimal unReceivedMoney;

    /**
     * 开票金额
     */
    @Schema(description = "开票金额")
    private BigDecimal invoiceMoney;

}