package com.platform.mesh.ai.biz.modules.cc.setword.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 提示语PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "cc_set_word", autoResultMap = true)
public class CcSetWord extends BasePO {

    /**
     * ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 提示语类型
     */
    private Integer wordFlag;

    /**
     * 提示语频率
     */
    private Integer wordRate;

    /**
     * 提示语间隔
     */
    private Integer wordInterval;

    /**
     * 提示语规则
     */
    private Integer wordRule;

    /**
     * 提示语内容
     */
    private String wordContent;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
