package com.platform.mesh.upms.biz.modules.msg.base.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 消息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "msg_base", autoResultMap = true)
public class MsgBase extends BasePO {


    /**
    * 
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 模块名称
    */
    private String moduleName;


    /**
    * 数据ID
    */
    private Long dataId;


    /**
    * 消息标识
    */
    private Integer msgFlag;


    /**
    * 消息类型
    */
    private Integer msgType;


    /**
     * 消息标题
     */
    private String msgTitle;


    /**
    * 消息主体
    */
    private String msgBody;


    /**
    * 消息外链
    */
    private String msgHref;


    /**
    * 提醒时间
    */
    private LocalDateTime noticeTime;


    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}