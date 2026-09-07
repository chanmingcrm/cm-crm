package com.platform.mesh.ai.biz.chat.domain.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * @description OCR识别
 * @author 蝉鸣
 */
@Schema(description = "发票识别OCR")
public record OcrBO(

		@JsonPropertyDescription("发票代码") @Schema(description = "发票代码") @JsonProperty(required = true,
				value = "invoiceNo") String invoiceNo,

		@JsonPropertyDescription("发票金额") @Schema(description = "发票金额") @JsonProperty(required = true,
				value = "amount") BigDecimal amount) {
}
