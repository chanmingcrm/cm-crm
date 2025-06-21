package com.platform.mesh.upms.biz.modules.sys.account.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description sys_account实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_account", autoResultMap = true)
public class SysAccount implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 账户ID
	 */
	@TableId(value = "account_id",type = IdType.ASSIGN_ID)
	private Long accountId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 账号码
	 */
	private String accountCode;

	/**
	 * 校验码
	 */
	private String checkCode;

	/**
	 * 账户昵称
	 */
	private String nickName;

	/**
	 * 用户头像
	 */
	private String avatar;

	/**
	 * 用户类型
	 */
	private Integer sourceFlag;

	/**
	 * 帐号状态（0正常 1停用）
	 */
	private Integer accountFlag;

	/**
	 * 预览标识（0正常 1预览）
	 */
	private Integer previewFlag;

	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	private Integer delFlag;

	/**
	 * 最后登陆IP
	 */
	private String loginIp;

	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	private LocalDateTime loginTime;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

	/**
	 * 账户所属当前根组织：用于冗余公司类型ID
	 */
	private Long scopeRootId;

	/**
	 * 账户所属当前组织
	 */
	private Long scopeOrgId;

}