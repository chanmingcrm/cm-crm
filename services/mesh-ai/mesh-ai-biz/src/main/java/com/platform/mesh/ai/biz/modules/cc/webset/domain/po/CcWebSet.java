package com.platform.mesh.ai.biz.modules.cc.webset.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 页面配置PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "cc_web_set", autoResultMap = true)
public class CcWebSet extends BasePO {

    /**
     * ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 页面描述
     */
    private String webDesc;

    /**
     * 页面脚本
     */
    private String webScript;

    /**
     * 官网咨询引导配置JSON
     */
    private String consultationGuide;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
