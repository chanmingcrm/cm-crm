package com.platform.mesh.gen.biz.modules.code.build.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("code_build")
public class CodeBuild extends BasePO {

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 构造名称
	 */
	private String buildName;

	/**
	 * 删除标记
	 */
	private Integer delFlag;

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
