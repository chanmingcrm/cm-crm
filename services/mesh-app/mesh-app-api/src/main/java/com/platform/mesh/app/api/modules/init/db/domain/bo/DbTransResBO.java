package com.platform.mesh.app.api.modules.init.db.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description 转化数据BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="转化数据BO")
public class DbTransResBO extends BaseBO {


    /**
     * 转化BO
     */
    @Schema(description = "转化BO")
    private Long fromModuleId;

    /**
     * 数据ID
     */
    @Schema(description = "数据ID")
    private Long toModuleId;

    /**
     * 数据对照
     */
    @Schema(description = "数据对照")
    private Map<Long,Long> dataMap;

}