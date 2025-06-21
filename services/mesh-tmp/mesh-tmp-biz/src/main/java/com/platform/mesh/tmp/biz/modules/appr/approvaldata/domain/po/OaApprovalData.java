package com.platform.mesh.tmp.biz.modules.appr.approvaldata.domain.po;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description OA办公审批数据DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "oa_approval_data", autoResultMap = true)
public class OaApprovalData extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 父模块ID
    */
    private Long parentModuleId;

    /**
    * 模块ID
    */
    private Long moduleId;

    /**
    * 新增表单ID
    */
    private Long addFormId;

    /**
    * 编辑页面ID
    */
    private Long editFormId;

    /**
    * 数据ID
    */
    private Long dataId;

    /**
    * 字段ID
    */
    private Long columnId;

    /**
    * 字段标识
    */
    private String columnMac;

    /**
    * 字段名称
    */
    private String columnName;

    /**
    * 数据值
    */
    private String dataValue;

    /**
    * 数据类型DataTypeEnum
    */
    private Integer dataType;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 修改时间
    */
    private LocalDateTime updateTime;

    /**
    * 用户ID
    */
    private Long scopeUserId;

    /**
    * 组织ID
    */
    private Long scopeOrgId;

}