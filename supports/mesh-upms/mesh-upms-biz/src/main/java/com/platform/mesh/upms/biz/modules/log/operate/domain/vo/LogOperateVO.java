package com.platform.mesh.upms.biz.modules.log.operate.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

                        
/**
 * @description 日志(LogOperate)VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="日志")
public class LogOperateVO extends BaseVO {

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
