package com.platform.mesh.app.biz.modules.app.formcolumn.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formbase.service.IAppFormBaseService;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.exception.AppFormColumnExceptionEnum;
import com.platform.mesh.app.biz.modules.app.formcolumn.mapper.AppFormColumnMapper;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.IAppFormColumnAddService;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.dto.AppFormColumnSortingDTO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import jakarta.annotation.Resource;
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
public class AppFormColumnServiceAddManual {

    public Map<String, AppFormColumn> getExistMap(List<AppFormColumn> existList) {
        Map<String, AppFormColumn> result = new HashMap<>();
        existList.forEach(column -> {
            String key = ObjectUtil.isNotEmpty(column.getParentId()) && column.getParentId() != NumberConst.NUM_0.longValue()
                    ? column.getParentId() + SymbolConst.DASH + column.getColumnMac()
                    : column.getColumnMac();
            result.put(key, column);
        });
        return result;
    }

    private final static Logger log = LoggerFactory.getLogger(AppFormColumnServiceAddManual.class);


    @Autowired
    private IAppFormColumnAddService appFormColumnAddService;

    @Autowired
    private AppFormColumnServiceManual appFormColumnServiceManual;

    @Resource
    private AppFormColumnMapper appFormColumnMapper;

    @Autowired
    private IAppFormBaseService appFormBaseService;

