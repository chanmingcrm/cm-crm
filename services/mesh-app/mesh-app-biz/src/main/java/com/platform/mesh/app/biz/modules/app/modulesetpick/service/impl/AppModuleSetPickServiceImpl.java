package com.platform.mesh.app.biz.modules.app.modulesetpick.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.modulesetpick.domain.dto.AppModuleSetPickDTO;
import com.platform.mesh.app.biz.modules.app.modulesetpick.domain.po.AppModuleSetPick;
import com.platform.mesh.app.biz.modules.app.modulesetpick.domain.vo.AppModuleSetPickVO;
import com.platform.mesh.app.biz.modules.app.modulesetpick.mapper.AppModuleSetPickMapper;
import com.platform.mesh.app.biz.modules.app.modulesetpick.service.IAppModuleSetPickService;
import com.platform.mesh.app.biz.modules.app.modulesetpick.service.manual.AppModuleSetPickServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块分配设置
 * @author 蝉鸣
 */
@Service
public class AppModuleSetPickServiceImpl extends ServiceImpl<AppModuleSetPickMapper, AppModuleSetPick> implements IAppModuleSetPickService {

    @Autowired
    private AppModuleSetPickServiceManual appModuleSetPickServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link AppModuleSetPick}
     * @author 蝉鸣
     */
    @Override
    public List<AppModuleSetPick> getModuleSetPickByModuleId(Long moduleId) {
        return this.lambdaQuery().eq(AppModuleSetPick::getModuleId, moduleId).list();
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param moduleSetPickDTOS moduleSetPickDTOS
     * @return 正常返回:{@link AppModuleSetPickVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addModuleSetPick(List<AppModuleSetPickDTO> moduleSetPickDTOS) {
        if(CollUtil.isEmpty(moduleSetPickDTOS)){
            return Boolean.FALSE;
        }
        List<Long> memberIds = moduleSetPickDTOS.stream().map(AppModuleSetPickDTO::getMemberId).toList();
        if(CollUtil.isEmpty(memberIds)){
            return Boolean.FALSE;
        }
        AppModuleSetPickDTO first = CollUtil.getFirst(moduleSetPickDTOS);
        //通过成员ID获取成员对象获取UserId
        Map<Long, Long> memberUserRelMap = appModuleSetPickServiceManual.getMemberUserRelByIds(memberIds);
        if(CollUtil.isEmpty(memberUserRelMap)){
            return Boolean.FALSE;
        }
        //先删除旧信息
        this.lambdaUpdate().eq(AppModuleSetPick::getModuleId,first.getModuleId()).remove();
        //新增
        List<AppModuleSetPick> moduleSetPicks = CollUtil.newArrayList();
        for (AppModuleSetPickDTO setPickDTO : moduleSetPickDTOS) {
            if(memberUserRelMap.containsKey(setPickDTO.getMemberId())){
                AppModuleSetPick appModuleSetPick = BeanUtil.copyProperties(setPickDTO, AppModuleSetPick.class);
                Long userId = memberUserRelMap.get(setPickDTO.getMemberId());
                appModuleSetPick.setUserId(userId);
                moduleSetPicks.add(appModuleSetPick);
            }
        }
        if(CollUtil.isEmpty(moduleSetPicks)){
            return Boolean.FALSE;
        }
        //新增信息
        this.saveBatch(moduleSetPicks);
        return Boolean.TRUE;
    }
}