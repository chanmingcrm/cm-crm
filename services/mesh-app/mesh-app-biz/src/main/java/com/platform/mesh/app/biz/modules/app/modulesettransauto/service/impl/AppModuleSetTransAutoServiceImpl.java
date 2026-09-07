package com.platform.mesh.app.biz.modules.app.modulesettransauto.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.po.AppModuleSetTrans;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.vo.AppModuleSetTransVO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.bo.AppModuleSetTransAutoBO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.dto.AppModuleSetTransAutoDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.dto.AppModuleSetTransAutoPageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.po.AppModuleSetTransAuto;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.vo.AppModuleSetTransAutoVO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.exception.AppModuleSetTransAutoExceptionEnum;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.mapper.AppModuleSetTransAutoMapper;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.service.IAppModuleSetTransAutoService;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.service.manual.AppModuleSetTransAutoServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
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
public class AppModuleSetTransAutoServiceImpl extends ServiceImpl<AppModuleSetTransAutoMapper, AppModuleSetTransAuto> implements IAppModuleSetTransAutoService {

    @Autowired
    private AppModuleSetTransAutoServiceManual appModuleSetTransAutoServiceManual;


    /**
     * 功能描述:
     * 〈获取当前信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    @Override
    public PageVO<AppModuleSetTransAutoVO> getModuleSetTransAutoVOPage(AppModuleSetTransAutoPageDTO pageDTO) {
        MPage<AppModuleSetTransAuto> mPage = MPageUtil.pageEntityToMPage(pageDTO, AppModuleSetTransAuto.class);
        MPage<AppModuleSetTransAuto> transMPage = this.lambdaQuery().eq(AppModuleSetTransAuto::getTransId, pageDTO.getTransId()).page(mPage);
        return appModuleSetTransAutoServiceManual.transToVO(transMPage);
    }


    /**
     * 功能描述:
     * 〈获取当前信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    @Override
    public PageVO<AppModuleSetTransBO> getModuleSetTransAutoBOPage(ModulePageDTO pageDTO) {
        //开启取消数据隔离设定
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        MPage<AppModuleSetTransAutoBO> mPage = MPageUtil.pageEntityToMPage(pageDTO, AppModuleSetTransAutoBO.class);
        MPage<AppModuleSetTransAutoBO> transMPage = this.getBaseMapper().selectTransAuthPage(mPage,pageDTO);
        PageVO<AppModuleSetTransBO> transToBO = appModuleSetTransAutoServiceManual.transToBO(transMPage);
        //关闭取消数据隔离设定
        DataScopeHandler.unEnableDataScope();
        return transToBO;
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param addDTO addDTO
     * @return 正常返回:{@link AppModuleSetTransVO}
     * @author 蝉鸣
     */
    @Override
    public Boolean addModuleSetTransAuto(AppModuleSetTransAutoDTO addDTO) {
        if(ObjectUtil.isEmpty(addDTO.getTransId())){
            throw AppModuleSetTransAutoExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        AppModuleSetTrans setTrans = appModuleSetTransAutoServiceManual.getSetTrans(addDTO.getTransId());
        if(ObjectUtil.isEmpty(setTrans)){
            throw AppModuleSetTransAutoExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        if(ObjectUtil.isEmpty(addDTO.getModuleSearchId())){
            addDTO.setModuleSearchId(NumberConst.NUM_0.longValue());
        }
        //查询是否有重复模块
        boolean exists = this.lambdaQuery()
                .eq(AppModuleSetTransAuto::getTransId, addDTO.getTransId())
                .eq(AppModuleSetTransAuto::getModuleFromId, setTrans.getModuleFromId())
                .eq(AppModuleSetTransAuto::getModuleSearchId, addDTO.getModuleSearchId())
                .eq(AppModuleSetTransAuto::getModuleToId, setTrans.getModuleToId())
                .exists();
        if(exists){
            throw AppModuleSetTransAutoExceptionEnum.ADD_SET_EXISTED.getBaseException();
        }
        AppModuleSetTransAuto appModuleSetTrans = BeanUtil.copyProperties(addDTO, AppModuleSetTransAuto.class);
        appModuleSetTrans.setModuleFromId(setTrans.getModuleFromId());
        appModuleSetTrans.setModuleToId(setTrans.getModuleToId());
        if(ObjectUtil.isEmpty(appModuleSetTrans.getRuleMac())){
            appModuleSetTrans.setRuleMac(StrConst.CREATE_TIME);
        }
        this.save(appModuleSetTrans);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param editDTO editDTO
     * @return 正常返回:{@link AppModuleSetTransVO}
     * @author 蝉鸣
     */
    @Override
    public Boolean editModuleSetTransAuto(AppModuleSetTransAutoDTO editDTO) {
        if(ObjectUtil.isEmpty(editDTO.getId())){
            return Boolean.FALSE;
        }
        AppModuleSetTrans setTrans = appModuleSetTransAutoServiceManual.getSetTrans(editDTO.getTransId());
        if(ObjectUtil.isEmpty(setTrans)){
            throw AppModuleSetTransAutoExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        AppModuleSetTransAuto appModuleSetTrans = BeanUtil.copyProperties(editDTO, AppModuleSetTransAuto.class);
        appModuleSetTrans.setModuleFromId(setTrans.getModuleFromId());
        appModuleSetTrans.setModuleToId(setTrans.getModuleToId());
        this.updateById(appModuleSetTrans);
        return Boolean.TRUE;
    }

}
