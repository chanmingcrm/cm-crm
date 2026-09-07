package com.platform.mesh.ai.biz.modules.cc.webset.domain.dto;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 官网咨询引导配置。行为由访客端固定，这里只允许配置展示文案和试用地址。
 */
@Data
@Accessors(chain = true)
@Schema(description = "官网咨询引导配置")
public class CcConsultationGuideDTO {

    @Size(max = 40)
    private String welcomeText = "您好，想了解哪方面？";

    @Size(max = 12)
    private String freeTrialLabel = "免费试用";

    @Pattern(regexp = "^https://[^\\s]+$", message = "免费试用地址必须使用 https")
    @Size(max = 500)
    private String freeTrialUrl = "https://crm.woscosmos.com";

    @Size(max = 12)
    private String bookDemoLabel = "预约演示";

    @Size(max = 12)
    private String pricingLabel = "价格与版本";

    @Size(max = 160)
    private String pricingReply = "蝉鸣CRM提供永久免费版，包含3个账户、2000条数据；SaaS企业版199元/年/账户，私有化部署和源码交付需单独确认报价。";

    @Size(max = 12)
    private String privateDeploymentLabel = "私有化/源码";

    @Size(max = 160)
    private String privateDeploymentReply = "支持私有化部署、源码交付和企业微信聊天记录自动同步；服务器配置、实施周期、源码范围及企微接入条件需按项目确认。";

    @Size(max = 12)
    private String otherQuestionLabel = "其他问题";

    @Size(max = 80)
    private String phonePrompt = "请留下手机号，我们会尽快与您联系。";

    @Size(max = 100)
    private String successMessage = "已收到，我们会尽快与您联系。您也可以继续在这里咨询。";

    public static CcConsultationGuideDTO fromJson(String json) {
        CcConsultationGuideDTO guide = new CcConsultationGuideDTO();
        if (StrUtil.isNotBlank(json) && JSONUtil.isTypeJSONObject(json)) {
            guide = JSONUtil.toBean(json, CcConsultationGuideDTO.class);
        }
        return guide.normalize();
    }

    public String toJson() {
        return JSONUtil.toJsonStr(normalize());
    }

    private CcConsultationGuideDTO normalize() {
        CcConsultationGuideDTO defaults = new CcConsultationGuideDTO();
        welcomeText = StrUtil.blankToDefault(welcomeText, defaults.welcomeText);
        freeTrialLabel = StrUtil.blankToDefault(freeTrialLabel, defaults.freeTrialLabel);
        freeTrialUrl = StrUtil.blankToDefault(freeTrialUrl, defaults.freeTrialUrl);
        bookDemoLabel = StrUtil.blankToDefault(bookDemoLabel, defaults.bookDemoLabel);
        pricingLabel = StrUtil.blankToDefault(pricingLabel, defaults.pricingLabel);
        pricingReply = StrUtil.blankToDefault(pricingReply, defaults.pricingReply);
        privateDeploymentLabel = StrUtil.blankToDefault(privateDeploymentLabel, defaults.privateDeploymentLabel);
        privateDeploymentReply = StrUtil.blankToDefault(privateDeploymentReply, defaults.privateDeploymentReply);
        otherQuestionLabel = StrUtil.blankToDefault(otherQuestionLabel, defaults.otherQuestionLabel);
        phonePrompt = StrUtil.blankToDefault(phonePrompt, defaults.phonePrompt);
        successMessage = StrUtil.blankToDefault(successMessage, defaults.successMessage);
        return this;
    }
}
