package com.platform.mesh.crm.biz.modules.plm.design.process.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.mybatis.plus.annotation.TableParentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 工序设计数据
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "plm_design_process", autoResultMap = true)
public class PlmProcessDesign extends AppPO {

    /**
     * 父ID
     */
    @TableParentId(value = "parent_id")
    private Long parentId;

}
