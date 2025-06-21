package com.platform.mesh.app.biz.modules.app.modulesettransmapping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.dto.AppModuleSetTransMappingDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.po.AppModuleSetTransMapping;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.vo.AppModuleSetTransMappingVO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 模块转化字段映射设置信息
 * @author 蝉鸣
 */
public interface IAppModuleSetTransMappingService extends IService<AppModuleSetTransMapping> {


    /**
     * 功能描述:
     * 〈获取当前模块转化字段映射设置信息〉
     * @param fromModuleId fromModuleId
     * @param toModuleId toModuleId
     * @return 正常返回:{@link List<AppModuleSetTransMapping>}
     * @author 蝉鸣
     */
    List<AppModuleSetTransMapping> getModuleSetTransMappingByModuleId(Long fromModuleId,Long toModuleId);

    /**
     * 功能描述:
     * 〈新增模块转化字段映射设置〉
     * @param moduleSetTransMappingDTOS moduleSetTransMappingDTOS
     * @return 正常返回:{@link AppModuleSetTransMappingVO}
     * @author 蝉鸣
     */
    Boolean addModuleSetTransMapping(List<AppModuleSetTransMappingDTO> moduleSetTransMappingDTOS);
}