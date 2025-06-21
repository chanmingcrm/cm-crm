package com.platform.mesh.upms.biz.modules.msg.leave.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 留言消息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="留言消息VO")
public class MsgLeaveVO extends BaseVO {


    /**
     * 邮箱账号
     */
    @Schema(description = "邮箱账号")
    private String email;


    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;


    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;


    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String company;


    /**
     * 访问代理
     */
    @Schema(description = "访问代理")
    private String ipAgent;


    /**
     * ip地址
     */
    @Schema(description = "ip地址")
    private String ipAddr;


    /**
     * 留言次数
     */
    @Schema(description = "留言次数")
    private Integer leaveNum;


    /**
     * 分配标识
     */
    @Schema(description = "分配标识")
    private Integer divideFlag;

    /**
     * 分配人员ID
     */
    @Schema(description = "分配人员ID")
    private Long divideUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;
}