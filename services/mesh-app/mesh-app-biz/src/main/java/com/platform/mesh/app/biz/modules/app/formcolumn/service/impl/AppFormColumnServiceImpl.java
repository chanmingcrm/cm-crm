package com.platform.mesh.app.biz.modules.app.formcolumn.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.SyncDataBO;
import com.platform.mesh.app.api.modules.app.enums.comp.ColumnTypeEnum;
import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.app.api.modules.app.enums.comp.ModuleTypeEnum;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.bo.ModuleFieldBO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnPageDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnSimpVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.mapper.AppFormColumnMapper;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.IAppFormColumnService;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.manual.AppFormColumnServiceAddManual;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.manual.AppFormColumnServiceManual;
import com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.domain.po.AppFormColumnSetRequire;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.es.domain.bo.EsIndexMappingBO;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;

import com.platform.mesh.mybatis.plus.query.LambdaQueryWrapperX;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysRoleBO;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.format.TreeUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段关联
 * @author 蝉鸣
 */
@Service
public class AppFormColumnServiceImpl extends ServiceImpl<AppFormColumnMapper, AppFormColumn> implements IAppFormColumnService  {

    @Autowired
    private AppFormColumnServiceManual appFormColumnServiceManual;

    @Autowired
    private AppFormColumnServiceAddManual appFormColumnServiceAddManual;

    /**
     * 功能描述:
     * 〈获取当前信息〉
     * @param formId formId
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    @Override
    public List<AppFormColumnVO> getFormColumnVOList(Long moduleId,Long formId) {
        List<AppFormColumn> appFormColumns = this.getBaseMapper().getFormColumnSortList(moduleId, formId);
        if(CollUtil.isEmpty(appFormColumns)){
            return CollUtil.newArrayList();
        }
        //获取字段补充信息
        return appFormColumnServiceManual.getAppFormColumnVO(appFormColumns);
    }

    /**
     * 功能描述:
     * 〈获取当前信息〉
     * @param formId formId
     * @return 正常返回:{@link AppFormColumnBO}
     * @author 蝉鸣
     */
    @Override
    public List<AppFormColumnBO> getFormColumnBOList(Long moduleId,Long formId) {
        List<AppFormColumn> appFormColumns = this.getBaseMapper().getFormColumnBOList(moduleId, formId);
        if(CollUtil.isEmpty(appFormColumns)){
            return CollUtil.newArrayList();
        }
        return BeanUtil.copyToList(appFormColumns, AppFormColumnBO.class);
    }

