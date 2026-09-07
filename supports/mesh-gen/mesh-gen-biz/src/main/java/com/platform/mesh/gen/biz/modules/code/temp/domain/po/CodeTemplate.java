package com.platform.mesh.gen.biz.modules.code.temp.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 代码生成模板数据库对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("code_template")
public class CodeTemplate extends BasePO {

	@TableId( type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 模板描述
	 */
	private String templateDesc;

	/**
	 * 模板代码
	 */
	private String templateCode;

	/**
	 * 模板路径
	 */
	private String buildPath;

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
