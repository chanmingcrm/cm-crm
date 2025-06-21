package com.platform.mesh.upms.api.modules.org.member.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 成员关系BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "成员关系BO")
public class OrgMemberRelBO extends BaseBO {

	/**
	 * 成员ID
	 */
	@Schema(description = "成员ID")
	private Long memberId;

	/**
	 * 成员名称
	 */
	@Schema(description = "成员名称")
	private String memberName;

	/**
	 * 人员ID
	 */
	@Schema(description = "人员ID")
	private Long userId;

	/**
	 * 默认部门
	 */
	@Schema(description = "默认部门")
	private Long levelId;

	/**
	 * 组织名称
	 */
	@Schema(description = "组织名称")
	private String levelName;

}
