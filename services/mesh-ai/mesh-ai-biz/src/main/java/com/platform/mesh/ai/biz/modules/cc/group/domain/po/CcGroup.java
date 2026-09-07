package com.platform.mesh.ai.biz.modules.cc.group.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 会话群PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "cc_group", autoResultMap = true)
public class CcGroup extends BasePO {

    /**
     * ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

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
     * 回复类型
     */
    private Integer replyType;

    /**
     * 会话状态
     */
    private Integer status;

    /**
     * 访客来源
     */
    private String source;

    /**
     * 来源页面
     */
    private String sourcePage;

    /**
     * 访客名称
     */
    private String visitorName;

    /**
     * 访客联系方式
     */
    private String visitorContact;

    /**
     * 访客公司
     */
    private String visitorCompany;

    /**
     * 访客需求
     */
    private String visitorDemand;

    /**
     * 首次响应时间
     */
    private LocalDateTime firstResponseAt;

    /**
     * 最后一条消息时间
     */
    private LocalDateTime lastMsgAt;

    /**
     * 关闭时间
     */
    private LocalDateTime closedAt;

    /**
     * 当前接待客服
     */
    private String assigneeUserHash;

    /**
     * 会话标签
     */
    private String tags;

    /**
     * 会话摘要
     */
    private String summary;

    /**
     * 线索状态：0未创建，1待跟进，2已跟进
     */
    private Integer leadStatus;

    /**
     * CRM线索ID
     */
    private Long leadId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
