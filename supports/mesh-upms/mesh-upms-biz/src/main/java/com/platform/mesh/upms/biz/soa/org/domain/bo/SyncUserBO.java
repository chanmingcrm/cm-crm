package com.platform.mesh.upms.biz.soa.org.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 同步人员BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "同步人员BO")
public class SyncUserBO extends BaseBO {

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private String userId;

	/**
	 * 唯一ID
	 */
	@Schema(description = "唯一ID")
	private String unionId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

	/**
	 * 电话
	 */
	@Schema(description = "电话")
	private String phone;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	private String email;

	/**
	 * 头像
	 */
	@Schema(description = "头像")
	private String avatar;

	/**
	 * 是否管理员
	 */
	@Schema(description = "是否管理员")
	private Integer isAdmin;

	/**
	 * 是否激活
	 */
	@Schema(description = "是否激活")
	private Integer isActive;

	/**
	 * 来源标识
	 */
	@SchemaEnum(value = SourceFlagEnum.class, description = "来源标识")
	private Integer sourceFlag;


	/**
	 * 组织Ids
	 */
	@Schema(description = "组织Ids")
	private List<String> levelIds;


}
