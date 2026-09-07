package com.platform.mesh.gen.biz.modules.code.temp.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "模板表")
public class CodeTemplateVO extends BaseVO {

	@Schema(description = "ID")
	private Long id;

	/**
	 * 模板名称
	 */
	@Schema(description = "模板名称")
	private String templateName;

	/**
	 * 模板描述
	 */
	@Schema(description = "模板描述")
	private String templateDesc;

	/**
	 * 模板代码
	 */
	@Schema(description = "模板代码")
	private String templateCode;

	/**
	 * 模板路径
	 */
	@Schema(description = "模板路径")
	private String buildPath;

	/**
	 * 删除标记
	 */
	@Schema(description = "删除标记")
	private Integer delFlag;

	/**
	 * 创建人ID
	 */
	@Schema(description = "创建人ID")
	private Long createUserId;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改人ID
	 */
	@Schema(description = "修改人ID")
	private Long updateUserId;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

}
