package com.platform.mesh.douyin.domain.feiyu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="飞鱼DTO对象")
public class FeiYuDTO extends DouYinPageDTO {

    @Schema(description ="客户ids")
    private List<Long> advertiserIds;

}