    /**
     * 功能描述:
     * 〈获取当前信息〉
     * @param formId formId
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    @Override
//    @Cacheable(value = CacheConstants.APP_MODULE_FORM, key = "#formId", unless = "#result?.size() == 0")
    public List<AppFormColumnVO> getFormColumnTree(Long moduleId,Long formId) {
        List<AppFormColumnVO> appFormColumnVOS = this.getFormColumnVOList(moduleId, formId);
        //封装树形结构
        return TreeUtil.packageTree(NumberConst.NUM_0.longValue(), appFormColumnVOS);
    }

    /**
     * 功能描述:
     * 〈获取单字段关联信息〉
     * @param formId formId
     * @param columnHash columnHash
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnVO getSingColumnInfo(Long formId, String columnHash) {
        List<AppFormColumn> appFormColumns = this.lambdaQuery()
                .eq(AppFormColumn::getFormId, formId)
                .eq(AppFormColumn::getColumnHash,columnHash).list();
        if(CollUtil.isEmpty(appFormColumns)){
            return null;
        }
        //获取字段补充信息
        List<AppFormColumnVO> appFormColumnVO = appFormColumnServiceManual.getAppFormColumnVO(appFormColumns);
        return CollUtil.getFirst(appFormColumnVO);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param batchId batchId
     * @param formColumnDTOs formColumnDTOs
     * @author 蝉鸣
     */
    @Transactional(rollbackFor = Exception.class)
    public void addFormColumn(Long batchId, List<AppFormColumnDTO> formColumnDTOs) {
        //平铺结构
        List<AppFormColumnDTO> dtoList = CollUtil.newArrayList();
        List<AppFormColumn> poList = CollUtil.newArrayList();
        //校验
        appFormColumnServiceAddManual.checkAddColumnDTOs(formColumnDTOs);
        //预处理
        appFormColumnServiceAddManual.preHandle(formColumnDTOs);
        //查询旧数据
        List<AppFormColumn> existList = appFormColumnServiceAddManual.getOld(formColumnDTOs);
        //获取存在字段Map
        Map<String, AppFormColumn> existMap = appFormColumnServiceAddManual.getExistMap(existList);
        //树结构转换平铺
        appFormColumnServiceAddManual.treeToList(formColumnDTOs,NumberConst.NUM_0.longValue(),existMap,dtoList,poList);
        //删除旧数据
        appFormColumnServiceAddManual.delOld(batchId,poList,existList);
        //保存数据
        appFormColumnServiceAddManual.addColumn(batchId,poList,existList);
        //后置处理
        appFormColumnServiceAddManual.afterHandle(batchId,dtoList,poList);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param formColumnIds formColumnIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteFormColumn(List<Long> formColumnIds) {
        return this.removeBatchByIds(formColumnIds);
    }

    /***
     * 功能描述:
     * 〈表单字段列表查询〉
     * @param appFormColumnPageDTO appFormColumnPageDTO
     * @return 正常返回:{@link MPage<AppFormColumn>}
     * @author 蝉鸣
     * @since 2024/8/29 17:19
     */
    @Override
    public MPage<AppFormColumn> page(AppFormColumnPageDTO appFormColumnPageDTO) {
        MPage<AppFormColumn> formColumnMPage = MPageUtil.pageEntityToMPage(appFormColumnPageDTO, AppFormColumn.class);
        LambdaQueryWrapperX<AppFormColumn> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eqIfPresent(AppFormColumn::getColumnType,appFormColumnPageDTO.getColumnType());
        lambdaQueryWrapperX.eqIfPresent(AppFormColumn::getFormId,appFormColumnPageDTO.getFormId());
        lambdaQueryWrapperX.eqIfPresent(AppFormColumn::getColumnName,appFormColumnPageDTO.getColumnName());
        lambdaQueryWrapperX.eqIfPresent(AppFormColumn::getColumnDesc,appFormColumnPageDTO.getColumnDesc());
        return page(formColumnMPage,lambdaQueryWrapperX);
    }

    /**
     * 功能描述:
     * 〈新增单字段关联〉
     * @param moduleIndex moduleIndex
     * @param moduleBaseIds moduleBaseIds
     * @return 正常返回:{@link EsIndexMappingBO}
     * @author 蝉鸣
     */
    @Override
    public EsIndexMappingBO getFormColumnEsMapping(String moduleIndex,List<Long> moduleBaseIds) {
        EsIndexMappingBO esIndexMappingBO = new EsIndexMappingBO();
        esIndexMappingBO.setIndexName(moduleIndex);
        //索取模块下字段池所有字段
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        List<AppFormColumn> formColumns = this.lambdaQuery()
                .in(AppFormColumn::getModuleId, moduleBaseIds)
                .eq(AppFormColumn::getFormId, NumberConst.NUM_0)
                .ne(AppFormColumn::getDefaultDataType, DataTypeEnum.INIT.getValue())
                .list();
        DataScopeHandler.unEnableDataScope();
        
        //转化Es类型
        esIndexMappingBO.setProperties(appFormColumnServiceManual.getFormColumnEsMapping(formColumns));
        return esIndexMappingBO;
    }

    /**
     * 功能描述:
     * 〈复制字段关联〉
     * @param sourceId sourceId
     * @param targetModule targetModule
     * @param copyForm copyForm
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<AppFormColumn, AppFormColumn> copyFormColumn(Long sourceId, AppModuleBase targetModule, Map<Long, Long> copyForm,Map<Long, AppFormColumnSetRequire> copyRequire) {
        Map<AppFormColumn, AppFormColumn> copyMap = new HashMap<>();
        if(ModuleTypeEnum.MODULE.getValue().equals(targetModule.getModuleType())){
            copyForm.put(NumberConst.NUM_0.longValue(),NumberConst.NUM_0.longValue());
        }
        if(CollUtil.isEmpty(copyForm)){
            return copyMap;
        }
        List<AppFormColumn> sourceFormColumns = this.lambdaQuery().eq(AppFormColumn::getModuleId, sourceId).list();
        //复制字段
        return copyFormColumn(sourceFormColumns,targetModule,copyForm,copyRequire);
    }

    /**
     * 功能描述:
     * 〈复制字段关联〉
     * @param sourceFormColumns sourceFormColumns
     * @param target target
     * @param copyForm copyForm
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<AppFormColumn, AppFormColumn> copyFormColumn(List<AppFormColumn> sourceFormColumns, AppModuleBase target, Map<Long, Long> copyForm,Map<Long, AppFormColumnSetRequire> copyRequire) {
        Map<AppFormColumn, AppFormColumn> ResultMap = new HashMap<>();
        Map<Long, AppFormColumn> copyMap = new HashMap<>();
        //查询源模块下的表单
        if(CollUtil.isEmpty(sourceFormColumns)){
            return ResultMap;
        }
        String seCodeHash = StrConst.SE_CODE.concat(SymbolConst.UNDERLINE).concat(IdUtil.fastUUID());
        //建立转化对象对应关系
        for (AppFormColumn sourceFormColumn : sourceFormColumns) {
            Long id = IdUtil.getSnowflake().nextId();
            AppFormColumn targetFormColumn = new AppFormColumn();
            BeanUtil.copyProperties(sourceFormColumn, targetFormColumn, ObjFieldUtil.ignoreDefault());
            targetFormColumn.setId(id);
            targetFormColumn.setModuleId(target.getId());
            targetFormColumn.setFormId(ObjectUtil.defaultIfNull(copyForm.get(sourceFormColumn.getFormId()),NumberConst.NUM_0.longValue()));
            if(StrConst.getFixFiled().contains(sourceFormColumn.getColumnMac())){
                targetFormColumn.setColumnName(target.getModuleName().concat(sourceFormColumn.getColumnName()));
            }
            //se_code 初始化默认相同,方便详情跳转
            if(sourceFormColumn.getCompMac().equals(StrConst.SE_CODE)){
                targetFormColumn.setColumnHash(seCodeHash);
            }else{
                //重置ColumnHash
                String newHash = targetFormColumn.getCompMac().concat(SymbolConst.UNDERLINE).concat(IdUtil.fastUUID());
                targetFormColumn.setColumnHash(newHash);
            }
            copyMap.put(sourceFormColumn.getId(), targetFormColumn);
        }
        //设置父ID
        List<AppFormColumn> targetFormColumns = CollUtil.newArrayList();
        for (AppFormColumn formColumn : sourceFormColumns) {
            //获取当前转换的字段
            AppFormColumn current = copyMap.get(formColumn.getId());
            //获取父字段
            AppFormColumn parent = copyMap.get(formColumn.getParentId());
            if(ObjectUtil.isNull(parent)){
                current.setParentId(NumberConst.NUM_0.longValue());
            }else{
                current.setParentId(parent.getId());
            }
            //重置rel_data_value  关联表单ID
            if(ObjectUtil.isNotEmpty(formColumn.getRelDataValue())
                    && NumberUtil.isNumber(formColumn.getRelDataValue().toString())
                    && copyForm.containsKey(NumberUtil.parseLong(formColumn.getRelDataValue().toString()))){
                current.setRelDataValue(copyForm.get(NumberUtil.parseLong(formColumn.getRelDataValue().toString())).toString());
            }
            //重置rel_trans_data_value  关联接口ID
            if(ObjectUtil.isNotEmpty(formColumn.getRelTransDataValue())
                    && NumberUtil.isNumber(formColumn.getRelTransDataValue().toString())
                    && copyRequire.containsKey(NumberUtil.parseLong(formColumn.getRelTransDataValue().toString()))){
                current.setRelTransDataValue(copyRequire.get(NumberUtil.parseLong(formColumn.getRelTransDataValue().toString())).toString());
            }
            if(CompMacEnum.SECOND_TABLE.getDesc().equals(formColumn.getCompMac())){
                JSONObject jsonObject = JSONUtil.createObj();
                jsonObject.set(StrUtil.toCamelCase(StrConst.COLUMN_HASH),seCodeHash);
                jsonObject.set(StrUtil.toCamelCase(StrConst.APP_ID),target.getAppId());
                jsonObject.set(StrUtil.toCamelCase(StrConst.MODULE_ID),target.getId());
                jsonObject.set(StrUtil.toCamelCase(StrConst.PARENT_MODULE_ID),target.getParentId());
                jsonObject.set(StrUtil.toCamelCase(StrConst.INDEX_NAME),target.getModuleIndex());
                jsonObject.set(StrUtil.toCamelCase(StrConst.MODULE_NAME),target.getModuleName());
                JSONArray array = JSONUtil.createArray();
                array.add(jsonObject);
                current.setRelTransDataValue(array);
                current.setRelTransDataType(DataTypeEnum.JSON_ARRAY.getValue());
            }
            targetFormColumns.add(current);
            ResultMap.put(formColumn, current);
        }
        //批量保存
        this.saveBatch(targetFormColumns);
        //增加菜单
        appFormColumnServiceManual.saveOrUpdateMenu(targetFormColumns);
        return ResultMap;
    }

    /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormColumnSimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<AppFormColumnVO> fastColumnVOByModuleAndFormType(Long moduleId, Integer formType) {
        //根据表单类型获取表单
        AppFormBase appFormBase = appFormColumnServiceManual.getAppFormBaseByFormType(moduleId,formType);
        if(ObjectUtil.isEmpty(appFormBase)){
            return CollUtil.newArrayList();
        }
        return this.getFormColumnVOList(moduleId,appFormBase.getId());
    }

    /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormColumnSimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<AppFormColumnVO> fastColumnTreeVOByModuleAndFormType(Long moduleId, Integer formType) {
        //根据表单类型获取表单
        AppFormBase appFormBase = appFormColumnServiceManual.getAppFormBaseByFormType(moduleId,formType);
        if(ObjectUtil.isEmpty(appFormBase)){
            return CollUtil.newArrayList();
        }
        return this.getFormColumnTree(moduleId,appFormBase.getId());
    }

    /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link AppFormColumnSimpVO}
     * @author 蝉鸣
     */
    @Override
    public List<AppFormColumnBO> fastColumnBOByModuleAndFormType(Long moduleId, Integer formType) {
        //根据表单类型获取表单
        AppFormBase appFormBase = appFormColumnServiceManual.getAppFormBaseByFormType(moduleId,formType);
        if(ObjectUtil.isEmpty(appFormBase)){
            return CollUtil.newArrayList();
        }
        return this.getFormColumnBOList(moduleId,appFormBase.getId());
    }

    /**
     * 功能描述:
     * 〈根据moduleId 业务字段类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param columnType columnType
     * @return 正常返回:{@link AppFormColumnSimpVO}
     * @author 蝉鸣
     */
    @Override
    public AppFormColumnVO fastColumnByModuleAndType(Long moduleId, Integer columnType) {
        List<AppFormColumn> appFormColumns = this.lambdaQuery().eq(AppFormColumn::getModuleId, moduleId).eq(AppFormColumn::getColumnType, columnType).list();
        if(CollUtil.isEmpty(appFormColumns)){
            return new AppFormColumnVO();
        }
        //获取字段补充信息
        List<AppFormColumnVO> appFormColumnVO = appFormColumnServiceManual.getAppFormColumnVO(appFormColumns);
        return CollUtil.getFirst(appFormColumnVO);
    }

    /**
     * 功能描述:
     * 〈获取同步信息使用关联模块以及字段信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link List<SyncDataBO>}
     * @author 蝉鸣
     */
    @Override
    public List<SyncDataBO> getRelModuleToSync(Long moduleId) {
        List<ModuleFieldBO> moduleFieldBOS = this.getBaseMapper().getRelModuleToSync(moduleId, CompTypeEnum.RELEVANCE.getDesc());
        if(CollUtil.isEmpty(moduleFieldBOS)){
            return List.of();
        }
        List<SyncDataBO> list = CollUtil.newArrayList();
        Map<Long, List<ModuleFieldBO>> map = moduleFieldBOS.stream().collect(Collectors.groupingBy(ModuleFieldBO::getModuleId));
        map.forEach((key,value)->{
            ModuleFieldBO first = CollUtil.getFirst(value);
            SyncDataBO syncDataBO = new SyncDataBO();
            syncDataBO.setModuleId(first.getModuleId());
            syncDataBO.setModuleIndex(first.getModuleIndex());
            syncDataBO.setModuleSchema(first.getModuleSchema());
            List<String> columnMacs = value.stream().map(ModuleFieldBO::getColumnMac).toList();
            syncDataBO.setFiledList(columnMacs);
            list.add(syncDataBO);
        });
        return list;
    }

}
