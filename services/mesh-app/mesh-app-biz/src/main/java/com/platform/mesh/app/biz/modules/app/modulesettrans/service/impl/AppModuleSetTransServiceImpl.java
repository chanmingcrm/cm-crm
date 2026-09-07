package com.platform.mesh.app.biz.modules.app.modulesettrans.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleRelPageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.dto.AppModuleSetTransDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.po.AppModuleSetTrans;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.vo.AppModuleSetTransVO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.mapper.AppModuleSetTransMapper;
import com.platform.mesh.app.biz.modules.app.modulesettrans.service.IAppModuleSetTransService;
import com.platform.mesh.app.biz.modules.app.modulesettrans.service.manual.AppModuleSetTransServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 模块转化设置
 * @author 蝉鸣
 */
@Service
public class AppModuleSetTransServiceImpl extends ServiceImpl<AppModuleSetTransMapper, AppModuleSetTrans> implements IAppModuleSetTransService {

    @Autowired
    private AppModuleSetTransServiceManual appModuleSetTransServiceManual;

    /**
     * 功能描述:
     * 〈根据Id获取模块转化配置〉
     * @param transId transId
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    @Override
    public AppModuleSetTransBO getModuleSetTransById(Long transId) {
        
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);

        AppModuleSetTrans setTrans = this.getById(transId);
        if(ObjectUtil.isEmpty(setTrans)){
            DataScopeHandler.unEnableDataScope();
            return new AppModuleSetTransBO();
        }
        AppModuleSetTransBO appModuleSetTransBO = appModuleSetTransServiceManual.transToBO(setTrans);
        DataScopeHandler.unEnableDataScope();
        
        return appModuleSetTransBO;
    }

    /**
     * 功能描述:
     * 〈获取当前信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    @Override
    public PageVO<AppModuleSetTransVO> getModuleSetTransVOPage(AppModuleRelPageDTO pageDTO) {
        MPage<AppModuleSetTrans> mPage = MPageUtil.pageEntityToMPage(pageDTO, AppModuleSetTrans.class);
        MPage<AppModuleSetTrans> transMPage = this.lambdaQuery().eq(AppModuleSetTrans::getModuleFromId, pageDTO.getModuleId()).page(mPage);
        return appModuleSetTransServiceManual.transToVO(transMPage);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param moduleSetTransDTO moduleSetTransDTO
     * @return 正常返回:{@link AppModuleSetTransVO}
     * @author 蝉鸣
     */
    @Override
    public Boolean addModuleSetTrans(AppModuleSetTransDTO moduleSetTransDTO) {
        AppModuleSetTrans appModuleSetTrans = BeanUtil.copyProperties(moduleSetTransDTO, AppModuleSetTrans.class);
        this.save(appModuleSetTrans);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param moduleSetTransDTO moduleSetTransDTO
     * @return 正常返回:{@link AppModuleSetTransVO}
     * @author 蝉鸣
     */
    @Override
    public Boolean editModuleSetTrans(AppModuleSetTransDTO moduleSetTransDTO) {
        if(ObjectUtil.isEmpty(moduleSetTransDTO.getId())){
            return Boolean.FALSE;
        }
        AppModuleSetTrans appModuleSetTrans = BeanUtil.copyProperties(moduleSetTransDTO, AppModuleSetTrans.class);
        this.updateById(appModuleSetTrans);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除转化配置〉
     * @param transId transId
     * @return 正常返回:{@link AppModuleSetTransVO}
     * @author 蝉鸣
     */
    @Override
    public Boolean delModuleSetTrans(Long transId) {
        //删除相关配置
        appModuleSetTransServiceManual.delModuleSetTrans(transId);
        //删除当前配置
        this.removeById(transId);
        return Boolean.TRUE;
    }

}
