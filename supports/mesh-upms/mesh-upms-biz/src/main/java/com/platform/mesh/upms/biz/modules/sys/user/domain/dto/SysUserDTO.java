package com.platform.mesh.upms.biz.modules.sys.user.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 用户信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户信息")
public class SysUserDTO extends BaseDTO {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "身份证唯一编号")
    private String idCard;

    @Schema(description = "用户性别")
    private Integer gender;

    @Schema(description = "帐号状态（0正常 1停用）")
    private Integer userFlag;

    @Schema(description = "角色")
    private List<Long> roleIds;

    @Schema(description = "岗位")
    private List<Long> postIds;

}
