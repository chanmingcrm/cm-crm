package com.platform.mesh.app.api.modules.init.db.domain.bo;

import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 转化数据BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="转化数据BO")
public class DbTransBO extends BaseBO {


    /**
     * 转化BO
     */
    @Schema(description = "转化BO")
    private AppModuleSetTransBO transBO;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private List<Long> dataIds;

    /**
     * 数据组织关联
     */
    @Schema(description = "数据组织关联")
    private OrgMemberRelBO userRelBO;

}