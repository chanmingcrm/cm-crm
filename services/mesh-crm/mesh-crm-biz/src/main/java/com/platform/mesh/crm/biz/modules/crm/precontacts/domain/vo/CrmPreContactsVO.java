package com.platform.mesh.crm.biz.modules.crm.precontacts.domain.vo;

import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @description 联系人对象VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="联系人对象VO")
public class CrmPreContactsVO extends AppVO {


    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long parentId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private Long customerId;
}