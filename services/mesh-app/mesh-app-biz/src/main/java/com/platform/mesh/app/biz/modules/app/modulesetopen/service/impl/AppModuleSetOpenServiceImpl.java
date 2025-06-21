package com.platform.mesh.app.biz.modules.app.modulesetopen.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.dto.AppModuleSetOpenDTO;
import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.po.AppModuleSetOpen;
import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.vo.AppModuleSetOpenVO;
import com.platform.mesh.app.biz.modules.app.modulesetopen.mapper.AppModuleSetOpenMapper;
import com.platform.mesh.app.biz.modules.app.modulesetopen.service.IAppModuleSetOpenService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块开放设置
 * @author 蝉鸣
 */
@Service
public class AppModuleSetOpenServiceImpl extends ServiceImpl<AppModuleSetOpenMapper, AppModuleSetOpen> implements IAppModuleSetOpenService {


    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link AppModuleSetOpen}
     * @author 蝉鸣
     */
    @Override
    public AppModuleSetOpen getModuleSetOpenByModuleId(Long moduleId) {
        List<AppModuleSetOpen> list = this.lambdaQuery().eq(AppModuleSetOpen::getModuleId, moduleId).list();
        if(CollUtil.isEmpty(list)){
            return null;
        }
        return CollUtil.getFirst(list);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param appModuleSetOpenDTO appModuleSetOpenDTO
     * @return 正常返回:{@link AppModuleSetOpenVO}
     * @author 蝉鸣
     */
    @Override
    public AppModuleSetOpenVO addModuleSetOpen(AppModuleSetOpenDTO appModuleSetOpenDTO) {
        AppModuleSetOpen appModuleSetOpen = BeanUtil.copyProperties(appModuleSetOpenDTO, AppModuleSetOpen.class);
        //先删除旧信息
        this.lambdaUpdate().eq(AppModuleSetOpen::getModuleId,appModuleSetOpenDTO.getModuleId()).remove();
        //新增信息
        this.save(appModuleSetOpen);
        return BeanUtil.copyProperties(appModuleSetOpen, AppModuleSetOpenVO.class);
    }

}