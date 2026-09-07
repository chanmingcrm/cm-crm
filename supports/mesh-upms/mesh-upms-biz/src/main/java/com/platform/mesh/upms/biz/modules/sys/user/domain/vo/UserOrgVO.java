package com.platform.mesh.upms.biz.modules.sys.user.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 用户组织VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户组织VO")
public class UserOrgVO extends BaseVO {

	/**
	 * 组织ID
	 */
	@Schema(description = "组织ID")
	private Long levelId;

	/**
	 * 组织名称
	 */
	@Schema(description = "组织名称")
	private String levelName;

	/**
	 * 职位ID
	 */
	@Schema(description = "职位ID")
	private Long postId;

	/**
	 * 职位名称
	 */
	@Schema(description = "职位名称")
	private String postName;

}
