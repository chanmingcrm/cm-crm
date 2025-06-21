package com.platform.mesh.app.biz.modules.app.base.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.biz.modules.app.base.domain.dto.AppBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.base.domain.po.AppBase;
import com.platform.mesh.app.biz.modules.app.base.domain.vo.AppBaseVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.enums.CopyTypeEnum;
import com.platform.mesh.app.biz.modules.app.modulebase.service.IAppModuleBaseService;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.api.modules.sys.menu.feign.RemoteSysMenuService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 应用
 * @author 蝉鸣
 */
@Service
public class AppBaseServiceManual{

    private static final Logger log = LoggerFactory.getLogger(AppBaseServiceManual.class);

    @Resource
    private IAppModuleBaseService appModuleBaseService;

    @Resource
    private RemoteSysMenuService remoteSysMenuService;
    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appBase appBase 
     * @return 正常返回:{@link AppBaseVO}
     * @author 蝉鸣
     */
    public AppBaseVO getBaseInfoById(AppBase appBase) {
        AppBaseVO appBaseVO = new AppBaseVO();
        if(ObjectUtil.isEmpty(appBaseVO)){
            return appBaseVO;
        }
        //转换VO
        BeanUtil.copyProperties(appBase, appBaseVO);
        return appBaseVO;
    }

    /**
     * 功能描述:
     * 〈同步菜单权限〉
     * @param appBase appBase
     * @param operateTypeEnum operateTypeEnum
     * @author 蝉鸣
     */
    public void addOrEditMenu(AppBase appBase, OperateTypeEnum operateTypeEnum) {
        AppMenuBO appMenuBO = new AppMenuBO();
        appMenuBO.setModuleId(appBase.getId());
        appMenuBO.setRelId(appBase.getId());
        appMenuBO.setOperateType(operateTypeEnum.getValue());
        appMenuBO.setTitle(appBase.getAppName());
        appMenuBO.setMenuType(MenuTypeEnum.APP.getValue());
        remoteSysMenuService.addOrEditMenu(appMenuBO);
    }

    /**
     * 功能描述:
     * 〈删除同步菜单权限〉
     * @param moduleIds moduleIds
     * @author 蝉鸣
     */
    public void appModuleMenuDelete(List<Long> moduleIds) {
        remoteSysMenuService.appModuleMenuDelete(moduleIds);
    }

    /**
     * 功能描述:
     * 〈清除同步菜单权限〉
     * @param moduleIds moduleIds
     * @author 蝉鸣
     */
    public void appModuleMenuClear(List<Long> moduleIds) {
        remoteSysMenuService.appModuleMenuClear(moduleIds);
    }

    /**
     * 功能描述:
     * 〈获取应用下顶层模块〉
     * @param appId appId
     * @author 蝉鸣
     */
    public List<AppModuleBase> getTopModules(Long appId) {
        return appModuleBaseService.lambdaQuery()
                .eq(AppModuleBase::getAppId, appId)
                .eq(AppModuleBase::getParentId, NumberConst.NUM_0.longValue())
                .list();
    }

    /**
     * 功能描述:
     * 〈获取应用下顶层模块〉
     * @param moduleId moduleId
     * @author 蝉鸣
     */
    public Boolean clearAppModule(Long moduleId) {
         appModuleBaseService.clearModuleBase(moduleId);
         return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈复制应用下顶层模块〉
     * @param appCopyDTO appCopyDTO
     * @author 蝉鸣
     */
    public List<AppModuleBaseCopyDTO> getCopyDTOS(AppBaseCopyDTO appCopyDTO) {
        List<AppModuleBase> topModules = this.getTopModules(appCopyDTO.getSourceId());
        if(CollUtil.isEmpty(topModules)){
            return CollUtil.newArrayList();
        }
        //如果是同级复制初始化目标顶层模块
        Map<Long, Long> copyMap = new HashMap<>();
        if(CopyTypeEnum.SELF.getValue().equals(appCopyDTO.getCopyType())){
            List<AppModuleBase> targetModules = CollUtil.newArrayList();
            for (AppModuleBase topModule : topModules) {
                AppModuleBase targetModule = new AppModuleBase();
                BeanUtil.copyProperties(topModule, targetModule, ObjFieldUtil.ignoreDefault());
                Long id = IdUtil.getSnowflake().nextId();
                targetModule.setId(id);
                targetModule.setAppId(appCopyDTO.getTargetId());
                copyMap.put(topModule.getId(), targetModule.getId());
            }
            appModuleBaseService.saveBatch(targetModules);
        }
        return topModules.stream().map(module->{
            AppModuleBaseCopyDTO moduleCopyDTO = new AppModuleBaseCopyDTO();
            moduleCopyDTO.setAppId(appCopyDTO.getTargetId());
            moduleCopyDTO.setForceOver(YesOrNoEnum.YES.getValue());
            moduleCopyDTO.setCopyType(appCopyDTO.getCopyType());
            moduleCopyDTO.setSourceId(module.getId());
            if(CopyTypeEnum.SELF.getValue().equals(appCopyDTO.getCopyType())){
                moduleCopyDTO.setTargetId(copyMap.get(module.getId()));
            }else{
                moduleCopyDTO.setTargetId(NumberConst.NUM_0.longValue());
            }
            return moduleCopyDTO;
        }).toList();
    }

    /**
     * 功能描述:
     * 〈复制应用下顶层模块〉
     * @param copyDTO copyDTO
     * @author 蝉鸣
     */
    public Boolean copyAppModule(AppModuleBaseCopyDTO copyDTO) {
        return appModuleBaseService.copyModuleBase(copyDTO);
    }

}