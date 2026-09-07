package com.platform.mesh.upms.biz.modules.sys.account.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "账户分页DTO")
public class AccountPageDTO extends PageDTO {

	/**
	 * 关键词搜索
	 */
	@Schema(description = "关键词搜索")
	private String searchValue;

	/**
	 * 删除标识
	 */
	@Schema(description = "删除标识",hidden = true)
	private Integer delFlag;

}