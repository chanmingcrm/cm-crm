package com.platform.mesh.app.biz.modules.app.modulesetopen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.dto.AppModuleSetOpenDTO;
import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.po.AppModuleSetOpen;
import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.vo.AppModuleSetOpenVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块开放设置信息
 * @author 蝉鸣
 */
public interface IAppModuleSetOpenService extends IService<AppModuleSetOpen> {


    /**
     * 功能描述:
     * 〈获取当前模块开放设置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link AppModuleSetOpen}
     * @author 蝉鸣
     */
    AppModuleSetOpen getModuleSetOpenByModuleId(Long moduleId);

    /**
     * 功能描述:
     * 〈新增模块开放设置〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link AppModuleSetOpenVO}
     * @author 蝉鸣
     */
    AppModuleSetOpenVO addModuleSetOpen(AppModuleSetOpenDTO formColumnMappingDTO);

}