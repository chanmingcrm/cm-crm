package com.platform.mesh.gen.biz.modules.code.field.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("code_field_mapping")
public class CodeFieldMapping extends BasePO {

	@TableId( type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 属性包名
	 */
	private String packageName;

	/**
	 * 字段类型
	 */
	private String columnFlag;

	/**
	 * 属性类型
	 */
	private String fieldFlag;

	/**
	 * 删除标记
	 */
	private Integer delFlag;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
}
