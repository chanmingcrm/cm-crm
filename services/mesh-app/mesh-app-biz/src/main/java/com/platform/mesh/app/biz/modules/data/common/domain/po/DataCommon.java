package com.platform.mesh.app.biz.modules.data.common.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 通用数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "data_common", autoResultMap = true)
public class DataCommon extends AppPO {

    /**
     * 最新的流程实例ID
     */
    private Long instProcessId;

    /**
     * 最新的流程实例审批状态
     */
    private Integer processPass;

}