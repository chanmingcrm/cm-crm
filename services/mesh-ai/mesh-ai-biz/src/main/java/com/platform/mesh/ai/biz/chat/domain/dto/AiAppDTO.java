package com.platform.mesh.ai.biz.chat.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @description AI会话DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI会话DTO")
public class AiAppDTO extends AiMsgDTO {

    /**
     * 字段映射
     */
    @Schema(description = "字段映射")
    private Map<String,String> columnMap;


}