package com.platform.mesh.upms.biz.captcha.domain.dto;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description="验证码校验DTO")
public class ImageCaptchaTrackDTO extends ImageCaptchaTrack {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date stopTime;

    @Override
    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
        super.setStartTime(startTime);
    }

    @Override
    public void setStopTime(final Date stopTime) {
        this.stopTime = stopTime;
        super.setStopTime(startTime);
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public Date getStopTime() {
        return stopTime;
    }
}