    /**
     * 功能描述:
     * 〈校验新增字段〉
     * @param formColumnDTOs formColumnDTOs
     * @author 蝉鸣
     */
    public void checkAddColumnDTOs(List<AppFormColumnDTO> formColumnDTOs) {
        if (CollUtil.isEmpty(formColumnDTOs)) {
            throw AppFormColumnExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        //如果传递字段formId多种则拒绝执行
        List<Long> formIds = formColumnDTOs.stream().map(AppFormColumnDTO::getFormId).filter(ObjectUtil::isNotEmpty).distinct().toList();
        if (formIds.size() > NumberConst.NUM_1) {
            throw AppFormColumnExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈预处理新增字段〉
     * @param formColumnDTOs formColumnDTOs
     * @author 蝉鸣
     */
    public void preHandle(List<AppFormColumnDTO> formColumnDTOs) {
        //处理导入表头
        preHandleImportForm(formColumnDTOs);

    }

    /**
     * 功能描述:
     * 〈查询存在数据〉
     * @param formColumnDTOs formColumnDTOs
     * @author 蝉鸣
     */
    public List<AppFormColumn> getOld(List<AppFormColumnDTO> formColumnDTOs) {
        AppFormColumnDTO columnDTO = CollUtil.getFirst(formColumnDTOs);
        return appFormColumnMapper.getFormColumnBOList(columnDTO.getModuleId(), columnDTO.getFormId());
    }

    /**
     * 功能描述:
     * 〈树结构转换平铺〉
     * @param tree tree
     * @param existMap existMap
     * @param dtoList dtoList
     * @param opList opList
     * @author 蝉鸣
     */
    public void treeToList(List<AppFormColumnDTO> tree,Long parentId,Map<String, AppFormColumn> existMap,List<AppFormColumnDTO> dtoList,List<AppFormColumn> opList) {
        if(CollUtil.isEmpty(tree)){
            return;
        }
        dtoList.addAll(tree);
        //递归添加子节点
        for (AppFormColumnDTO item : tree) {
            //转换PO保存
            AppFormColumn columnPO = this.getDtoToPo(item,parentId,existMap);
            opList.add(columnPO);
            List<AppFormColumnDTO> children = item.getChildren();
            if(CollUtil.isEmpty(children)){
                continue;
            }
            treeToList(children, columnPO.getId(), existMap, dtoList, opList);
        }
    }

    /**
     * 功能描述:
     * 〈删除存在数据〉
     * @param poList poList
     * @author 蝉鸣
     */
    public void delOld(Long batchId,List<AppFormColumn> poList,List<AppFormColumn> existList) {
        AppFormColumn columnPO = CollUtil.getFirst(poList);
        //如果存在删除则同步删除数据库中的数据，
        List<Long> columnIds = poList.stream().map(AppFormColumn::getId).filter(ObjectUtil::isNotEmpty).toList();
        appFormColumnAddService.lambdaUpdate()
                .eq(AppFormColumn::getModuleId,columnPO.getModuleId())
                .eq(AppFormColumn::getFormId,columnPO.getFormId())
                .notIn(CollUtil.isNotEmpty(columnIds),AppFormColumn::getId,columnIds)
                .remove();
    }

    /**
     * 功能描述:
     * 〈新增字段〉
     * @param batchId batchId
     * @param columnPOs columnPOs
     * @param existList existList
     * @author 蝉鸣
     */
    public void addColumn(Long batchId,List<AppFormColumn> columnPOs, List<AppFormColumn> existList) {
        //系统账户保存处理
        appFormColumnAddService.saveOrUpdateBatch(columnPOs);
        //更新子字段
        updateChild(columnPOs,existList);
    }

    /**
     * 功能描述:
     * 〈新增字段〉
     * @param batchId batchId
     * @param formColumnDTOs formColumnDTOs
     * @param poList poList
     * @author 蝉鸣
     */
    public void afterHandle(Long batchId, List<AppFormColumnDTO> formColumnDTOs,List<AppFormColumn> poList) {
        //增加动作
        FutureHandleUtil.runWithResult(formColumnDTOs,appFormColumnServiceManual::saveOrUpdateSetAction);
        //增加事件
        FutureHandleUtil.runWithResult(formColumnDTOs,appFormColumnServiceManual::saveOrUpdateSetEvent);
        //增加流程
        FutureHandleUtil.runWithResult(formColumnDTOs,appFormColumnServiceManual::saveOrUpdateSetProcess);
        //增加请求
        FutureHandleUtil.runWithResult(formColumnDTOs,appFormColumnServiceManual::saveOrUpdateSetRequire);
        //增加排序
        List<AppFormColumnSortingDTO> setSorting = appFormColumnServiceManual.getSetSorting(batchId, poList);
        FutureHandleUtil.runNoResult(setSorting,appFormColumnServiceManual::saveOrUpdateSetSorting);
        //增加菜单
        FutureHandleUtil.runNoResult(poList,appFormColumnServiceManual::saveOrUpdateMenu);
    }

    /**
     * 功能描述:
     * 〈预处理导入表头配置〉
     * @param formColumnDTOs formColumnDTOs
     * @author 蝉鸣
     */
    public void preHandleImportForm(List<AppFormColumnDTO> formColumnDTOs){
        AppFormColumnDTO columnDTO = CollUtil.getFirst(formColumnDTOs);
        //前置重置数据
        //如果是导入则需要查询新增表单，将新增字段特殊设置赋值导入：例如唯一，非空
        AppFormBase currentForm = appFormBaseService.getById(columnDTO.getFormId());
        if(ObjectUtil.isEmpty(currentForm) || !FormTypeEnum.HEAD_IMPORT.getValue().equals(currentForm.getFormType())){
            return;
        }
        AppFormBase addForm = appFormBaseService.getAppFormBaseByFormType(columnDTO.getModuleId(), FormTypeEnum.FORM_ADD.getValue());
        if(ObjectUtil.isEmpty(addForm)){
            return;
        }
        List<AppFormColumn> appFormColumns = appFormColumnMapper.getFormColumnBOList(columnDTO.getModuleId(), addForm.getId());
        if(CollUtil.isEmpty(appFormColumns)){
            return;
        }
        Map<String, AppFormColumn> map = appFormColumns.stream().collect(Collectors.toMap(AppFormColumn::getColumnHash, Function.identity()));
        //赋值配置
        for (AppFormColumnDTO formColumnDTO : formColumnDTOs) {
            if(map.containsKey(formColumnDTO.getColumnHash())){
                AppFormColumn appFormColumn = map.get(formColumnDTO.getColumnHash());
                formColumnDTO.setUniqueFlag(appFormColumn.getUniqueFlag());
                formColumnDTO.setMultiFlag(appFormColumn.getMultiFlag());
                formColumnDTO.setEmptyFlag(appFormColumn.getEmptyFlag());
                formColumnDTO.setEditFlag(appFormColumn.getEditFlag());
                formColumnDTO.setHiddenFlag(appFormColumn.getHiddenFlag());
            }
        }
    }

    /**
     * 功能描述:
     * 〈更新子字段〉
     * @param poList poList
     * @param existList existList
     * @author 蝉鸣
     */
    public void updateChild(List<AppFormColumn> poList, List<AppFormColumn> existList) {
        AppFormColumn columnPO = CollUtil.getFirst(poList);
        //当formId=0,修改子模块相关数据
        List<Long> moduleIds = appFormColumnServiceManual.getChildModuleIds(columnPO.getModuleId(),columnPO.getFormId());
        if(CollUtil.isEmpty(moduleIds)){
            return;
        }
        //更新字段表
        for (AppFormColumn newColumn : poList) {
            AppFormColumn formColumn = this.updateChildFormColumn(newColumn);
            LambdaUpdateWrapper<AppFormColumn> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(AppFormColumn::getColumnHash,newColumn.getColumnHash())
                    .in(AppFormColumn::getModuleId,moduleIds);
            appFormColumnAddService.update(formColumn,updateWrapper);
        }
    }

    /**
     * 功能描述:
     * 〈DTO 转 PO〉
     * @return 正常返回:{@link List<AppFormColumn>}
     * @author 蝉鸣
     */
    public AppFormColumn getDtoToPo(AppFormColumnDTO formColumnDTO,Long parentId,Map<String,AppFormColumn> columnMap) {
        CopyOptions options = CopyOptions.create();
        if (CollUtil.isEmpty(columnMap)) {
            //如果不存在则直接新增
            options.setIgnoreProperties(ObjFieldUtil.getFieldName(AppFormColumn::getId));
        }
        formColumnDTO.setParentId(parentId);
        AppFormColumn formColumn = new AppFormColumn();
        options.setIgnoreProperties(ObjFieldUtil.getFieldName(AppFormColumn::getDefaultDataValue));
        BeanUtil.copyProperties(formColumnDTO, formColumn, options);
        Object defaultValue = BaseEnum.getEnumByValue(DataTypeEnum.class, formColumn.getDefaultDataType(),DataTypeEnum.INIT).getDefaultValue(formColumnDTO.getDefaultDataValue());
        formColumn.setDefaultDataValue(defaultValue);
        Object setValue = BaseEnum.getEnumByValue(DataTypeEnum.class, formColumn.getSetDataType(),DataTypeEnum.INIT).getDefaultValue(formColumnDTO.getSetDataValue());
        formColumn.setSetDataValue(setValue);
        Object relValue = BaseEnum.getEnumByValue(DataTypeEnum.class, formColumn.getRelDataType(),DataTypeEnum.INIT).getDefaultValue(formColumnDTO.getRelDataValue());
        formColumn.setRelDataValue(relValue);
        Object relTransValue = BaseEnum.getEnumByValue(DataTypeEnum.class, formColumn.getRelTransDataType(),DataTypeEnum.INIT).getDefaultValue(formColumnDTO.getRelTransDataValue());
        formColumn.setRelTransDataValue(relTransValue);
        String resetMac = this.resetMac(formColumn);
        formColumn.setColumnMac(resetMac);
        //判断是否已经存在
        if(CollUtil.isNotEmpty(columnMap)){
            //如果是子表字段
            String childMac = formColumn.getParentId() + SymbolConst.DASH + formColumn.getColumnMac();
            if (columnMap.containsKey(childMac)) {
                formColumn.setId(columnMap.get(childMac).getId());
            }else if(columnMap.containsKey(formColumn.getColumnMac())){
                formColumn.setId(columnMap.get(formColumn.getColumnMac()).getId());
            }else{
                formColumn.setId(IdUtil.getSnowflake().nextId());
            }
        }else{
            if(ObjectUtil.isEmpty(formColumn.getId())){
                formColumn.setId(IdUtil.getSnowflake().nextId());
            }
        }
        if(ObjectUtil.isEmpty(formColumn.getXAddr())){
            formColumn.setXAddr(NumberConst.NUM_0);
        }
        if(ObjectUtil.isEmpty(formColumn.getYAddr())){
            formColumn.setYAddr(NumberConst.NUM_0);
        }
        formColumn.setDeleteFlag(YesOrNoEnum.YES.getValue());
        return formColumn;
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
        for (CompTypeEnum typeEnum : CompTypeEnum.values()) {
            jsonList.add(typeEnum.getDesc());
        }
        if(jsonList.contains(formColumn.getCompMac())){
            if(formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_JSON)){
                return formColumn.getColumnMac();
            }else{
                return formColumn.getColumnMac().concat(EsConst.MAPPING_SUFFIX_JSON);
            }
        }
        if(CompMacEnum.DATE.getDesc().equals(formColumn.getCompMac())){
            if(formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_DATE)){
                return formColumn.getColumnMac();
            }else{
                return formColumn.getColumnMac().concat(EsConst.MAPPING_SUFFIX_DATE);
            }
        }
        if(CompMacEnum.TIME.getDesc().equals(formColumn.getCompMac())
                || CompMacEnum.DATE_TIME.getDesc().equals(formColumn.getCompMac())){
            if(formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_TIME)){
                return formColumn.getColumnMac();
            }else{
                return formColumn.getColumnMac().concat(EsConst.MAPPING_SUFFIX_TIME);
            }
        }
        if(CompMacEnum.NUMBER.getDesc().equals(formColumn.getCompMac())){
            if(formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_NUM)
                    || formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_MONEY)){
                return formColumn.getColumnMac();
            }else{
                return formColumn.getColumnMac().concat(EsConst.MAPPING_SUFFIX_NUM);
            }
        }

        if(CompMacEnum.TEXT_AREA.getDesc().equals(formColumn.getCompMac())
                || CompMacEnum.TEXT_MULTI.getDesc().equals(formColumn.getCompMac())){
            if(formColumn.getColumnMac().endsWith(EsConst.MAPPING_SUFFIX_TEXT)
                || formColumn.getColumnMac().startsWith(EsConst.MAPPING_PREFIX_TEXTAREA)
                || formColumn.getColumnMac().startsWith(EsConst.MAPPING_PREFIX_TEXT_MULTI)
                || formColumn.getColumnMac().equals(StrConst.DATA_DESC)){
                return formColumn.getColumnMac();
            }else{
                return formColumn.getColumnMac().concat(EsConst.MAPPING_SUFFIX_TEXT);
            }
        }
        return formColumn.getColumnMac();
    }

    /**
     * 功能描述:
     * 〈修改子表单字段〉
     * @param newColumn newColumn
     * @author 蝉鸣
     */
    public AppFormColumn updateChildFormColumn(AppFormColumn newColumn) {
        AppFormColumn formColumn = new AppFormColumn();
        formColumn.setColumnMac(newColumn.getColumnMac());
        formColumn.setColumnName(newColumn.getColumnName());
        formColumn.setColumnDesc(newColumn.getColumnDesc());
        formColumn.setSetDataType(newColumn.getSetDataType());
        formColumn.setSetDataValue(newColumn.getSetDataValue());
        formColumn.setRelDataType(newColumn.getRelDataType());
        formColumn.setRelDataValue(newColumn.getRelDataValue());
        return formColumn;
    }

}
