package com.platform.mesh.app.biz.modules.app.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.base.domain.dto.AppBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.base.domain.dto.AppBaseDTO;
import com.platform.mesh.app.biz.modules.app.base.domain.po.AppBase;
import com.platform.mesh.app.biz.modules.app.base.domain.vo.AppBaseVO;
import com.platform.mesh.app.biz.modules.app.base.exception.AppBaseExceptionEnum;
import com.platform.mesh.app.biz.modules.app.base.mapper.AppBaseMapper;
import com.platform.mesh.app.biz.modules.app.base.service.IAppBaseService;
import com.platform.mesh.app.biz.modules.app.base.service.manual.AppBaseServiceManual;
import com.platform.mesh.app.biz.modules.app.modulebase.constant.ModuleConst;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.enums.CopyTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 应用
 * @author 蝉鸣
 */
@Service
public class AppBaseServiceImpl extends ServiceImpl<AppBaseMapper, AppBase> implements IAppBaseService  {

    @Autowired
    private AppBaseServiceManual appBaseServiceManual;

    @Override
    public MPage<AppBase> selectPage(PageDTO pageDTO) {
        MPage<AppBase> baseMPage = MPageUtil.pageEntityToMPage(pageDTO, AppBase.class);
        return this.getBaseMapper().selectMPage(baseMPage,pageDTO);
    }

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param baseId baseId  
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    @Override
    public AppBaseVO getBaseInfoById(Long baseId) {
        AppBase appBase = this.getById(baseId);
        return appBaseServiceManual.getBaseInfoById(appBase);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    // todo 分布式事务处理
    @Override
    public AppBaseVO addBase(AppBaseDTO baseDTO) {
        AppBase appBase = BeanUtil.copyProperties(baseDTO, AppBase.class);
        appBase.setDelFlag(YesOrNoEnum.YES.getValue());
        this.save(appBase);
        appBaseServiceManual.addOrEditMenu(appBase,OperateTypeEnum.INSERT);
        return BeanUtil.copyProperties(appBase, AppBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    // todo 分布式事务处理
    @Override
    public AppBaseVO editBase(AppBaseDTO baseDTO) {
        if(ObjectUtil.isEmpty(baseDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(AppBaseDTO::getId);
            throw AppBaseExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        AppBase appBase = BeanUtil.copyProperties(baseDTO, AppBase.class);
        this.updateById(appBase);
        appBaseServiceManual.addOrEditMenu(appBase,OperateTypeEnum.UPDATE);
        return BeanUtil.copyProperties(appBase, AppBaseVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    // todo 分布式事务处理
    @Override
    public Boolean deleteBase(Long baseId) {
        AppBase appBase = this.getById(baseId);
        if(ObjectUtil.isEmpty(appBase)){
            return false;
        }
        //删除应用菜单
        appBaseServiceManual.appModuleMenuDelete(CollUtil.newArrayList(baseId));
        //删除当前应用
        appBase.setDelFlag(YesOrNoEnum.NO.getValue());
        this.updateById(appBase);
        return true;
    }

    /**
     * 功能描述:
     * 〈彻底删除〉
     * @param baseId baseId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    // todo 分布式事务处理
    @Override
    public Boolean clearBase(Long baseId) {
        AppBase appBase = this.getById(baseId);
        if(ObjectUtil.isEmpty(appBase)){
            return false;
        }
        //删除模块信息
        List<AppModuleBase> topModules = appBaseServiceManual.getTopModules(baseId);
        if(CollUtil.isNotEmpty(topModules)){
            List<Long> moduleIds = topModules.stream().map(AppModuleBase::getId).toList();
            FutureHandleUtil.runWithResult(moduleIds,appBaseServiceManual::clearAppModule);
        }
        //删除应用菜单
        appBaseServiceManual.appModuleMenuClear(CollUtil.newArrayList(baseId));
        //删除当前应用
        this.removeById(baseId);
        return true;
    }

    /**
     * 功能描述:
     * 〈拷贝应用〉
     * @param copyDTO copyDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean copyAppBase(AppBaseCopyDTO copyDTO) {
        //
        AppBase sourceApp = this.getById(copyDTO.getSourceId());
        if(ObjectUtil.isEmpty(sourceApp)){
            return Boolean.FALSE;
        }
        AppBase targetApp;
        //如果目标ID是空则新增应用
        if(CopyTypeEnum.PARENT.getValue().equals(copyDTO.getCopyType())){
            targetApp = new AppBase();
            targetApp.setAppType(sourceApp.getAppType());
            targetApp.setAppMac(sourceApp.getAppMac().concat(ModuleConst.COPY_EN_SUFFIX));
            targetApp.setAppName(sourceApp.getAppName().concat(ModuleConst.COPY_CN_SUFFIX));
            targetApp.setAppDesc(sourceApp.getAppDesc().concat(ModuleConst.COPY_CN_SUFFIX));
            targetApp.setAppLogo(sourceApp.getAppLogo());
            targetApp.setDelFlag(YesOrNoEnum.YES.getValue());
            this.save(targetApp);
            //新增菜单
            appBaseServiceManual.addOrEditMenu(targetApp,OperateTypeEnum.INSERT);
        }else{
            targetApp = this.getById(copyDTO.getTargetId());
        }
        if(ObjectUtil.isEmpty(targetApp)){
            return Boolean.FALSE;
        }
        copyDTO.setTargetId(targetApp.getId());
        //获取复制对象
        List<AppModuleBaseCopyDTO> copyDTOS =  appBaseServiceManual.getCopyDTOS(copyDTO);
        FutureHandleUtil.runWithResult(copyDTOS,appBaseServiceManual::copyAppModule);
        return Boolean.TRUE;
    }
}
