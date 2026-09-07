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
import java.util.stream.Collectors;


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
     * 〈发布模块〉
     * @param moduleIds moduleIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean initModuleBaseToES(String moduleIndex,List<Long> moduleIds) {
        if(StrUtil.isEmpty(moduleIndex)){
            return false;
        }
        //查询是否存在当前模块索引
        boolean existIndex = esIndexService.existIndex(CollUtil.newArrayList(moduleIndex));
        if(existIndex){
            return false;
        }
        //初始化索引
        boolean createIndex = esIndexService.createIndex(moduleIndex);
        //初始化字段映射
        boolean setMapping = esIndexService.setMapping(appFormColumnService.getFormColumnEsMapping(moduleIndex, moduleIds));
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
        appMenuBO.setIcon(appModuleBase.getModuleLogo());
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
     * 〈初始化模块下的表单〉
     * @param appModuleBase appModuleBase
     * @author 蝉鸣
     */
    public Map<Long, Long> addInitForm(AppModuleBase appModuleBase) {
        Map<Long, Long> initForm = new HashMap<>();
        //分类类型需要初始化
        if(!ModuleTypeEnum.CATEGORY.getValue().equals(appModuleBase.getModuleType())){
            return initForm;
        }
        //查询
        List<AppFormBase> appFormBases = appFormBaseService.lambdaQuery()
                .eq(AppFormBase::getModuleId, NumberConst.NUM_0.longValue())
                .list();
        if(CollUtil.isEmpty(appFormBases)){
            return initForm;
        }
        List<AppFormBase> moduleFormBases = appFormBases.stream().map(formBase->{
            Long formBaseId = IdUtil.getSnowflake().nextId();
            initForm.put(formBase.getId(), formBaseId);
            AppFormBase appFormBase = new AppFormBase();
            BeanUtil.copyProperties(formBase,appFormBase,ObjFieldUtil.ignoreDefault());
            appFormBase.setId(formBaseId);
            appFormBase.setModuleId(appModuleBase.getId());
            return appFormBase;
        }).toList();
        appFormBaseService.saveBatch(moduleFormBases);
        return initForm;
    }

    /**
     * 功能描述:
     * 〈初始化模块下的字段〉
     * @param appModuleBase appModuleBase
     * @author 蝉鸣
     */
    public Map<AppFormColumn, AppFormColumn> addInitColumn(AppModuleBase appModuleBase,Map<Long, Long> formMap,Map<Long, AppFormColumnSetRequire> copyRequire) {
        Map<AppFormColumn, AppFormColumn> initColumn = new HashMap<>();
        //分类类型需要初始化
        List<AppFormColumn> appFormColumns = CollUtil.newArrayList();
        if(ModuleTypeEnum.MODULE.getValue().equals(appModuleBase.getModuleType())){
            //查询
            appFormColumns = appFormColumnService.lambdaQuery()
                    .eq(AppFormColumn::getModuleId, NumberConst.NUM_0.longValue())
                    .eq(AppFormColumn::getFormId, NumberConst.NUM_0.longValue())
                    .list();
            if(CollUtil.isEmpty(appFormColumns)){
                return initColumn;
            }
        }else if(ModuleTypeEnum.CATEGORY.getValue().equals(appModuleBase.getModuleType())){
            //查询
            appFormColumns = appFormColumnService.lambdaQuery()
                    .eq(AppFormColumn::getModuleId, NumberConst.NUM_0.longValue())
                    .ne(AppFormColumn::getFormId, NumberConst.NUM_0.longValue())
                    .list();
            if(CollUtil.isEmpty(appFormColumns)){
                return initColumn;
            }
        }
        //拷贝字段
        return appFormColumnService.copyFormColumn(appFormColumns,appModuleBase,formMap,copyRequire);
    }

    /**
     * 功能描述:
     * 〈初始化模块下的动作〉
     * @param appModuleBase appModuleBase
     * @author 蝉鸣
     */
    public void addInitColumnSetAction(AppModuleBase appModuleBase, Map<Long, AppFormColumn> initColumn) {
        //分类类型需要初始化
        if(!ModuleTypeEnum.CATEGORY.getValue().equals(appModuleBase.getModuleType())){
            return;
        }
        appFormColumnSetActionService.copyFormColumnSetAction(NumberConst.NUM_0.longValue(),appModuleBase.getId(),initColumn);
    }

    /**
     * 功能描述:
     * 〈初始化模块下的事件〉
     * @param appModuleBase appModuleBase
     * @author 蝉鸣
     */
    public void addInitColumnSetEvent(AppModuleBase appModuleBase, Map<AppFormColumn, AppFormColumn> initColumn, Map<Long, AppFormColumnSetRequire> copyRequire) {
        //分类类型需要初始化
        if(!ModuleTypeEnum.CATEGORY.getValue().equals(appModuleBase.getModuleType())){
            return;
        }
        appFormColumnSetEventService.copyFormColumnSetEvent(NumberConst.NUM_0.longValue(),appModuleBase.getId(),initColumn,copyRequire);
    }

    /**
     * 功能描述:
     * 〈获取新旧字段ID map〉
     * @param initColumn initColumn
     * @author 蝉鸣
     */
    public Map<Long, AppFormColumn> getColumnLongMap(Map<AppFormColumn, AppFormColumn> initColumn) {
        //分类类型需要初始化
        if(CollUtil.isEmpty(initColumn)){
            return new HashMap<>();
        }
        Map<Long, AppFormColumn> resultMap = new HashMap<>(initColumn.size());
        initColumn.forEach((key, value) -> resultMap.put(key.getId(), value));
        return resultMap;
    }

    /**
     * 功能描述:
     * 〈初始化模块下的对接接口〉
     * @param appModuleBase appModuleBase
     * @author 蝉鸣
     */
    public Map<Long, AppFormColumnSetRequire> addInitColumnSetRequire(AppModuleBase appModuleBase) {
        //分类类型需要初始化
        if(!ModuleTypeEnum.CATEGORY.getValue().equals(appModuleBase.getModuleType())){
            return new HashMap<>();
        }
        return appFormColumnSetRequireService.copyFormColumnSetRequire(NumberConst.NUM_0.longValue(),appModuleBase.getId());
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
        Map<String, List<AppModuleBase>> moduleMap = esModuleBases.stream()
                .collect(Collectors.groupingBy(AppModuleBase::getModuleIndex));
        moduleMap.forEach((key,modules)->{
            //获取父模块ID,用于获取字段池字段
            List<Long> parentIds = modules.stream().map(AppModuleBase::getParentId).distinct().toList();
            Boolean initSuccess = this.initModuleBaseToES(key,parentIds);
            if(initSuccess){
                initModule.addAll(modules);
            }
        });
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
     * @param sourceId sourceId
     * @param target target
     * @author 蝉鸣
     */
    public void copyModuleBase(Long sourceId, AppModuleBase target) {
        //添加菜单
        this.addOrEditMenu(target, OperateTypeEnum.INSERT);
        //复制表单
        Map<Long,Long> copyForm = appFormBaseService.copyFormBase(sourceId,target.getId());
        //复制字段请求
        Map<Long, AppFormColumnSetRequire> copyRequire = appFormColumnSetRequireService.copyFormColumnSetRequire(sourceId,target.getId());
        //复制字段
        Map<AppFormColumn, AppFormColumn> copyColumn = appFormColumnService.copyFormColumn(sourceId,target,copyForm,copyRequire);
        Map<Long, AppFormColumn> columnLongMap = this.getColumnLongMap(copyColumn);
        if(CollUtil.isEmpty(copyColumn)){
            return;
        }
        //复制字段动作
        appFormColumnSetActionService.copyFormColumnSetAction(sourceId,target.getId(),columnLongMap);
        //复制字段事件
        Map<Long, AppFormColumnSetEvent> copyEvent = appFormColumnSetEventService.copyFormColumnSetEvent(sourceId, target.getId(), copyColumn, copyRequire);
        //复制字段流程
        appFormColumnSetProcessService.copyFormColumnSetProcess(sourceId,target.getId(),copyEvent);
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