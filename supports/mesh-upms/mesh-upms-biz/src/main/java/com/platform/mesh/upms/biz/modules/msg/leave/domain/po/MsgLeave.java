package com.platform.mesh.upms.biz.modules.msg.leave.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 留言消息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "msg_leave", autoResultMap = true)
public class MsgLeave extends BasePO {


    /**
    * 
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 邮箱账号
    */
    private String email;


    /**
    * 手机号码
    */
    private String phone;


    /**
    * 姓名
    */
    private String name;


    /**
    * 公司名称
    */
    private String company;


    /**
     * 访问代理
     */
    private String ipAgent;


    /**
    * ip地址
    */
    private String ipAddr;


    /**
     * 留言次数
     */
    private Integer leaveNum;


    /**
     * 分配标识
     */
    private Integer divideFlag;

    /**
     * 分配人员ID
     */
    private Long divideUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}