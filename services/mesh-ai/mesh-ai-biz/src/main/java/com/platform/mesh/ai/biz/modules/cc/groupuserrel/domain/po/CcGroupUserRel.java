package com.platform.mesh.ai.biz.modules.cc.groupuserrel.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 会话人员关系PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "cc_group_user_rel", autoResultMap = true)
public class CcGroupUserRel extends BasePO {

    /**
     * ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 群Id
     */
    private Long groupId;

    /**
     * 群Hash
     */
    private String groupHash;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群类型
     */
    private Integer groupType;

    /**
     * 人员Hash
     */
    private String userHash;

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 人员类型
     */
    private Integer userType;

    /**
     * 服务星级
     */
    private Integer starLevel;

    /**
     * 上次查看时间
     */
    private LocalDateTime lastVisitTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
