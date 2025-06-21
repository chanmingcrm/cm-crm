package com.platform.mesh.upms.biz.modules.msg.leave.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 留言消息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="留言消息DTO")
public class MsgLeaveDTO extends BaseDTO {


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

}