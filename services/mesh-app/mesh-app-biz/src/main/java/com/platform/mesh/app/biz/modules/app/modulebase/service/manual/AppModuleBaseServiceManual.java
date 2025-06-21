package com.platform.mesh.app.biz.modules.app.modulebase.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.app.api.modules.app.constant.AppConst;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.api.modules.app.enums.comp.ModuleTypeEnum;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formbase.service.IAppFormBaseService;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.IAppFormColumnService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.po.AppFormColumnSetAction;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.service.IAppFormColumnSetActionService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service.IAppFormColumnSetEventService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.domain.po.AppFormColumnSetProcess;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.service.IAppFormColumnSetProcessService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service.IAppFormColumnSetRequireService;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleBaseVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.es.service.IEsIndexService;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseBO;
import com.platform.mesh.upms.api.modules.dict.base.feign.RemoteDictService;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.RouteParamsBO;
import com.platform.mesh.upms.api.modules.sys.menu.feign.RemoteSysMenuService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 模块
 * @author 蝉鸣
 */
@Service
public class AppModuleBaseServiceManual{

    private static final Logger log = LoggerFactory.getLogger(AppModuleBaseServiceManual.class);

    @Autowired
    private IEsIndexService esIndexService;

    @Autowired
    private IAppFormBaseService appFormBaseService;

    @Autowired
    private IAppFormColumnService appFormColumnService;

    @Autowired
    private IAppFormColumnSetActionService appFormColumnSetActionService;

    @Autowired
    private IAppFormColumnSetEventService appFormColumnSetEventService;

    @Autowired
    private IAppFormColumnSetProcessService appFormColumnSetProcessService;

    @Autowired
    private IAppFormColumnSetRequireService appFormColumnSetRequireService;

    @Autowired
    private RemoteSysMenuService remoteSysMenuService;

    @Autowired
    private RemoteDictService remoteDictService;
    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appModuleBase appModuleBase 
     * @return 正常返回:{@link AppModuleBaseVO}
     * @author 蝉鸣
     */
    public AppModuleBaseVO getModuleBaseInfoById(AppModuleBase appModuleBase) {
        AppModuleBaseVO appModuleBaseVO = new AppModuleBaseVO();
        if(ObjectUtil.isEmpty(appModuleBaseVO)){
            return appModuleBaseVO;
        }
        //转换VO
        BeanUtil.copyProperties(appModuleBase, appModuleBaseVO);
        return appModuleBaseVO;
    }

    /**
     * 功能描述:
     * 〈发布模块〉
     * @param moduleBase moduleBase
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean initModuleBaseToES(AppModuleBase moduleBase) {
        if(StrUtil.isEmpty(moduleBase.getModuleIndex())){
            return false;
        }
        //查询是否存在当前模块索引
        boolean existIndex = esIndexService.existIndex(CollUtil.newArrayList(moduleBase.getModuleIndex()));
        if(existIndex){
            return false;
        }
        //初始化索引
        boolean createIndex = esIndexService.createIndex(moduleBase.getModuleIndex());
        //初始化字段映射
        boolean setMapping = esIndexService.setMapping(appFormColumnService.getFormColumnEsMapping(moduleBase.getParentId(), moduleBase.getModuleIndex()));
        return createIndex && setMapping;
    }

    /***
     * 功能描述:
     * 〈新建或编辑的应用模块同步至菜单〉
     * @param appModuleBase appModuleBase
     * @param operateTypeEnum operateTypeEnum
     * @author 蝉鸣
     */
    public void addOrEditMenu(AppModuleBase appModuleBase, OperateTypeEnum operateTypeEnum) {
        AppMenuBO appMenuBO = new AppMenuBO();
        appMenuBO.setTitle(appModuleBase.getModuleName());
        appMenuBO.setModuleId(appModuleBase.getId());
        appMenuBO.setOperateType(operateTypeEnum.getValue());
        appMenuBO.setParentModuleId(appModuleBase.getParentId());
        if (ModuleTypeEnum.APPLICATION.getValue().equals(appModuleBase.getModuleType())) {
            appMenuBO.setMenuType(MenuTypeEnum.MODULE.getValue());
            //应用层级没有parentId,用appId代替，App在菜单中用的关联moduleId字段也是App模块的主键id
            appMenuBO.setParentModuleId(appModuleBase.getAppId());
            appMenuBO.setRelId(appModuleBase.getId());
        } else if (ModuleTypeEnum.MODULE.getValue().equals(appModuleBase.getModuleType())) {
            appMenuBO.setRelId(appModuleBase.getId());
            appMenuBO.setMenuType(MenuTypeEnum.MENU.getValue());
            //前端菜单层级需要
            appMenuBO.setComponent(AppConst.MENU_ROUTE_PREFIX);
            List<RouteParamsBO> params = CollUtil.newArrayList();
            RouteParamsBO routeParamsBO = new RouteParamsBO();
            routeParamsBO.setKey(AppConst.MODULE_ID);
            routeParamsBO.setValue(String.valueOf(appModuleBase.getId()));
            params.add(routeParamsBO);
            appMenuBO.setParams(params);
        } else if (ModuleTypeEnum.CATEGORY.getValue().equals(appModuleBase.getModuleType())) {
            appMenuBO.setRelId(appModuleBase.getId());
            appMenuBO.setMenuType(MenuTypeEnum.CATEGORY.getValue());
            List<RouteParamsBO> params = CollUtil.newArrayList();
            RouteParamsBO routeParamsBO = new RouteParamsBO();
            routeParamsBO.setKey(AppConst.MODULE_ID);
            routeParamsBO.setValue(String.valueOf(appModuleBase.getId()));
            params.add(routeParamsBO);
            appMenuBO.setParams(params);
        }
        remoteSysMenuService.addOrEditMenu(appMenuBO);
    }

