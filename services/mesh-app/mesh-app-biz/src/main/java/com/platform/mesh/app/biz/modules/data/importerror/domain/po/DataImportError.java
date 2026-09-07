package com.platform.mesh.app.biz.modules.data.importerror.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 导入错误数据PO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "data_import_error", autoResultMap = true)
public class DataImportError extends BasePO {


    /**
     * 主键ID
     */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 模块ID
     */
    private Long moduleId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 批次ID
     */
    private Long batchId;

    /**
     * 行号
     */
    private Integer rowNum;

    /**
     * 行数据
     */
    private String rowData;

    /**
     * 错误记录
     */
    private String errorRecord;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}