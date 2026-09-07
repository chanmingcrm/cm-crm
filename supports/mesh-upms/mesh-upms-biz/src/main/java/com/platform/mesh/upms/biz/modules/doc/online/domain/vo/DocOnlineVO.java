package com.platform.mesh.upms.biz.modules.doc.online.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.biz.modules.doc.dir.enums.DocFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 在线文档DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="在线文档DTO")
public class DocOnlineVO extends BaseVO {


    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    
    /**
     * 文档标识
     */
    @SchemaEnum(value = DocFlagEnum.class, description = "文档标识")
    private Integer docFlag;


    /**
     * 文档标题
     */
    @Schema(description = "文档标题")
    private String docTitle;


    /**
     * 文档关键字
     */
    @Schema(description = "文档关键字")
    private String docKeyword;


    /**
     * 文档描述
     */
    @Schema(description = "文档描述")
    private String docDesc;


    /**
     * 文档文本
     */
    @Schema(description = "文档文本")
    private String docContext;


    /**
     * 移动端文档文本
     */
    @Schema(description = "移动端文档文本")
    private String docAppContext;


    /**
     * 星级
     */
    @Schema(description = "星级")
    private Integer starLevel;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;


    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;


    /**
     * 发布标识
     */
    @Schema(description = "发布标识")
    private Integer pubFlag;
}