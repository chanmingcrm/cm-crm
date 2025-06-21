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
 * @description 成员BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "成员BO")
public class OrgMemberBO extends BaseBO {

	/**
	 * 成员ID
	 */
	@Schema(description = "成员ID")
	private Long id;

	/**
	 * 成员名称
	 */
	@Schema(description = "成员名称")
	private String memberName;

}
