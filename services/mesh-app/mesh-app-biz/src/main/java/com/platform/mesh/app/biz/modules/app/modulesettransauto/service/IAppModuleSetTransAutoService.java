package com.platform.mesh.app.biz.modules.app.modulesettransauto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.po.AppModuleSetTrans;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.dto.AppModuleSetTransAutoDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.dto.AppModuleSetTransAutoPageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.po.AppModuleSetTransAuto;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.vo.AppModuleSetTransAutoVO;
import com.platform.mesh.core.application.domain.vo.PageVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块转化自动化设置信息
 * @author 蝉鸣
 */
public interface IAppModuleSetTransAutoService extends IService<AppModuleSetTransAuto> {

    /**
     * 功能描述:
     * 〈获取当前模块转化自动化设置信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    PageVO<AppModuleSetTransAutoVO> getModuleSetTransAutoVOPage(AppModuleSetTransAutoPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前模块转化设置信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    PageVO<AppModuleSetTransBO> getModuleSetTransAutoBOPage(ModulePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈新增模块转化自动化设置〉
     * @param addDTO addDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addModuleSetTransAuto(AppModuleSetTransAutoDTO addDTO);

    /**
     * 功能描述:
     * 〈新增模块转化自动化设置〉
     * @param editDTO editDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean editModuleSetTransAuto(AppModuleSetTransAutoDTO editDTO);

}
