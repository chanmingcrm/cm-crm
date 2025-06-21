package com.platform.mesh.upms.biz.modules.sys.user.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description sys_user实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_user", autoResultMap = true)
public class SysUser extends BasePO {

	/**
	 * 用户ID
	 */
	@TableId(value = "user_id",type = IdType.ASSIGN_ID)
	private Long userId;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户头像
	 */
	private String avatar;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 身份证唯一编号
	 */
	private String idCard;

	/**
	 * 用户性别
	 */
	private Integer gender;

	/**
	 * 管理员状态(AdminFlagEnum)
	 */
	private Integer adminFlag;


	/**
	 * 帐号状态（0正常 1停用）
	 */
	private Integer userFlag;

	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	private Integer delFlag;

	/**
	 * 最后登陆IP
	 */
	private String loginIp;

	/**
	 * 最后登录时间
	 */
	private LocalDateTime loginDate;

	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long createUserId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUserId;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 备注
	 */
	private String remark;
}