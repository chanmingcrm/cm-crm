package com.platform.mesh.upms.biz.modules.doc.pub.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 在线文档DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "doc_pub", autoResultMap = true)
public class DocPub extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 文档标识
    */
    private String docToken;


    /**
    * 文档标题
    */
    private String docUrlSite;


    /**
    * 文档标题
    */
    private String docUrlPrefix;


    /**
     * 租户ID
     */
    private Long tenantId;


}