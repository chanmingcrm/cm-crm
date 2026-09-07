package com.platform.mesh.ai.biz.modules.cc.webset.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 官网咨询手机号提交。租户仅由 webSetId 在服务端确定。
 */
@Data
@Accessors(chain = true)
public class CcConsultationLeadDTO {

    @NotNull
    private Long webSetId;

    @NotBlank
    @Size(max = 100)
    private String groupHash;

    @NotBlank
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @NotBlank
    @Pattern(regexp = "^(book_demo|pricing|private_deployment)$", message = "咨询类型不支持")
    private String intent;

    @Size(max = 1000)
    private String sourcePage;
}
