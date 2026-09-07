package com.platform.mesh.app.api.modules.app.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @description 导入错误BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="导入错误BO")
public class ImportErrorBO extends BaseBO {

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;


    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;


    /**
     * 批次ID
     */
    @Schema(description = "批次ID")
    private Long batchId;


    /**
     * 错误信息
     */
    @Schema(description = "错误信息")
    private Map<Integer, List<String>> errorRecordMap;


    /**
     * 行数据信息
     */
    @Schema(description = "行数据信息")
    private Map<Integer, String> rowDataMap;

}