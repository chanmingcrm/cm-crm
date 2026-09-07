package com.platform.mesh.app.api.modules.app.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @description 导入结果公共VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="导入结果公共VO")
public class ImportVO extends BaseVO {

    /**
     * 结果
     */
    @Schema(description = "结果")
    private Boolean result;

    /**
     * 批次ID
     */
    @Schema(description = "批次ID")
    private Long batchId;

    /**
     * 总数量
     */
    @Schema(description = "总数量")
    private Long totalNum;

    /**
     * 覆盖数量
     */
    @Schema(description = "覆盖量")
    private Long overNum;

    /**
     * 跳过数量
     */
    @Schema(description = "跳过量")
    private Long skipNum;

    /**
     * 失败数量
     */
    @Schema(description = "失败数量")
    private Long errorNum;

    /**
     * 快速信息
     */
    @Schema(description = "快速信息")
    private Map<Integer,List<String>> context;

}