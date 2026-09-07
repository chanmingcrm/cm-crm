package com.platform.mesh.ai.biz.modules.cc.userworkrel.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 人员排班PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "cc_user_work_rel", autoResultMap = true)
public class CcUserWorkRel extends BasePO {

    /**
     * ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 客服人员ID
     */
    private Long ccUserId;

    /**
     * 人员Hash
     */
    private String userHash;

    /**
     * 人员类型
     */
    private Integer userType;

    /**
     * 排班ID
     */
    private Long ccWorkId;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
