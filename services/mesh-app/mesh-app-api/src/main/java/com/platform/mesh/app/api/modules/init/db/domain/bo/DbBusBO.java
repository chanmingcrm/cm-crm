package com.platform.mesh.app.api.modules.init.db.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
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
public class DbBusBO extends BaseBO {

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String tableName;

    /**
     * 索引列表
     */
    @Schema(description = "索引列表")
    private List<String> indexList;

}