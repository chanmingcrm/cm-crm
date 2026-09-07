package com.platform.mesh.upms.biz.modules.sys.user.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 用户列表查询对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户列表查询对象")
public class SysUserPageDTO extends PageDTO {

    /**
     * 关键字搜索
     */
    @Schema(description = "关键字搜索")
    private String searchValue;

}
