package com.platform.mesh.upms.biz.captcha.domain.dto;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="验证码校验DTO")
public class ImageCaptchaTrackDTO extends ImageCaptchaTrack {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+8")
    private Long startTime;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+8")
    private Long stopTime;

    @Override
    public void setStartTime(final Long startTime) {
        this.startTime = startTime;
        super.setStartTime(startTime);
    }

    @Override
    public void setStopTime(final Long stopTime) {
        this.stopTime = stopTime;
        super.setStopTime(startTime);
    }

    @Override
    public Long getStartTime() {
        return startTime;
    }

    @Override
    public Long getStopTime() {
        return stopTime;
    }
}