    /**
     * 功能描述:
     * 〈初始化模块下的字段〉
     * @param appModuleBase appModuleBase
     * @author 蝉鸣
     */
    public void addInitColumn(AppModuleBase appModuleBase) {
        //分类类型需要初始化
        if(!ModuleTypeEnum.MODULE.getValue().equals(appModuleBase.getModuleType())){
            return;
        }
        //查询
        List<AppFormColumn> appFormColumns = appFormColumnService.lambdaQuery()
                .eq(AppFormColumn::getModuleId, NumberConst.NUM_0.longValue())
                .eq(AppFormColumn::getFormId, NumberConst.NUM_0.longValue())
                .list();
        if(CollUtil.isEmpty(appFormColumns)){
            return;
        }
        List<AppFormColumn> moduleFormColumns = appFormColumns.stream().map(column->{
            AppFormColumn appFormColumn = new AppFormColumn();
            BeanUtil.copyProperties(column,appFormColumn,ObjFieldUtil.ignoreDefault());
            appFormColumn.setModuleId(appModuleBase.getId());
            appFormColumn.setColumnHash(column.getCompMac().concat(IdUtil.fastSimpleUUID()));
            appFormColumn.setColumnName(appModuleBase.getModuleName().concat(column.getColumnName()));
            return appFormColumn;
        }).toList();
        appFormColumnService.saveBatch(moduleFormColumns);
    }

    /**
     * 功能描述:
     * 〈初始化模块ES〉
     * @param esModuleBases esModuleBases
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public List<AppModuleBase> initEs(List<AppModuleBase> esModuleBases) {
        List<AppModuleBase> initModule = CollUtil.newArrayList();
        if(CollUtil.isEmpty(esModuleBases)){
            return initModule;
        }
        for (AppModuleBase moduleBase : esModuleBases) {
            Boolean initSuccess = initModuleBaseToES(moduleBase);
            if(initSuccess){
                initModule.add(moduleBase);
            }
        }
        return initModule;
    }

    /**
     * 功能描述:
     * 〈删除模块下的配置信息〉
     * @param moduleIds moduleIds
     * @author 蝉鸣
     */
    public void deleteModuleBase(List<Long> moduleIds) {
        if(CollUtil.isEmpty(moduleIds)){
            return;
        }
        //删除菜单
        remoteSysMenuService.appModuleMenuDelete(moduleIds);
    }

