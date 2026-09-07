package com.platform.mesh.douyin.domain.feiyu.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="巨量引擎DTO对象")
public class DouYinPageDTO extends PageDTO {

    @Schema(description ="请求token")
    private String accessToken;

    @Schema(description ="起始时间")
    private LocalDateTime startTime;

    @Schema(description ="截止时间")
    private LocalDateTime endTime;

}
