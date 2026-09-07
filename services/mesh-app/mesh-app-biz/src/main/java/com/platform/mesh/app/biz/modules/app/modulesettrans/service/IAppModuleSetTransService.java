package com.platform.mesh.app.biz.modules.app.modulesettrans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleRelPageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.dto.AppModuleSetTransDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.po.AppModuleSetTrans;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.vo.AppModuleSetTransVO;
import com.platform.mesh.core.application.domain.vo.PageVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块转化设置信息
 * @author 蝉鸣
 */
public interface IAppModuleSetTransService extends IService<AppModuleSetTrans> {


    /**
     * 功能描述:
     * 〈根据Id获取模块转化配置〉
     * @param transId transId
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    AppModuleSetTransBO getModuleSetTransById(Long transId);

    /**
     * 功能描述:
     * 〈获取当前模块转化设置信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    PageVO<AppModuleSetTransVO> getModuleSetTransVOPage(AppModuleRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈新增模块转化设置〉
     * @param moduleSetTransDTO moduleSetTransDTO
     * @return 正常返回:{@link AppModuleSetTransVO}
     * @author 蝉鸣
     */
    Boolean addModuleSetTrans(AppModuleSetTransDTO moduleSetTransDTO);

    /**
     * 功能描述:
     * 〈新增模块转化设置〉
     * @param moduleSetTransDTO moduleSetTransDTO
     * @return 正常返回:{@link AppModuleSetTransVO}
     * @author 蝉鸣
     */
    Boolean editModuleSetTrans(AppModuleSetTransDTO moduleSetTransDTO);

    /**
     * 功能描述:
     * 〈删除转化配置〉
     * @param transId transId
     * @return 正常返回:{@link AppModuleSetTransVO}
     * @author 蝉鸣
     */
    Boolean delModuleSetTrans(Long transId);
}
