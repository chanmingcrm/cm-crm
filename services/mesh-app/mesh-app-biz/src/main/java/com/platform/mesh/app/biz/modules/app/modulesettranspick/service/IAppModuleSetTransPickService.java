package com.platform.mesh.app.biz.modules.app.modulesettranspick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.modulesettranspick.domain.dto.AppModuleSetTransPickDTO;
import com.platform.mesh.app.biz.modules.app.modulesettranspick.domain.po.AppModuleSetTransPick;
import com.platform.mesh.app.biz.modules.app.modulesettranspick.domain.vo.AppModuleSetTransPickVO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块分配设置信息
 * @author 蝉鸣
 */
public interface IAppModuleSetTransPickService extends IService<AppModuleSetTransPick> {


    /**
     * 功能描述:
     * 〈获取当前模块分配设置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link List<AppModuleSetTransPick>}
     * @author 蝉鸣
     */
    List<AppModuleSetTransPick> getModuleSetPickByModuleId(Long moduleId);

    /**
     * 功能描述:
     * 〈获取当前模块分配设置信息〉
     * @param transId transId
     * @return 正常返回:{@link List<AppModuleSetTransPick>}
     * @author 蝉鸣
     */
    List<AppModuleSetTransPick> getModuleSetTransPickByTransId(Long transId);

    /**
     * 功能描述:
     * 〈新增模块分配设置〉
     * @param moduleSetPickDTOS moduleSetPickDTOS
     * @return 正常返回:{@link AppModuleSetTransPickVO}
     * @author 蝉鸣
     */
    Boolean addModuleSetPick(List<AppModuleSetTransPickDTO> moduleSetPickDTOS);

}
