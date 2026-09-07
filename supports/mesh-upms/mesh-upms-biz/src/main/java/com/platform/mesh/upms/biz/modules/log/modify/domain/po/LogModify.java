package com.platform.mesh.upms.biz.modules.log.modify.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
                        
/**
 * @description 修改日志(LogModify)实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("log_modify")
public class LogModify extends BasePO {

    /**
    * 日志ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 模块ID
     */
    private Long moduleId;

    /**
     * 数据ID
     */
    private Long dataId;

    /**
     * 批次ID
     */
    private Long batchId;

    /**
     * 字段名称
     */
    private String keyName;

    /**
     * 变更值
     */
    private String valueJson;

    /**
     * 数据类型
     */
    private Integer operateType;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;


}

