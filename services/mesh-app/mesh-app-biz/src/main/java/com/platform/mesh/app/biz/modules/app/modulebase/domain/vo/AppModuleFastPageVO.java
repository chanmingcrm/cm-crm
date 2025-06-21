package com.platform.mesh.app.biz.modules.app.modulebase.domain.vo;

import com.platform.mesh.app.biz.modules.app.formbase.domain.vo.AppFormBaseVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @description 模块VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="模块VO")
public class AppModuleFastPageVO extends BaseVO {



    /**
     * 模块信息
     */
    @Schema(description = "模块信息")
    private AppModuleBaseVO moduleBaseVO;


    /**
     * 页面信息
     */
    @Schema(description = "页面信息")
    private AppFormBaseVO formBaseVO;


    /**
     * 页面组件
     */
    @Schema(description = "页面组件")
    private List<AppFormColumnVO> pageForm;

    /**
     * 页面组件关联组件
     */
    @Schema(description = "页面组件关联组件")
    private Map<Long,List<AppFormColumnVO>> relForm;

}