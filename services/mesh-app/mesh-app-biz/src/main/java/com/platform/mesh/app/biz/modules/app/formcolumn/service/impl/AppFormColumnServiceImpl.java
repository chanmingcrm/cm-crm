package com.platform.mesh.app.biz.modules.app.formcolumn.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.enums.comp.ModuleTypeEnum;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnPageDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnSimpVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.mapper.AppFormColumnMapper;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.IAppFormColumnService;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.manual.AppFormColumnServiceManual;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.es.domain.bo.EsIndexMappingBO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.query.LambdaQueryWrapperX;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.format.TreeUtil;
import com.platform.mesh.utils.function.FutureHandleUtil;
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
        List<AppFormColumn> appFormColumns = this.getBaseMapper().getFormColumnSortList(moduleId, formId);
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
     * @return 正常返回:{@link AppFormColumnVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addFormColumn(Long batchId, List<AppFormColumnDTO> formColumnDTOs) {
        if (CollUtil.isEmpty(formColumnDTOs)) {
            return Boolean.FALSE;
        }
        //如果传递字段formId多种则拒绝执行
        List<Long> formIds = formColumnDTOs.stream().map(AppFormColumnDTO::getFormId).filter(ObjectUtil::isNotEmpty).distinct().toList();
        if (formIds.size() > NumberConst.NUM_1) {
            return Boolean.FALSE;
        }
        AppFormColumnDTO columnDTO = CollUtil.getFirst(formColumnDTOs);
        //查询模块下存在配置数据
        List<AppFormColumn> existList = this.lambdaQuery()
                .eq(AppFormColumn::getModuleId, columnDTO.getModuleId())
                .eq(AppFormColumn::getFormId, columnDTO.getFormId())
                .list();
        CopyOptions options = CopyOptions.create();
        if (CollUtil.isEmpty(existList)) {
            //如果不存在则直接新增
            options.setIgnoreProperties(ObjFieldUtil.getFieldName(AppFormColumn::getId));
        }else{
            //如果存在删除则同步删除数据库中的数据，
            List<Long> columnIds = formColumnDTOs.stream().map(AppFormColumnDTO::getId).filter(ObjectUtil::isNotEmpty).toList();
            this.lambdaUpdate()
                    .eq(AppFormColumn::getModuleId,columnDTO.getModuleId())
                    .eq(AppFormColumn::getFormId,columnDTO.getFormId())
                    .eq(AppFormColumn::getParentId,columnDTO.getParentId())
                    .notIn(CollUtil.isNotEmpty(columnIds),AppFormColumn::getId,columnIds)
                    .remove();
            //如果子组件为空则删除相应的数据
            List<Long> emptyParentIds = formColumnDTOs.stream().filter(item->CollUtil.isEmpty(item.getChildren()))
                    .map(AppFormColumnDTO::getId).filter(ObjectUtil::isNotEmpty).toList();
            if (CollUtil.isNotEmpty(emptyParentIds)) {
                this.lambdaUpdate()
                        .eq(AppFormColumn::getModuleId,columnDTO.getModuleId())
                        .eq(AppFormColumn::getFormId,columnDTO.getFormId())
                        .in(AppFormColumn::getParentId,emptyParentIds)
                        .remove();
            }
            //必须先执行上述删除逻辑后再执行下述删除
            //删除多余数据
            this.getBaseMapper().deleteLossColumn(columnDTO.getModuleId(),columnDTO.getFormId());
        }

        //转换PO保存
        List<AppFormColumn> columnPOs = appFormColumnServiceManual.getDtoToPo(formColumnDTOs,options);
        Map<String, AppFormColumn> columnMap = columnPOs.stream().collect(Collectors.toMap(AppFormColumn::getColumnMac, Function.identity()));
        //系统账户保存处理
        this.saveOrUpdateBatch(columnPOs);
        //递归添加子节点
        formColumnDTOs.forEach(item->{
            if(columnMap.containsKey(item.getColumnMac())){
                //设置ID
                item.setId(columnMap.get(item.getColumnMac()).getId());
                //子节点进行递归
                if(CollUtil.isNotEmpty(item.getChildren())){
                    item.getChildren().forEach(child-> child.setParentId(columnMap.get(item.getColumnMac()).getId()));
                    addFormColumn(batchId,item.getChildren());
                }
            }
        });
        //增加动作
        FutureHandleUtil.runWithResult(formColumnDTOs,appFormColumnServiceManual::saveOrUpdateSetAction);
        //增加事件
        FutureHandleUtil.runWithResult(formColumnDTOs,appFormColumnServiceManual::saveOrUpdateSetEvent);
        //增加流程
        FutureHandleUtil.runWithResult(formColumnDTOs,appFormColumnServiceManual::saveOrUpdateSetProcess);
        //增加请求
        FutureHandleUtil.runWithResult(formColumnDTOs,appFormColumnServiceManual::saveOrUpdateSetRequire);
        //增加排序
        FutureHandleUtil.runNoResult(columnPOs,appFormColumnServiceManual::saveOrUpdateSetSorting);
        //增加菜单
        FutureHandleUtil.runNoResult(columnPOs,appFormColumnServiceManual::saveOrUpdateMenu);
        //当formId=0,修改子模块相关数据
        List<Long> moduleIds = appFormColumnServiceManual.getChildModuleIds(columnDTO.getModuleId(),columnDTO.getFormId());
        if(CollUtil.isNotEmpty(moduleIds)){
            for (AppFormColumn newColumn : columnPOs) {
                this.lambdaUpdate()
                        .set(AppFormColumn::getColumnMac,newColumn.getColumnMac())
                        .set(AppFormColumn::getColumnName,newColumn.getColumnName())
                        .set(AppFormColumn::getColumnDesc,newColumn.getColumnDesc())
                        .eq(AppFormColumn::getColumnHash,newColumn.getColumnHash())
                        .in(AppFormColumn::getModuleId,moduleIds)
                        .update();
            }
        }
        return Boolean.TRUE;
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

    @Override
    public List<AppFormColumn> queryList(AppFormColumnPageDTO appFormColumnPageDTO) {
        return null;
    }

    /**
     * 功能描述:
     * 〈新增单字段关联〉
     * @param moduleBaseId moduleBaseId
     * @param moduleIndex moduleIndex
     * @return 正常返回:{@link EsIndexMappingBO}
     * @author 蝉鸣
     */
    @Override
    public EsIndexMappingBO getFormColumnEsMapping(Long moduleBaseId,String moduleIndex) {
        EsIndexMappingBO esIndexMappingBO = new EsIndexMappingBO();
        esIndexMappingBO.setIndexName(moduleIndex);
        //索取模块下字段池所有字段
        List<AppFormColumn> formColumns = this.lambdaQuery()
                .eq(AppFormColumn::getModuleId, moduleBaseId)
                .eq(AppFormColumn::getFormId, NumberConst.NUM_0)
                .ne(AppFormColumn::getDefaultDataType, DataTypeEnum.INIT.getValue())
                .list();
        //转化Es类型
        esIndexMappingBO.setProperties(appFormColumnServiceManual.getFormColumnEsMapping(formColumns));
        return esIndexMappingBO;
    }

    /**
     * 功能描述:
     * 〈复制字段关联〉
     * @param sourceModule sourceModule
     * @param targetModule targetModule
     * @param copyForm copyForm
     * @return 正常返回:{@link Map<Long,AppFormColumn>}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Long, AppFormColumn> copyFormColumn(AppModuleBase sourceModule, AppModuleBase targetModule, Map<Long, Long> copyForm) {
        Map<Long, AppFormColumn> copyMap = new HashMap<>();
        if(ModuleTypeEnum.MODULE.getValue().equals(sourceModule.getModuleType())){
            copyForm.put(NumberConst.NUM_0.longValue(),NumberConst.NUM_0.longValue());
        }
        if(CollUtil.isEmpty(copyForm)){
            return copyMap;
        }
        List<AppFormColumn> sourceFormColumns = this.lambdaQuery().eq(AppFormColumn::getModuleId, sourceModule.getId()).list();
        //查询源模块下的表单
        if(CollUtil.isEmpty(sourceFormColumns)){
            return copyMap;
        }
        //建立转化对象对应关系
        for (AppFormColumn sourceFormColumn : sourceFormColumns) {
            Long id = IdUtil.getSnowflake().nextId();
            AppFormColumn targetFormColumn = new AppFormColumn();
            BeanUtil.copyProperties(sourceFormColumn, targetFormColumn, ObjFieldUtil.ignoreDefault());
            targetFormColumn.setId(id);
            targetFormColumn.setModuleId(targetModule.getId());
            targetFormColumn.setFormId(copyForm.get(sourceFormColumn.getFormId()));
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
            copyMap.put(formColumn.getId(), current);
            targetFormColumns.add(current);
        }
        //批量保存
        this.saveBatch(targetFormColumns);
        //增加菜单
        appFormColumnServiceManual.saveOrUpdateMenu(targetFormColumns);
        return copyMap;
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


}