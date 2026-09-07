package com.platform.mesh.upms.api.modules.org.member.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
	 * 人员ID
	 */
	@Schema(description = "人员ID")
	private Long userId;

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
