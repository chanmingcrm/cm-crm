package com.platform.mesh.app.biz.modules.app.formcolumn.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import com.platform.mesh.app.api.modules.app.constant.AppConst;
import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formbase.service.IAppFormBaseService;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.dto.AppFormColumnSetActionDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.po.AppFormColumnSetAction;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.vo.AppFormColumnSetActionVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.service.IAppFormColumnSetActionService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.dto.AppFormColumnSetEventDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.vo.AppFormColumnSetEventVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service.IAppFormColumnSetEventService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.domain.po.AppFormColumnSetProcess;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.domain.vo.AppFormColumnSetProcessVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.service.IAppFormColumnSetProcessService;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.vo.AppFormColumnSetRequireVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.service.IAppFormColumnSetRequireService;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.dto.AppFormColumnSortingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.service.IAppFormColumnSortingService;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.service.IAppModuleBaseService;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.es.util.EsUtil;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.mybatis.plus.enums.MateFillEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.RouteParamsBO;
import com.platform.mesh.upms.api.modules.sys.menu.feign.RemoteSysMenuService;
import com.platform.mesh.utils.format.TreeUtil;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 单字段关联
 * @author 蝉鸣
 */
@Service
public class AppFormColumnServiceManual{

    private final static Logger log = LoggerFactory.getLogger(AppFormColumnServiceManual.class);

    @Autowired
    private IAppFormColumnSetActionService appFormColumnSetActionService;

    @Autowired
    private IAppFormColumnSetEventService appFormColumnSetEventService;

    @Autowired
    private IAppFormColumnSetProcessService appFormColumnSetProcessService;

    @Autowired
    private IAppFormColumnSetRequireService appFormColumnSetRequireService;

    @Autowired
    private IAppFormColumnSortingService appFormColumnSortingService;

    @Autowired
    private IAppFormBaseService appFormBaseService;

    @Autowired
    private RemoteSysMenuService remoteSysMenuService;

    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param appFormColumns appFormColumns
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    public List<AppFormColumnVO> getFormColumnTree(List<AppFormColumn> appFormColumns) {
        if(CollUtil.isEmpty(appFormColumns)){
            return CollUtil.newArrayList();
        }
        //转换VO
        List<AppFormColumnVO> appFormColumnVOS = BeanUtil.copyToList(appFormColumns, AppFormColumnVO.class);
        return TreeUtil.packageTree(NumberConst.NUM_0.longValue(), appFormColumnVOS);
    }

