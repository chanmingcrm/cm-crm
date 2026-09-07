package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 模块转化设置字段映射BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块转化设置人员领取BO")
public class UserPickBO extends BaseBO {

    /**
     * 转化设置ID
     */
    @Schema(description = "转化设置ID")
    private Long transId;

    /**
     * 人员ID
     */
    @Schema(description = "人员ID")
    private Long userId;


    /**
     * 分配参数值
     */
    @Schema(description = "分配参数值")
    private Integer pickNum;
}