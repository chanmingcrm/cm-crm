package com.platform.mesh.app.biz.modules.app.modulesettransmapping.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.dto.AppModuleSetTransMappingDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.po.AppModuleSetTransMapping;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.mapper.AppModuleSetTransMappingMapper;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.service.IAppModuleSetTransMappingService;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.service.manual.AppModuleSetTransMappingServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块转化字段映射设置
 * @author 蝉鸣
 */
@Service
public class AppModuleSetTransMappingServiceImpl extends ServiceImpl<AppModuleSetTransMappingMapper, AppModuleSetTransMapping> implements IAppModuleSetTransMappingService {

    @Autowired
    private AppModuleSetTransMappingServiceManual appModuleSetTransMappingServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param transId transId
     * @return 正常返回:{@link List<AppModuleSetTransMapping>}
     * @author 蝉鸣
     */
    @Override
    public List<AppModuleSetTransMapping> getModuleSetTransMappingByTransId(Long transId) {
        return this.getBaseMapper().getModuleSetTransMappingByTransId(transId);
    }

    /**
     * 功能描述:
     * 〈获取当前信息〉
     * @param fromModuleId fromModuleId
     * @param toModuleId toModuleId
     * @return 正常返回:{@link List<AppModuleSetTransMapping>}
     * @author 蝉鸣
     */
    @Override
    public List<AppModuleSetTransMapping> getModuleSetTransMappingByModuleId(Long fromModuleId,Long toModuleId) {
        return this.getBaseMapper().getModuleSetTransMappingByModuleId(fromModuleId,toModuleId);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param moduleSetTransMappingDTOS moduleSetTransMappingDTOS
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addModuleSetTransMapping(List<AppModuleSetTransMappingDTO> moduleSetTransMappingDTOS) {
        if(CollUtil.isEmpty(moduleSetTransMappingDTOS)){
            return Boolean.FALSE;
        }
        //删除旧数据
        AppModuleSetTransMappingDTO first = CollUtil.getFirst(moduleSetTransMappingDTOS);
        this.lambdaUpdate()
                .eq(AppModuleSetTransMapping::getTransId,first.getTransId())
                .remove();
        //新增数据
        List<AppModuleSetTransMapping> list = moduleSetTransMappingDTOS.stream().map(dto-> BeanUtil.copyProperties(dto, AppModuleSetTransMapping.class)).toList();
        this.saveBatch(list);
        return Boolean.TRUE;
    }

}