    /**
     * 功能描述:
     * 〈新增单字段关联〉
     * @param formColumns formColumns
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, Property> getFormColumnEsMapping(List<AppFormColumn> formColumns) {
        if(CollUtil.isEmpty(formColumns)){
            return new HashMap<>();
        }
        //转化Es类型
        Map<String, Property> propertyMap = formColumns.stream()
                .filter(column -> YesOrNoEnum.YES.getValue().equals(column.getEsInit()))
                .peek(column -> {
                    if (StrUtil.isBlank(column.getEsKind())) {
                        column.setEsKind(EsUtil.getDefaultEsKind(column.getColumnMac()).name());
                    }
                })
                .collect(Collectors.toMap(AppFormColumn::getColumnMac, column -> EsUtil.getPropertyByKind(column.getEsKind()), (v1, v2) -> v2));
        //添加固定字段映射
        propertyMap.putAll(getFixColumnEsMapping());
        return propertyMap;
    }

    /**
     * 功能描述:
     * 〈固定字段映射〉
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, Property> getFixColumnEsMapping() {
        //转化Es类型
        Map<String, Property> propertyMap = new HashMap<>();
        propertyMap.put(StrConst.ID, EsUtil.getPropertyByKind(Property.Kind.Long.name()));
        propertyMap.put(StrConst.MODULE_ID, EsUtil.getPropertyByKind(Property.Kind.Long.name()));
        propertyMap.put(StrConst.DATA_TYPE, EsUtil.getPropertyByKind(Property.Kind.Integer.name()));
        propertyMap.put(StrConst.DATA_SERIAL, EsUtil.getPropertyByKind(Property.Kind.Integer.name()));
        propertyMap.put(StrConst.DATA_PERIOD, EsUtil.getPropertyByKind(Property.Kind.Integer.name()));
        propertyMap.put(MateFillEnum.CREATE_USER_ID.getDesc(), EsUtil.getPropertyByKind(Property.Kind.Long.name()));
        propertyMap.put(MateFillEnum.CREATE_TIME.getDesc(), EsUtil.getPropertyByKind(Property.Kind.Date.name()));
        propertyMap.put(MateFillEnum.UPDATE_USER_ID.getDesc(), EsUtil.getPropertyByKind(Property.Kind.Long.name()));
        propertyMap.put(MateFillEnum.UPDATE_TIME.getDesc(), EsUtil.getPropertyByKind(Property.Kind.Date.name()));
        propertyMap.put(MateFillEnum.SCOPE_USER_ID.getDesc(), EsUtil.getPropertyByKind(Property.Kind.Long.name()));
        propertyMap.put(MateFillEnum.SCOPE_ORG_ID.getDesc(), EsUtil.getPropertyByKind(Property.Kind.Long.name()));
        return propertyMap;
    }


    /**
     * 功能描述:
     * 〈批量新增字段动作〉
     * @param appFormColumnDTO appFormColumnDTO
     * @author 蝉鸣
     */
    public Boolean saveOrUpdateSetAction(AppFormColumnDTO appFormColumnDTO) {
        if(ObjectUtil.isEmpty(appFormColumnDTO) || CollUtil.isEmpty(appFormColumnDTO.getActionList())){
            return Boolean.FALSE;
        }
        appFormColumnSetActionService.lambdaUpdate()
                .eq(AppFormColumnSetAction::getModuleId, appFormColumnDTO.getModuleId())
                .eq(AppFormColumnSetAction::getFormId, appFormColumnDTO.getFormId())
                .eq(AppFormColumnSetAction::getColumnId, appFormColumnDTO.getId())
                .remove();
        //增加动作
        List<AppFormColumnSetAction> actions = appFormColumnDTO.getActionList().stream()
                .map(actionDTO -> {
                    AppFormColumnSetAction columnSetAction = BeanUtil.copyProperties(appFormColumnDTO
                            , AppFormColumnSetAction.class
                            , ObjFieldUtil.getFieldName(AppFormColumnSetAction::getId));
                    BeanUtil.copyProperties(actionDTO, columnSetAction);
                    columnSetAction.setColumnId(appFormColumnDTO.getId());
                    return columnSetAction;
                }).toList();
        //只保存非系统动作
        List<AppFormColumnSetAction> sysActions = appFormColumnSetActionService.lambdaQuery()
                .eq(AppFormColumnSetAction::getModuleId, appFormColumnDTO.getModuleId())
                .eq(AppFormColumnSetAction::getFormId, appFormColumnDTO.getFormId())
                .eq(AppFormColumnSetAction::getColumnId, appFormColumnDTO.getId())
                .list();
        List<Long> actionIds = sysActions.stream().map(AppFormColumnSetAction::getId).toList();
        List<AppFormColumnSetAction> customActions = actions.stream().filter(action ->!actionIds.contains(action.getId())).toList();
        if(CollUtil.isNotEmpty(customActions)){
            appFormColumnSetActionService.saveBatch(customActions);
        }
        //赋值ID
        Map<String, AppFormColumnSetAction> actionMap = actions.stream().collect(Collectors.toMap(AppFormColumnSetAction::getActionHash, Function.identity()));
        for (AppFormColumnSetActionDTO actionDTO : appFormColumnDTO.getActionList()) {
            if (actionMap.containsKey(actionDTO.getActionHash())) {
                actionDTO.setId(actionMap.get(actionDTO.getActionHash()).getId());
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈批量新增字段事件〉
     * @param appFormColumnDTO appFormColumnDTO
     * @author 蝉鸣
     */
    public Boolean saveOrUpdateSetEvent(AppFormColumnDTO appFormColumnDTO) {
        if(ObjectUtil.isEmpty(appFormColumnDTO) || CollUtil.isEmpty(appFormColumnDTO.getEventList())){
            return Boolean.FALSE;
        }
        //如果存在则删除
        appFormColumnSetEventService.lambdaUpdate()
                .eq(AppFormColumnSetEvent::getModuleId, appFormColumnDTO.getModuleId())
                .eq(AppFormColumnSetEvent::getFormId, appFormColumnDTO.getFormId())
                .eq(AppFormColumnSetEvent::getColumnId, appFormColumnDTO.getId())
                .remove();
        //获取动作信息
        Map<String, AppFormColumnSetActionDTO> actionDTOMap = new HashMap<>();
        if (CollUtil.isNotEmpty(appFormColumnDTO.getActionList())) {
            actionDTOMap = appFormColumnDTO.getActionList().stream().collect(Collectors.toMap(AppFormColumnSetActionDTO::getActionHash, Function.identity()));
        }
        //增加事件
        Map<String, AppFormColumnSetActionDTO> finalActionDTOMap = actionDTOMap;
        List<AppFormColumnSetEvent> events = appFormColumnDTO.getEventList().stream().map(eventDTO -> {
            AppFormColumnSetEvent columnSetEvent = BeanUtil.copyProperties(appFormColumnDTO
                    , AppFormColumnSetEvent.class
                    , ObjFieldUtil.getFieldName(AppFormColumnSetAction::getId));
//            BeanUtil.copyProperties(eventDTO, columnSetEvent, ObjFiledUtil.getFieldName(AppFormColumnSetEvent::getEventFlow));
            BeanUtil.copyProperties(eventDTO, columnSetEvent);
            columnSetEvent.setColumnId(appFormColumnDTO.getId());
            //补充动作信息
            if(ObjectUtil.isNotEmpty(finalActionDTOMap) && finalActionDTOMap.containsKey(eventDTO.getActionHash())){
                AppFormColumnSetActionDTO actionDTO = finalActionDTOMap.get(eventDTO.getActionHash());
                columnSetEvent.setActionId(actionDTO.getId());
            }
            return columnSetEvent;
        }).toList();
        //只保存非系统事件
        List<AppFormColumnSetEvent> sysEvents = appFormColumnSetEventService.lambdaQuery()
                .eq(AppFormColumnSetEvent::getModuleId, appFormColumnDTO.getModuleId())
                .eq(AppFormColumnSetEvent::getFormId, appFormColumnDTO.getFormId())
                .eq(AppFormColumnSetEvent::getColumnId, appFormColumnDTO.getId())
                .list();
        List<Long> eventIds = sysEvents.stream().map(AppFormColumnSetEvent::getId).toList();
        List<AppFormColumnSetEvent> customEvents = events.stream().filter(event ->!eventIds.contains(event.getId())).toList();
        if(CollUtil.isNotEmpty(customEvents)){
            appFormColumnSetEventService.saveBatch(customEvents);
        }
        //赋值ID
        Map<String, AppFormColumnSetEvent> eventMap = events.stream().collect(Collectors.toMap(AppFormColumnSetEvent::getEventHash, Function.identity()));
        for (AppFormColumnSetEventDTO eventDTO : appFormColumnDTO.getEventList()) {
            if (eventMap.containsKey(eventDTO.getEventHash())) {
                eventDTO.setId(eventMap.get(eventDTO.getEventHash()).getId());
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈批量新增流程〉
     * @param appFormColumnDTO appFormColumnDTO
     * @author 蝉鸣
     */
    public Boolean saveOrUpdateSetProcess(AppFormColumnDTO appFormColumnDTO) {
        if(ObjectUtil.isEmpty(appFormColumnDTO) || CollUtil.isEmpty(appFormColumnDTO.getProcessList())){
            return Boolean.FALSE;
        }
        //如果存在则删除
        appFormColumnSetProcessService.lambdaUpdate()
                .eq(AppFormColumnSetProcess::getModuleId, appFormColumnDTO.getModuleId())
                .eq(AppFormColumnSetProcess::getFormId, appFormColumnDTO.getFormId())
                .eq(AppFormColumnSetProcess::getColumnId, appFormColumnDTO.getId())
                .remove();
        //获取事件信息
        Map<String, AppFormColumnSetEventDTO> eventDTOMap = appFormColumnDTO.getEventList().stream().collect(Collectors.toMap(AppFormColumnSetEventDTO::getEventHash, Function.identity()));
        //增加请求
        List<AppFormColumnSetProcess> processes = appFormColumnDTO.getProcessList().stream().map(processDTO -> {
            List<AppFormColumnSetProcess> processList = CollUtil.newArrayList();
            AppFormColumnSetProcess columnSetProcess = BeanUtil.copyProperties(appFormColumnDTO
                    , AppFormColumnSetProcess.class
                    , ObjFieldUtil.getFieldName(AppFormColumnSetProcess::getId));
            BeanUtil.copyProperties(processDTO, columnSetProcess, ObjFieldUtil.ignoreDefault());
            columnSetProcess.setColumnId(appFormColumnDTO.getId());
            //补充事件信息
            if(eventDTOMap.containsKey(processDTO.getEventHash())){
                AppFormColumnSetEventDTO eventDTO = eventDTOMap.get(processDTO.getEventHash());
                columnSetProcess.setActionId(eventDTO.getActionId());
                columnSetProcess.setEventId(eventDTO.getId());
            }
            if(ObjectUtil.isNotEmpty(columnSetProcess.getTempProcessId())){
                processList.add(columnSetProcess);
            }
            return processList;
        }).flatMap(List::stream).toList();
        if(CollUtil.isNotEmpty(processes)){
            appFormColumnSetProcessService.saveBatch(processes);
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈批量新增字段请求〉
     * @param appFormColumnDTO appFormColumnDTO
     * @author 蝉鸣
     */
    public Boolean saveOrUpdateSetRequire(AppFormColumnDTO appFormColumnDTO) {
        if(ObjectUtil.isEmpty(appFormColumnDTO) || CollUtil.isEmpty(appFormColumnDTO.getRequireList())){
            return Boolean.FALSE;
        }
        //如果存在则删除
        appFormColumnSetRequireService.lambdaUpdate()
                .eq(AppFormColumnSetRequire::getModuleId, appFormColumnDTO.getModuleId())
                .eq(AppFormColumnSetRequire::getFormId, appFormColumnDTO.getFormId())
                .eq(AppFormColumnSetRequire::getColumnId, appFormColumnDTO.getId())
                .remove();
        //增加请求
        List<AppFormColumnSetRequire> requires = appFormColumnDTO.getRequireList().stream().map(requireDTO -> {
            AppFormColumnSetRequire columnSetRequire = BeanUtil.copyProperties(appFormColumnDTO
                    , AppFormColumnSetRequire.class
                    , ObjFieldUtil.getFieldName(AppFormColumnSetAction::getId));
            BeanUtil.copyProperties(requireDTO, columnSetRequire);
            columnSetRequire.setColumnId(appFormColumnDTO.getId());
            return columnSetRequire;
        }).toList();
        //只保存非系统请求
        List<AppFormColumnSetRequire> sysRequire = appFormColumnSetRequireService.lambdaQuery()
                .eq(AppFormColumnSetRequire::getModuleId, appFormColumnDTO.getModuleId())
                .eq(AppFormColumnSetRequire::getFormId, appFormColumnDTO.getFormId())
                .eq(AppFormColumnSetRequire::getColumnId, appFormColumnDTO.getId())
                .list();
        List<Long> requireIds = sysRequire.stream().map(AppFormColumnSetRequire::getId).toList();
        List<AppFormColumnSetRequire> customRequires = requires.stream().filter(require ->!requireIds.contains(require.getId())).toList();
        if(CollUtil.isNotEmpty(customRequires)){
            appFormColumnSetRequireService.saveBatch(customRequires);
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈批量新增字段请求〉
     * @param appFormColumns appFormColumns
     * @author 蝉鸣
     */
    public List<AppFormColumnSortingDTO> getSetSorting(Long batchId, List<AppFormColumn> appFormColumns) {
        if(CollUtil.isEmpty(appFormColumns)){
            return CollUtil.newArrayList();
        }
        return appFormColumns.stream().map(column -> {
            AppFormColumnSortingDTO sortingDTO = new AppFormColumnSortingDTO();
            BeanUtil.copyProperties(column, sortingDTO, ObjFieldUtil.ignoreDefault());
            sortingDTO.setBatchId(batchId);
            sortingDTO.setParentColumnId(column.getParentId());
            sortingDTO.setColumnId(column.getId());
            return sortingDTO;
        }).toList();
    }

    /**
     * 功能描述:
     * 〈批量新增字段请求〉
     * @param columnSortingDTOS columnSortingDTOS
     * @author 蝉鸣
     */
    public void saveOrUpdateSetSorting(List<AppFormColumnSortingDTO> columnSortingDTOS) {
        if(CollUtil.isEmpty(columnSortingDTOS)){
            return;
        }
        appFormColumnSortingService.addFormColumnSorting(columnSortingDTOS);
    }

    /**
     * 功能描述:
     * 〈批量新增修改菜单〉
     * @param appFormColumns appFormColumns
     * @author 蝉鸣
     */
    public void saveOrUpdateMenu(List<AppFormColumn> appFormColumns) {
        if(CollUtil.isEmpty(appFormColumns)){
            return;
        }
        //去重表单ID
        List<Long> formIds = appFormColumns.stream().map(AppFormColumn::getFormId).distinct().toList();
        //获取所有的页面类型
        List<Integer> pageList = FormTypeEnum.getValueByCode(FormTypeEnum.PAGE_LIST.getCode());
        //获取列表页面
        List<AppFormBase> pageForms = appFormBaseService.lambdaQuery()
                .in(AppFormBase::getId, formIds)
                .in(AppFormBase::getFormType, pageList)
                .list();
        if(CollUtil.isEmpty(pageForms)){
            return;
        }
        //获取列表页面ID
        List<Long> pageFormIds = pageForms.stream().map(AppFormBase::getId).distinct().toList();
        //获取需要转化菜单的组件类型
        List<String> buttons = CollUtil.newArrayList(CompMacEnum.DIALOG.getDesc(),CompMacEnum.BUTTON.getDesc());
        appFormColumns.stream()
            .filter(column-> pageFormIds.contains(column.getFormId()))
            .filter(column-> buttons.contains(column.getCompMac()))
            .forEach(column->{
                 AppMenuBO appMenuBO = new AppMenuBO();
                 appMenuBO.setTitle(column.getColumnName());
                 appMenuBO.setParentModuleId(column.getModuleId());
                 appMenuBO.setModuleId(column.getModuleId());
                 appMenuBO.setRelId(column.getId());
                 appMenuBO.setOperateType(OperateTypeEnum.UPDATE.getValue());
                 appMenuBO.setMenuType(MenuTypeEnum.COMPONENT.getValue());
                 List<RouteParamsBO> params = CollUtil.newArrayList();
                 RouteParamsBO routeParamsBO = new RouteParamsBO();
                 routeParamsBO.setKey(AppConst.MODULE_ID);
                 routeParamsBO.setValue(String.valueOf(column.getModuleId()));
                 params.add(routeParamsBO);
                 appMenuBO.setParams(params);
                 remoteSysMenuService.addOrEditMenu(appMenuBO);
        });
    }

    /**
     * 功能描述:
     * 〈获取子模块〉
     * @param moduleId moduleId
     * @param formId formId
     * @author 蝉鸣
     */
    public List<Long> getChildModuleIds(Long moduleId,Long formId) {
        //获取fromId=0 模块下的所有子模块
        if(!formId.equals(NumberConst.NUM_0.longValue())){
            return CollUtil.newArrayList();
        }
        IAppModuleBaseService appModuleBaseService = SpringContextHolderUtil.getBean(IAppModuleBaseService.class);
        List<AppModuleBase> appModuleBases = appModuleBaseService.lambdaQuery().eq(AppModuleBase::getParentId, moduleId).list();
        if(CollUtil.isEmpty(appModuleBases)){
            return CollUtil.newArrayList();
        }
        return appModuleBases.stream().map(AppModuleBase::getId).toList();
    }


    /**
     * 功能描述:
     * 〈获取字段补充信息〉
     * @param appFormColumns appFormColumns
     * @author 蝉鸣
     */
    public List<AppFormColumnVO> getAppFormColumnVO(List<AppFormColumn> appFormColumns) {
        if(CollUtil.isEmpty(appFormColumns)){
            return CollUtil.newArrayList();
        }
        //转换VO对象
        List<AppFormColumnVO> appFormColumnVOS = this.getVO(appFormColumns);
        //补充动作信息
        FutureHandleUtil.runNoResult(appFormColumnVOS,this::getSetAction);
        //补充事件信息
        FutureHandleUtil.runNoResult(appFormColumnVOS,this::getSetEvent);
        //补充流程信息
        FutureHandleUtil.runNoResult(appFormColumnVOS,this::getSetProcess);
        //补充请求信息
        FutureHandleUtil.runNoResult(appFormColumnVOS,this::getSetRequire);
        return appFormColumnVOS;
    }

    /**
     * 功能描述:
     * 〈获取VO对象动作〉
     * @param appFormColumns appFormColumns
     * @author 蝉鸣
     */
    public List<AppFormColumnVO> getVO(List<AppFormColumn> appFormColumns) {
        if(CollUtil.isEmpty(appFormColumns)){
            return CollUtil.newArrayList();
        }
        return appFormColumns.stream().map(column->{
            AppFormColumnVO appFormColumnVO = BeanUtil.copyProperties(column, AppFormColumnVO.class);
            appFormColumnVO.setCanEditFlag(YesOrNoEnum.YES.getValue());
            appFormColumnVO.setSysFlag(YesOrNoEnum.NO.getValue());
            return appFormColumnVO;
        }).toList();
    }

    /**
     * 功能描述:
     * 〈获取字段动作〉
     * @param appFormColumnVOs appFormColumnVOs
     * @author 蝉鸣
     */
    public void getSetAction(List<AppFormColumnVO> appFormColumnVOs) {
        if(CollUtil.isEmpty(appFormColumnVOs)){
            return;
        }
        AppFormColumnVO columnVO = CollUtil.getFirst(appFormColumnVOs);
        //获取动作
        List<AppFormColumnSetAction> setActions = appFormColumnSetActionService.lambdaQuery()
                .eq(AppFormColumnSetAction::getModuleId, columnVO.getModuleId())
                .eq(AppFormColumnSetAction::getFormId, columnVO.getFormId())
                .list();
        if(CollUtil.isEmpty(setActions)){
            return;
        }
        Map<Long, List<AppFormColumnSetAction>> actionMap = setActions.stream()
                .filter(item->ObjectUtil.isNotEmpty(item.getColumnId()))
                .collect(Collectors.groupingBy(AppFormColumnSetAction::getColumnId));
        for (AppFormColumnVO formColumnVO : appFormColumnVOs) {
            if(actionMap.containsKey(formColumnVO.getId())){
                List<AppFormColumnSetActionVO> actionVOS = BeanUtil.copyToList(actionMap.get(formColumnVO.getId()), AppFormColumnSetActionVO.class);
                formColumnVO.setActionList(actionVOS);
            }
        }
    }


    /**
     * 功能描述:
     * 〈获取字段事件〉
     * @param appFormColumnVOs appFormColumnVOs
     * @author 蝉鸣
     */
    public void getSetEvent(List<AppFormColumnVO> appFormColumnVOs) {
        if(CollUtil.isEmpty(appFormColumnVOs)){
            return;
        }
        AppFormColumnVO columnVO = CollUtil.getFirst(appFormColumnVOs);
        //获取动作
        List<AppFormColumnSetEvent> setEvents = appFormColumnSetEventService.lambdaQuery()
                .eq(AppFormColumnSetEvent::getModuleId, columnVO.getModuleId())
                .eq(AppFormColumnSetEvent::getFormId, columnVO.getFormId())
                .list();
        if(CollUtil.isEmpty(setEvents)){
            return;
        }
        Map<Long, List<AppFormColumnSetEvent>> eventMap = setEvents.stream()
                .filter(item->ObjectUtil.isNotEmpty(item.getColumnId()))
                .collect(Collectors.groupingBy(AppFormColumnSetEvent::getColumnId));
        CopyOptions options = CopyOptions.create();
        for (AppFormColumnVO formColumnVO : appFormColumnVOs) {
            if(eventMap.containsKey(formColumnVO.getId())){
//                options.setIgnoreProperties(ObjFiledUtil.getFieldName(AppFormColumnSetEvent::getEventFlow));
                List<AppFormColumnSetEventVO> eventVOS = BeanUtil.copyToList(eventMap.get(formColumnVO.getId()), AppFormColumnSetEventVO.class,options);
                formColumnVO.setEventList(eventVOS);
            }
        }
    }


    /**
     * 功能描述:
     * 〈获取流程〉
     * @param appFormColumnVOs appFormColumnVOs
     * @author 蝉鸣
     */
    public void getSetProcess(List<AppFormColumnVO> appFormColumnVOs) {
        if(CollUtil.isEmpty(appFormColumnVOs)){
            return;
        }
        AppFormColumnVO columnVO = CollUtil.getFirst(appFormColumnVOs);
        //获取请求
        List<AppFormColumnSetProcess> setProcesses = appFormColumnSetProcessService.lambdaQuery()
                .eq(AppFormColumnSetProcess::getModuleId, columnVO.getModuleId())
                .eq(AppFormColumnSetProcess::getFormId, columnVO.getFormId())
                .list();
        if(CollUtil.isEmpty(setProcesses)){
            return;
        }
        Map<Long, List<AppFormColumnSetProcess>> processMap = setProcesses.stream()
                .filter(item->ObjectUtil.isNotEmpty(item.getColumnId()))
                .sorted(Comparator.comparing(AppFormColumnSetProcess::getCreateTime).reversed())
                .collect(Collectors.groupingBy(AppFormColumnSetProcess::getColumnId));
        CopyOptions options = CopyOptions.create();
        for (AppFormColumnVO formColumnVO : appFormColumnVOs) {
            if(processMap.containsKey(formColumnVO.getId())){
//                options.setIgnoreProperties(ObjFiledUtil.getFieldName(AppFormColumnSetEvent::getEventFlow));
                List<AppFormColumnSetProcessVO> eventVOS = BeanUtil.copyToList(processMap.get(formColumnVO.getId()), AppFormColumnSetProcessVO.class,options);
                formColumnVO.setProcessList(eventVOS);
            }
        }
    }


    /**
     * 功能描述:
     * 〈获取字段请求〉
     * @param appFormColumnVOs appFormColumnVOs
     * @author 蝉鸣
     */
    public void getSetRequire(List<AppFormColumnVO> appFormColumnVOs) {
        if(CollUtil.isEmpty(appFormColumnVOs)){
            return;
        }
        AppFormColumnVO columnVO = CollUtil.getFirst(appFormColumnVOs);
        //获取请求
        List<AppFormColumnSetRequire> setRequires = appFormColumnSetRequireService.lambdaQuery()
                .eq(AppFormColumnSetRequire::getModuleId, columnVO.getModuleId())
                .eq(AppFormColumnSetRequire::getFormId, columnVO.getFormId())
                .list();
        if(CollUtil.isEmpty(setRequires)){
            return;
        }
        Map<Long, List<AppFormColumnSetRequireVO>> requireMap = setRequires.stream()
                .filter(item->ObjectUtil.isNotEmpty(item.getColumnId()))
                .map(item->BeanUtil.copyProperties(item,AppFormColumnSetRequireVO.class))
                .collect(Collectors.groupingBy(AppFormColumnSetRequireVO::getColumnId));
        for (AppFormColumnVO formColumnVO : appFormColumnVOs) {
            if(requireMap.containsKey(formColumnVO.getId())){
                //Map->请求Hash标识,请求环境
                Map<String, List<AppFormColumnSetRequireVO>> hashMap = requireMap.get(formColumnVO.getId()).stream()
                        .collect(Collectors.groupingBy(AppFormColumnSetRequireVO::getRequireHash));
                //转换请求数据
                List<AppFormColumnSetRequireVO> requireList = appFormColumnSetRequireService.getServiceManual().getFormColumnSetRequireInfo(hashMap);
                formColumnVO.setRequireList(requireList);
            }
        }
    }

    /**
     * 功能描述:
     * 〈获取表单〉
     * @param moduleId moduleId
     * @param formType formType
     * @author 蝉鸣
     */
    public AppFormBase getAppFormBaseByFormType(Long moduleId, Integer formType) {
        return appFormBaseService.getAppFormBaseByFormType(moduleId, formType);
    }



}
