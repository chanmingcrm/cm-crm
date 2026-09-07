package com.platform.mesh.upms.api.modules.sys.user.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 功能描述:
 * 〈账号租户关系状态〉
 * @author qingfeng
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "账号租户关系状态")
public class AccountTenantStatusBO extends BaseBO {

    @Schema(description = "账户ID")
    private Long accountId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "租户ID")
    private Long tenantId;

    @Schema(description = "账号码")
    private String accountCode;

    @Schema(description = "账户昵称")
    private String nickName;

    @Schema(description = "账户租户关系是否有效")
    private boolean active;
}
