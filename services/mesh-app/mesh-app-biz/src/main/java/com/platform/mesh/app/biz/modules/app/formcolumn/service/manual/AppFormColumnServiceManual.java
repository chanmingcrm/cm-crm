package com.platform.mesh.app.biz.modules.app.formcolumn.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.util.EsUtil;
import com.platform.mesh.mybatis.plus.enums.MateFillEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.MenuTypeEnum;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.RouteParamsBO;
import com.platform.mesh.upms.api.modules.sys.menu.feign.RemoteSysMenuService;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.format.TreeUtil;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @return 正常返回:{@link Map<String,Property>}
     * @author 蝉鸣
     */
    public Map<String, Property> getFormColumnEsMapping(List<AppFormColumn> formColumns) {
        if(CollUtil.isEmpty(formColumns)){
            return new HashMap<>();
        }
        //转化Es类型
        Map<String, Property> propertyMap = formColumns.stream()
                .filter(column -> YesOrNoEnum.YES.getValue().equals(column.getEsInit()))
                .collect(Collectors.toMap(AppFormColumn::getColumnMac, column -> EsUtil.getPropertyByKind(column.getEsKind()), (v1, v2) -> v2));
        //添加固定字段映射
        propertyMap.putAll(getFixColumnEsMapping());
        return propertyMap;
    }


    /**
     * 功能描述:
     * 〈固定字段映射〉
     * @return 正常返回:{@link Map<String,Property>}
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
     * 〈DTO 转 PO〉
     * @return 正常返回:{@link List<AppFormColumn>}
     * @author 蝉鸣
     */
    public List<AppFormColumn> getDtoToPo(List<AppFormColumnDTO> formColumnDTOs,CopyOptions options) {
        //转换PO保存
        return formColumnDTOs.stream().map(item -> {
            AppFormColumn formColumn = new AppFormColumn();
            options.setIgnoreProperties(ObjFieldUtil.getFieldName(AppFormColumn::getDefaultDataValue));
            BeanUtil.copyProperties(item, formColumn, options);
            Object defaultValue = BaseEnum.getEnumByValue(DataTypeEnum.class, formColumn.getDefaultDataType(),DataTypeEnum.INIT).getDefaultValue(item.getDefaultDataValue());
            formColumn.setDefaultDataValue(defaultValue);
            Object setValue = BaseEnum.getEnumByValue(DataTypeEnum.class, formColumn.getSetDataType(),DataTypeEnum.INIT).getDefaultValue(item.getSetDataValue());
            formColumn.setSetDataValue(setValue);
            Object relValue = BaseEnum.getEnumByValue(DataTypeEnum.class, formColumn.getRelDataType(),DataTypeEnum.INIT).getDefaultValue(item.getRelDataValue());
            formColumn.setRelDataValue(relValue);
            Object relTransValue = BaseEnum.getEnumByValue(DataTypeEnum.class, formColumn.getRelTransDataType(),DataTypeEnum.INIT).getDefaultValue(item.getRelTransDataValue());
            formColumn.setRelTransDataValue(relTransValue);
            String resetMac = resetMac(formColumn);
            formColumn.setColumnMac(resetMac);
            if(ObjectUtil.isEmpty(formColumn.getId())){
                formColumn.setId(IdUtil.getSnowflake().nextId());
            }
            return formColumn;
        }).toList();
    }

    /**
     * 功能描述:
     * 〈重置名称〉
     * @param formColumn formColumn
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String resetMac(AppFormColumn formColumn) {
        List<String> jsonList =  CollUtil.newArrayList();
        jsonList.add(CompTypeEnum.CHECKBOX.getDesc());
        jsonList.add(CompTypeEnum.RADIO.getDesc());
        jsonList.add(CompTypeEnum.SELECT.getDesc());
        jsonList.add(CompTypeEnum.MAP.getDesc());
        jsonList.add(CompTypeEnum.USER.getDesc());
        jsonList.add(CompTypeEnum.DEP.getDesc());
        jsonList.add(CompTypeEnum.RELEVANCE.getDesc());
        if(jsonList.contains(formColumn.getCompMac())){
            if(formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_JSON)){
                return formColumn.getColumnMac();
            }else{
                return formColumn.getColumnMac().concat(EsConst.MAPPING_SUFFIX_JSON);
            }
        }
        if(CompMacEnum.DATE.getDesc().equals(formColumn.getCompMac())){
            if(formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_TIME)){
                return formColumn.getColumnMac();
            }else{
                return formColumn.getColumnMac().concat(EsConst.MAPPING_SUFFIX_TIME);
            }
        }
        if(CompMacEnum.NUMBER.getDesc().equals(formColumn.getCompMac())){
            if(formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_NUM)){
                return formColumn.getColumnMac();
            }else{
                return formColumn.getColumnMac().concat(EsConst.MAPPING_SUFFIX_NUM);
            }
        }
        return formColumn.getColumnMac();
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
        appFormColumnSetActionService.saveBatch(actions);
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
        appFormColumnSetEventService.saveBatch(events);
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
            BeanUtil.copyProperties(processDTO, columnSetProcess);
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
        appFormColumnSetRequireService.saveBatch(requires);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈批量新增字段请求〉
     * @param appFormColumns appFormColumns
     * @author 蝉鸣
     */
    public void saveOrUpdateSetSorting(List<AppFormColumn> appFormColumns) {
        if(CollUtil.isEmpty(appFormColumns)){
            return;
        }
        List<AppFormColumnSortingDTO> columnSortingDTOS = appFormColumns.stream().map(column -> {
            AppFormColumnSortingDTO sortingDTO = new AppFormColumnSortingDTO();
            BeanUtil.copyProperties(column, sortingDTO, ObjFieldUtil.ignoreDefault());
            sortingDTO.setParentColumnId(column.getParentId());
            sortingDTO.setColumnId(column.getId());
            return sortingDTO;
        }).toList();
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
        //获取列表页面
        List<AppFormBase> pageForms = appFormBaseService.lambdaQuery()
                .in(AppFormBase::getId, formIds)
                .eq(AppFormBase::getFormType, FormTypeEnum.PAGE_LIST.getValue())
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
        List<AppFormBase> appFormBases = appFormBaseService.lambdaQuery()
                .eq(AppFormBase::getModuleId, moduleId)
                .eq(AppFormBase::getFormType, formType)
                .orderByAsc(AppFormBase::getDefaultFlag)
                .list();
        if(CollUtil.isEmpty(appFormBases)){
            return null;
        }
        return CollUtil.getFirst(appFormBases);
    }

}