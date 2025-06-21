package com.platform.mesh.upms.biz.modules.org.postdatascope.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.upms.biz.modules.org.postdatascope.enums.DataFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 岗位权限VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="岗位权限VO")
public class OrgPostDataScopeVO extends BaseVO {

    /**
    * 职位ID
    */
    @Schema(description = "岗位ID")
    private Long postId;
    /**
     * 数据权限类型
     */
    @Schema(implementation = DataScopeEnum.class)
    private Integer dataScope;
    /**
     * 数据关联类型
     */
    @Schema(implementation = DataFlagEnum.class)
    private Integer dataFlag;
    /**
     * 数据关联ID
     */
    @Schema(description = "数据关联ID")
    private Long dataId;
    /**
     * 数据关联名称
     */
    @Schema(description = "数据关联名称")
    private String dataName;

}

