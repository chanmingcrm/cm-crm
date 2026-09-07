package com.platform.mesh.gen.biz.modules.gen.grouprel.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 代码生成模板数据库对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("gen_all_group_rel")
public class GenGroupRel extends BasePO {

	@TableId( type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 分组ID
	 */
	private Long groupId;

	/**
	 * 分组类型
	 */
	private Integer groupType;

	/**
	 * 数据ID
	 */
	private Long dataId;

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