    /**
     * 功能描述:
     * 〈强制覆盖清除模块下的配置信息〉
     * @param moduleIds moduleIds
     * @author 蝉鸣
     */
    public void clearModuleBase(List<Long> moduleIds) {
        if(CollUtil.isEmpty(moduleIds)){
            return;
        }
        //删除菜单
        remoteSysMenuService.appModuleMenuClear(moduleIds);
        //清除表单
        appFormBaseService.lambdaUpdate().in(AppFormBase::getModuleId,moduleIds).remove();
        //清除字段
        appFormColumnService.lambdaUpdate().in(AppFormColumn::getModuleId,moduleIds).remove();
        //清除字段映射
        //清除字段动作
        appFormColumnSetActionService.lambdaUpdate().in(AppFormColumnSetAction::getModuleId,moduleIds).remove();
        //清除字段事件
        appFormColumnSetEventService.lambdaUpdate().in(AppFormColumnSetEvent::getModuleId,moduleIds).remove();
        //清除字段流程
        appFormColumnSetProcessService.lambdaUpdate().in(AppFormColumnSetProcess::getModuleId,moduleIds).remove();
        //清除字段请求
        appFormColumnSetRequireService.lambdaUpdate().in(AppFormColumnSetRequire::getModuleId,moduleIds).remove();
    }

    /**
     * 功能描述:
     * 〈拷贝模块〉
     * @param source source
     * @param target target
     * @author 蝉鸣
     */
    public void copyModuleBase(AppModuleBase source, AppModuleBase target) {
        //添加菜单
        this.addOrEditMenu(target, OperateTypeEnum.INSERT);
        //复制表单
        Map<Long,Long> copyForm = appFormBaseService.copyFormBase(source.getId(),target.getId());
        //复制字段
        Map<Long, AppFormColumn> copyColumn = appFormColumnService.copyFormColumn(source,target,copyForm);
        if(CollUtil.isEmpty(copyColumn)){
            return;
        }
        //复制字段映射
        //复制字段动作
        appFormColumnSetActionService.copyFormColumnSetAction(source.getId(),target.getId(),copyColumn);
        //复制字段事件
        Map<Long, AppFormColumnSetEvent> copyEvent = appFormColumnSetEventService.copyFormColumnSetEvent(source.getId(), target.getId(), copyColumn);
        //复制字段流程
        appFormColumnSetProcessService.copyFormColumnSetProcess(source.getId(),target.getId(),copyEvent);
        //复制字段请求
        appFormColumnSetRequireService.copyFormColumnSetRequire(source.getId(),target.getId(),copyColumn);
    }

    /**
     * 功能描述:
     * 〈获取页面组件〉
     * @param moduleId moduleId
     * @author 蝉鸣
     */
    public List<AppFormColumnVO> getFastPageFormColumn(Long moduleId) {
        List<AppFormBase> pageList = appFormBaseService.lambdaQuery()
                .eq(AppFormBase::getModuleId, moduleId)
                .eq(AppFormBase::getFormType, FormTypeEnum.PAGE_LIST.getValue())
                .list();
        if(CollUtil.isEmpty(pageList)){
            return CollUtil.newArrayList();
        }
        AppFormBase defaultPage = CollUtil.getFirst(pageList);
        return appFormColumnService.getFormColumnTree(moduleId,defaultPage.getId());
    }

    /**
     * 功能描述:
     * 〈获取页面组件〉
     * @param pageForm pageForm
     * @author 蝉鸣
     */
    public Map<Long, List<AppFormColumnVO>> getFastPageRelFormColumn(Long moduleId,List<AppFormColumnVO> pageForm) {
        Map<Long, List<AppFormColumnVO>> formColumnMap = new HashMap<>();
        if(CollUtil.isEmpty(pageForm)){
            return formColumnMap;
        }
        pageForm.stream()
                .filter(columnVO->ObjectUtil.isNotEmpty(columnVO.getRelDataValue()) && NumberUtil.isNumber(StrUtil.toString(columnVO.getRelDataValue())))
                .forEach(columnVO -> {
                    Object relDataValue = columnVO.getRelDataValue();
                    Long formId = Long.parseLong(StrUtil.toString(relDataValue));
                    if(!formColumnMap.containsKey(formId)){
                        List<AppFormColumnVO> columnVOS = appFormColumnService.getFormColumnTree(moduleId, formId);
                        formColumnMap.put(formId, columnVOS);
                    }
                });
        return formColumnMap;
    }

    /**
     * 功能描述:
     * 〈获取关联字典〉
     * @param dictIds dictIds
     * @author 蝉鸣
     */
    public List<DictBaseBO> getRelDictPage(List<Long> dictIds) {
        Result<List<DictBaseBO>> listResult = remoteDictService.selectDictByIds(dictIds);
        Optional<List<DictBaseBO>> dict = ResultUtil.of(listResult).getData();
        return dict.orElseGet(CollUtil::newArrayList);
    }

}