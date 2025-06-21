package com.platform.mesh.upms.api.modules.org.member.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 组织BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "组织BO")
public class OrgLevelBO extends BaseBO {

	/**
	 * 组织ID
	 */
	@Schema(description = "组织ID")
	private Long id;

	/**
	 * 组织名称
	 */
	@Schema(description = "组织名称")
	private String levelName;
}
