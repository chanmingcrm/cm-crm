package com.platform.mesh.app.biz.modules.app.modulesetpick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.modulesetpick.domain.dto.AppModuleSetPickDTO;
import com.platform.mesh.app.biz.modules.app.modulesetpick.domain.po.AppModuleSetPick;
import com.platform.mesh.app.biz.modules.app.modulesetpick.domain.vo.AppModuleSetPickVO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块分配设置信息
 * @author 蝉鸣
 */
public interface IAppModuleSetPickService extends IService<AppModuleSetPick> {


    /**
     * 功能描述:
     * 〈获取当前模块分配设置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link AppModuleSetPick}
     * @author 蝉鸣
     */
    List<AppModuleSetPick> getModuleSetPickByModuleId(Long moduleId);

    /**
     * 功能描述:
     * 〈新增模块分配设置〉
     * @param moduleSetPickDTOS moduleSetPickDTOS
     * @return 正常返回:{@link AppModuleSetPickVO}
     * @author 蝉鸣
     */
    Boolean addModuleSetPick(List<AppModuleSetPickDTO> moduleSetPickDTOS);
    
}