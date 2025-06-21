package com.platform.mesh.upms.biz.modules.sys.user.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 成员VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "成员VO")
public class SysMemberVO extends BaseVO {


	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private Long userId;

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

}
