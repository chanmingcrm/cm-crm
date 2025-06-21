package com.platform.mesh.core.application.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 分页VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="分页VO")
public class PageVO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private long current = 1;

    /**
     * 每页显示条数，默认 20
     */
    @Schema(description = "每页显示条数")
    private long size = 20;

    /**
     * 总记录数
     */
    @Schema(description = "总记录数")
    private long total = 0;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private long pages = 0;

    /**
     * 查询数据列表
     */
    @Schema(description = "查询数据列表")
    private List<T> records = new ArrayList<>();

    /**
     * ES 专用,时间快照
     */
    @Schema(description = "ES 专用,时间快照")
    private String pit;

    /**
     * ES 专用，搜索结果最后值
     */
    @Schema(description = "ES 专用，搜索结果最后值")
    private List<Map<String,String>> searchAfter;

}
