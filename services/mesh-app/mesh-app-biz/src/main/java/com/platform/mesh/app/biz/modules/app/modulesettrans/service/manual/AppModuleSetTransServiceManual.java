package com.platform.mesh.app.biz.modules.app.modulesettrans.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransMappingBO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleBaseVO;
import com.platform.mesh.app.biz.modules.app.modulebase.service.IAppModuleBaseService;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.po.AppModuleSetTrans;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.vo.AppModuleSetTransVO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.po.AppModuleSetTransAuto;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.service.IAppModuleSetTransAutoService;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.po.AppModuleSetTransMapping;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.service.IAppModuleSetTransMappingService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 模块转化设置
 * @author 蝉鸣
 */
@Service
public class AppModuleSetTransServiceManual {

    @Autowired
    private IAppModuleBaseService appModuleBaseService;

    @Autowired
    private IAppModuleSetTransMappingService appModuleSetTransMappingService;

    /**
     * 功能描述:
     * 〈获取当前信息〉
     * @param transMPage transMPage
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    public PageVO<AppModuleSetTransVO> transToVO(MPage<AppModuleSetTrans> transMPage) {
        MPage<AppModuleSetTransVO> pageVO = MPageUtil.convertToPage(transMPage);
        List<AppModuleSetTransVO> appModuleSetTransVOS = transMPage.getRecords().stream().map(this::transToVO).toList();
        pageVO.setRecords(appModuleSetTransVOS);
        return MPageUtil.convertToVO(pageVO, AppModuleSetTransVO.class);
    }

    /**
     * 功能描述:
     * 〈转换VO信息〉
     * @param transSet transSet
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    public AppModuleSetTransBO transToBO(AppModuleSetTrans transSet) {
        if(ObjectUtil.isEmpty(transSet)) {
            return null;
        }
        AppModuleSetTransBO transBO = new AppModuleSetTransBO();
        BeanUtils.copyProperties(transSet, transBO);
        if(ObjectUtil.isNotEmpty(transSet.getModuleFromId())){
            AppModuleBase moduleBase = appModuleBaseService.getById(transSet.getModuleFromId());
            transBO.setModuleFrom(BeanUtil.copyProperties(moduleBase, AppModuleBaseBO.class));
        }
        if(ObjectUtil.isNotEmpty(transSet.getModuleToId())){
            AppModuleBase moduleBase = appModuleBaseService.getById(transSet.getModuleToId());
            transBO.setModuleTo(BeanUtil.copyProperties(moduleBase, AppModuleBaseBO.class));
        }
        //获取转化字段映射
        List<AppModuleSetTransMapping> transMappingList = appModuleSetTransMappingService.lambdaQuery().eq(AppModuleSetTransMapping::getTransId, transSet.getId()).list();
        transBO.setMappingBOList(BeanUtil.copyToList(transMappingList, AppModuleSetTransMappingBO.class));
        return transBO;
    }

    /**
     * 功能描述:
     * 〈转换VO信息〉
     * @param transSet transSet
     * @return 正常返回:{@link AppModuleSetTrans}
     * @author 蝉鸣
     */
    public AppModuleSetTransVO transToVO(AppModuleSetTrans transSet) {
        if(ObjectUtil.isEmpty(transSet)) {
            return null;
        }
        AppModuleSetTransVO transVO = new AppModuleSetTransVO();
        BeanUtils.copyProperties(transSet, transVO);
        if(ObjectUtil.isNotEmpty(transSet.getModuleFromId())){
            AppModuleBase moduleBase = appModuleBaseService.getById(transSet.getModuleFromId());
            transVO.setModuleFrom(BeanUtil.copyProperties(moduleBase, AppModuleBaseVO.class));
        }
        if(ObjectUtil.isNotEmpty(transSet.getModuleToId())){
            AppModuleBase moduleBase = appModuleBaseService.getById(transSet.getModuleToId());
            transVO.setModuleTo(BeanUtil.copyProperties(moduleBase, AppModuleBaseVO.class));
        }
        return transVO;
    }

    /**
     * 功能描述:
     * 〈删除转化配置〉
     * @param transId transId
     * @author 蝉鸣
     */
    public void delModuleSetTrans(Long transId) {
        //删除自动规则
        IAppModuleSetTransAutoService appModuleSetTransAutoService = SpringContextHolderUtil.getBean(IAppModuleSetTransAutoService.class);
        appModuleSetTransAutoService.lambdaUpdate().eq(AppModuleSetTransAuto::getTransId,transId).remove();
        //删除字段映射
        appModuleSetTransMappingService.lambdaUpdate().eq(AppModuleSetTransMapping::getTransId,transId).remove();
    }
}