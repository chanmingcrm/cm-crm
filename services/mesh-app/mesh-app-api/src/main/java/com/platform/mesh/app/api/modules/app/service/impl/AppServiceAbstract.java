package com.platform.mesh.app.api.modules.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.platform.mesh.app.api.modules.app.domain.bo.AppDataAddBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.bo.ImportErrorBO;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppRelPO;
import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import com.platform.mesh.app.api.modules.app.domain.vo.ImportVO;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.api.modules.app.exception.AppExceptionEnum;
import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.app.api.modules.app.service.manual.AppServiceManual;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.api.modules.serial.SerialUtil;
import com.platform.mesh.app.api.modules.serial.domain.bo.ColumnCompBO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.search.utils.SearchUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.utils.excel.constants.ExcelConst;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.listener.UploadDataListener;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import lombok.Getter;
import org.apache.fesod.sheet.FesodSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public abstract class AppServiceAbstract<M extends BaseMapper<T>, T extends AppPO> extends CrudRepository<M, T> implements IAppService<T>  {

    @Autowired
    private AppServiceManual appServiceManual;

    private static final int IMPORT_ERROR_RECORD_LIMIT = NumberConst.NUM_2000;

    /**
     * 功能描述:
     * 〈获取ES数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO) {
        
        return appServiceManual.selectEsPage(pageDTO);
    }

    /**
     * 功能描述:
     * 〈根据ID获取数据〉
     * @param moduleIndex moduleIndex
     * @param ids ids
     * @return 正常返回:{@link List<Object>}
     * @author 蝉鸣
     */
    @Override
    public List<Object> getEsByIds(String moduleIndex, List<Long> ids) {
        if(CollUtil.isEmpty(ids)){
            return CollUtil.newArrayList();
        }
        return appServiceManual.getEsByIds(moduleIndex,ids);
    }

    /**
     * 功能描述:
     * 〈获取当前数据对象信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    @Override
    public <E extends AppVO> E getDataInfoById(EsDocSGetDTO esDocSGetDTO, Class<E> voClass) {
        //设置moduleId
        if(ObjectUtil.isEmpty(esDocSGetDTO.getModuleId())){
            DataScopeHandler.setEnableDataScope(Boolean.FALSE);
            T dataPO = this.getById(esDocSGetDTO.getDataId());
            DataScopeHandler.unEnableDataScope();
            if(ObjectUtil.isEmpty(dataPO)){
                return BeanUtil.copyProperties(new AppVO(), voClass);
            }
            esDocSGetDTO.setModuleId(dataPO.getModuleId());
        }
        //设置索引
        if(ObjectUtil.isEmpty(esDocSGetDTO.getIndexName())){
            AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(esDocSGetDTO.getModuleId());
            if(ObjectUtil.isEmpty(moduleInfo)){
                return BeanUtil.copyProperties(new AppVO(), voClass);
            }
            esDocSGetDTO.setIndexName(moduleInfo.getModuleIndex());
        }
        Object esData = appServiceManual.getDataInfoById(esDocSGetDTO);
        E dataVO = BeanUtil.copyProperties(esData, voClass);
        if(ObjectUtil.isEmpty(esData)){
            return dataVO;
        }
        dataVO.setEsData(BeanUtil.beanToMap(esData));
//        dataVO.setEsData(AppUtil.beanToMap(esData));
        return getOtherAction(dataVO);
    }

    /**
     * 功能描述:
     * 〈新增数据对象〉
     * @param dataAddSimpDTO dataAddDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <D extends AppDataPO> T addDataSimp(DataAddSimpDTO dataAddSimpDTO, Class<T> poClass, Class<D> dataPoClass) {
        //获取模块信息
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(dataAddSimpDTO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            throw AppExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        //获取字段信息
        List<AppFormColumnBO> columnBOS = appServiceManual.getFormColumnInfo(dataAddSimpDTO.getModuleId(),dataAddSimpDTO.getFormId());
        //校验数据
        appServiceManual.checkData(columnBOS,moduleInfo,dataAddSimpDTO.getDocData(),poClass);
        //转化数据
        T dataPO = SerialUtil.genDataSerial(poClass,BeanUtil.copyToList(columnBOS, ColumnCompBO.class),dataAddSimpDTO.getDocData());
        dataPO.setModuleId(dataAddSimpDTO.getModuleId());
        dataPO.setDelFlag(YesOrNoEnum.YES.getValue());
        //保存前逻辑
        this.preDbOtherAction(dataPO, dataAddSimpDTO);
        //保存数据库
        this.save(dataPO);
        //其他添加逻辑
        this.addOtherAction(dataPO, dataAddSimpDTO);
        //保存data数据
        List<D> dataList = appServiceManual.getDbDataSimp(moduleInfo, dataPO.getId(),dataPoClass, columnBOS, dataAddSimpDTO.getDocData());
        //保存DbData数据
        this.addDbDataBatch(dataList);
        //保存DbRel数据
        List<AppRelPO> relPOS = appServiceManual.parseRelPO(dataPO, columnBOS, dataAddSimpDTO.getDocData());
        this.addDbRelBatch(relPOS);
        Map<String, Object> transMap = appServiceManual.getDataMap(dataList);
        //保存ES数据
        appServiceManual.addEsData(moduleInfo.getModuleIndex(),dataPO, transMap);
        //发送消息
        appServiceManual.addMsgNotice(moduleInfo,columnBOS,transMap);
        //日志信息
        appServiceManual.addLogModify(OperateTypeEnum.INSERT.getValue(), dataPO, columnBOS,null, transMap);
        //返回VO对象
        return dataPO;
    }

    /**
     * 功能描述:
     * 〈新增数据对象〉
     * @param dataAddCompDTO dataAddCompDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <D extends AppDataPO> T addDataComp(DataAddCompDTO dataAddCompDTO, Class<T> poClass, Class<D> dataPoClass) {
        List<AppFormColumnAddDTO> docDataList = dataAddCompDTO.getDocDataList();
        if(CollUtil.isEmpty(docDataList)){
            return  BeanUtil.copyProperties(new AppDataPO(),poClass);
        }
        //获取存储ID
        Long dataId = IdUtil.getSnowflake().nextId();
        //保存data数据
        List<D> dataList = appServiceManual.getDbDataComp(dataId, dataAddCompDTO,dataPoClass);
        Map<String,Object> dataMap = appServiceManual.getDataMap(dataList);
        //保存数据库
        T po = BeanUtil.copyProperties(dataMap, poClass);
        po.setModuleId(dataAddCompDTO.getModuleId());
        po.setId(dataId);
        this.save(po);
        //保存DbData数据
        this.addDbDataBatch(dataList);
        //保存ES数据
        appServiceManual.addEsData(dataAddCompDTO.getModuleIndex(),po, dataMap);
        //返回VO对象
        return po;
    }

    /**
     * 功能描述:
     * 〈修改数据对象〉
     * @param dataEditDTO dataEditDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> T editData(DataEditSimpDTO dataEditDTO, Class<T> poClass, Class<D> dataPoClass) {
        if(ObjectUtil.isEmpty(dataEditDTO.getDataId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(DataEditSimpDTO::getDataId);
            throw AppExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        //获取模块信息
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(dataEditDTO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            throw AppExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        //获取字段信息
        List<AppFormColumnBO> columnBOS = appServiceManual.getFormColumnInfo(dataEditDTO.getModuleId(),dataEditDTO.getFormId());
        //获取保存前数据信息
        Map<String,Object> oldMap = appServiceManual.getEsData(moduleInfo.getModuleIndex(),dataEditDTO.getDataId());
        //校验数据
        appServiceManual.checkData(columnBOS,moduleInfo,dataEditDTO.getDocData(),poClass);
        if(ObjectUtil.isEmpty(dataEditDTO.getDocData().get(StrConst.DATA_NAME))){
            dataEditDTO.getDocData().put(StrConst.DATA_NAME, dataEditDTO.getDocData().get(StrConst.DATA_MAC));
        }
        //转化数据
        T dataPO = BeanUtil.copyProperties(dataEditDTO.getDocData(), poClass);
        dataPO.setId(dataEditDTO.getDataId());
        dataPO.setUpdateTime(LocalDateTime.now());
        //其他修改逻辑
        this.editOtherAction(dataPO, dataEditDTO);
        //修改PO
        this.updateById(dataPO);
        //其他修改逻辑
        this.addOtherAction(dataPO, dataEditDTO);
        List<D> dataList = appServiceManual.getDbDataSimp(moduleInfo, dataPO.getId(),dataPoClass, columnBOS, dataEditDTO.getDocData());
        //修改Data数据
        this.addDbDataBatch(dataList);
        //保存DbRel数据
        List<AppRelPO> relPOS = appServiceManual.parseRelPO(dataPO, columnBOS, dataEditDTO.getDocData());
        this.addDbRelBatch(relPOS);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),dataPO,dataEditDTO.getDocData());
        //发送消息
        appServiceManual.addMsgNotice(moduleInfo,columnBOS,dataEditDTO.getDocData());
        //日志信息
        appServiceManual.addLogModify(OperateTypeEnum.UPDATE.getValue(), dataPO, columnBOS,oldMap, dataEditDTO.getDocData());
        return dataPO;
    }


    /**
     * 功能描述:
     * 〈删除数据对象〉
     * @param dataId dataId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteData(Long dataId) {
        DataScopeHandler.setEnableDataScope(Boolean.FALSE);
        T dataPO = getById(dataId);
        DataScopeHandler.unEnableDataScope();
        if(ObjectUtil.isEmpty(dataPO)){
            return Boolean.FALSE;
        }
        //获取模块信息
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(dataPO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return Boolean.FALSE;
        }
        //其他删除逻辑
        delOtherAction(CollUtil.newArrayList(dataId));
        //删除ES数据
        appServiceManual.deleteEsData(moduleInfo.getModuleIndex(),CollUtil.newArrayList(dataId));
        //删除数据库数据
        dataPO.setDelFlag(YesOrNoEnum.NO.getValue());
        this.updateById(dataPO);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈批量删除数据对象〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteData(DataDelDTO delDTO) {
        if(CollUtil.isEmpty(delDTO.getDataIds())){
            return Boolean.FALSE;
        }
        //获取模块信息
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(delDTO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return Boolean.FALSE;
        }
        //其他删除逻辑
        delOtherAction(delDTO.getDataIds());
        //删除ES数据
        appServiceManual.deleteEsData(moduleInfo.getModuleIndex(),delDTO.getDataIds());
        //删除数据库数据 Lambda方式无法重定T 泛型报错
        UpdateWrapper<T> wrapper = new UpdateWrapper<>();
        wrapper.set(ObjFieldUtil.getColumnName(T::getDelFlag),YesOrNoEnum.NO.getValue());
        wrapper.in(ObjFieldUtil.getColumnName(T::getId),delDTO.getDataIds());
        this.update(wrapper);
        //删除第三方关联数据
        appServiceManual.delRel(delDTO.getDataIds(),moduleInfo);
        //删除流程关联数据
        appServiceManual.delBpm(delDTO.getDataIds(),moduleInfo);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈转移数据对象〉
     * @param transScopeDTO transScopeDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean transData(TransScopeDTO transScopeDTO) {
        if(CollUtil.isEmpty(transScopeDTO.getIds())){
            return Boolean.FALSE;
        }
        OrgMemberRelBO defaultRel = appServiceManual.getDefaultRelByMemberId(transScopeDTO.getMemberId());
        if(ObjectUtil.isEmpty(defaultRel) || ObjectUtil.isEmpty(defaultRel.getUserId())){
            throw AppExceptionEnum.ADD_NO_ORG_SCOPE_INVALID.getBaseException();
        }
        transScopeDTO.setScopeUserId(defaultRel.getUserId());
        if(ObjectUtil.isEmpty(transScopeDTO.getScopeOrgId())){}{
            transScopeDTO.setScopeOrgId(defaultRel.getLevelId());
        }
        if(ObjectUtil.isEmpty(transScopeDTO.getScopeOrgId())){
            throw AppExceptionEnum.ADD_NO_ORG_SCOPE_INVALID.getBaseException();
        }
        //修改DB_DATA
        this.transDbScopeBatch(transScopeDTO.getIds(),transScopeDTO.getScopeUserId(),transScopeDTO.getScopeOrgId());
        //修改ES
        appServiceManual.transEsData(transScopeDTO);
        //日志信息
//        FutureHandleUtil.runWithResult(transScopeDTO.getIds(),item-> {
//            if(ObjectUtil.isNotNull(item)){
//            }
//            return Boolean.TRUE;
//        });

        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈导入数据对象〉
     * @param importDTO importDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <D extends AppDataPO> ImportVO importData(DataImportDTO importDTO,Class<T> poClass ,Class<D> dataPoClass) {
        ImportVO importVO = new ImportVO();
        //导入批次
        importVO.setBatchId(IdUtil.getSnowflakeNextId());
        //错误回收信息
        Map<Integer,List<String>> errorRecord = new HashMap<>();
        Map<Integer,String> dataRecord = new HashMap<>();
        //当前模块ID
        Long moduleId = importDTO.getModuleId();
        //当前表单ID
        Long formId = importDTO.getFormId();
        MultipartFile file = importDTO.getFile();
        Integer skipOrOver = importDTO.getSkipOrOver();
        //获取模块信息
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(moduleId);
        //获取模块对应的字段信息
        List<AppFormColumnBO> formColumnInfo = appServiceManual.getFormColumnInfo(moduleId, formId);
        //查询新增表单字段,同步新增表单配置信息
        List<AppFormColumnBO> addFormColumn = appServiceManual.getFormColumnInfo(moduleId, FormTypeEnum.FORM_ADD.getValue());
        //分类数据
        Map<String, String> columnMap = formColumnInfo.stream().collect(Collectors.toMap(AppFormColumnBO::getColumnName, AppFormColumnBO::getColumnMac));
        List<String> transMacs = Arrays.stream(CompTypeEnum.values()).map(CompTypeEnum::getDesc).toList();
        List<String> objFieldNames = ObjFieldUtil.getObjFieldNames(poClass);
        //transMacs需要解析的字段,objFieldNames固定表字段则不解析
        Map<String, AppFormColumnBO> compMap = formColumnInfo.stream()
                .filter(comp->transMacs.contains(comp.getCompMac()))
                .filter(comp->!objFieldNames.contains(StrUtil.toCamelCase(comp.getColumnMac())))
                .collect(Collectors.toMap(AppFormColumnBO::getColumnMac, Function.identity()));
        //导入数量结果
        AtomicReference<Long> totalNum = new AtomicReference<>(NumberConst.NUM_0.longValue());
        AtomicReference<Long> errorNum = new AtomicReference<>(NumberConst.NUM_0.longValue());
        AtomicReference<Long> skipNum = new AtomicReference<>(NumberConst.NUM_0.longValue());
        AtomicReference<Long> overNum = new AtomicReference<>(NumberConst.NUM_0.longValue());
        //分页读取信息,默认100
        UploadDataListener uploadDataListener = new UploadDataListener(dataList -> {
            // dataList分页批量处理
            //保存db对象
            totalNum.getAndUpdate(v -> v + dataList.size());
            log.debug(dataList.toString());
            List<T> poList = CollUtil.newArrayList();
            List<AppDataAddBO> appDataAddBOS = CollUtil.newArrayList();
            List<AppDataAddBO> appDataEditBOS = CollUtil.newArrayList();
            for (Map<String, Object> dataMap : dataList) {
                Object object = dataMap.get(ExcelConst.EXCEL_IMPORT_ROW_NUM);
                Integer rowNum = Integer.parseInt(StrUtil.toString(object));
                //解析数据
                if(ObjectUtil.isNotEmpty(compMap)){
                    compMap.forEach((key,value)->{
                        Object parseValue = SearchUtil.parseImportData(value.getCompMac(),value.getRelDataValue(),key,dataMap.get(key));
                        dataMap.put(key,parseValue);
                    });
                }
                //对象实体
//                T dataPO = SerialUtil.genDataSerial(poClass,BeanUtil.copyToList(formColumnInfo, ColumnCompBO.class),dataMap);
                T dataPO = BeanUtil.copyProperties(dataMap, poClass);
                //校验数据:非空校验,人员校验，组织校验
                List<String> checked = appServiceManual.checkedMap(addFormColumn, dataMap);
                if(CollUtil.isNotEmpty(checked)){
                    if(errorRecord.size() < IMPORT_ERROR_RECORD_LIMIT){
                        dataRecord.put(rowNum,JSONUtil.toJsonStr(dataMap));
                        errorRecord.put(rowNum,checked);
                    }
                    //错误数量
                    errorNum.getAndUpdate(v -> v + NumberConst.NUM_1);
                    continue;
                }
                //唯一校验
                T uniqueMap = appServiceManual.checkUniqueMap(addFormColumn, moduleInfo, dataMap, poClass);
                if(ObjectUtil.isNotEmpty(uniqueMap)){
                    if(skipOrOver.equals(YesOrNoEnum.YES.getValue())){
                        //覆盖
                        dataPO.setId(ObjectUtil.defaultIfNull(uniqueMap.getId(),dataPO.getId()));
                        overNum.getAndUpdate(v -> v + NumberConst.NUM_1);
                    }else{
                        //错误数量
                        skipNum.getAndUpdate(v -> v + NumberConst.NUM_1);
                        //跳过
                        continue;
                    }
                }
                dataPO.setModuleId(moduleId);
                Long scopeUserId = AppUtil.getSingleColumnIdValue(StrConst.SCOPE_USER, dataMap);
                if(ObjectUtil.isNotEmpty(scopeUserId)){
                    dataPO.setScopeUserId(scopeUserId);
                }
                Long scopeOrgId = AppUtil.getSingleColumnIdValue(StrConst.SCOPE_ORG, dataMap);
                if(ObjectUtil.isNotEmpty(scopeOrgId)){
                    dataPO.setScopeOrgId(scopeOrgId);
                }
                poList.add(dataPO);
                //data保存数据
                AppDataAddBO addBO = new AppDataAddBO();
                addBO.setModuleBaseBO(moduleInfo);
                addBO.setFormId(formId);
                addBO.setInstObj(dataPO);
                addBO.setColumnBOS(formColumnInfo);
                addBO.setDataDoc(dataMap);
                if(ObjectUtil.isEmpty(uniqueMap)){
                    //新增
                    appDataAddBOS.add(addBO);
                }else{
                    //编辑
                    appDataEditBOS.add(addBO);
                }
            }
            //保存实体
            this.saveOrUpdateBatch(poList);
            //保存data
            for (AppDataAddBO item : appDataAddBOS) {
                List<D> dbDataList = appServiceManual.importData(item, poClass, dataPoClass);
                this.addDbDataBatch(dbDataList);
            }
            //保存ES数据
            for (AppDataAddBO item : appDataAddBOS) {
                appServiceManual.importAddEs(item,poClass);
            }
            for (AppDataAddBO item : appDataEditBOS) {
                appServiceManual.importEditEs(item,poClass);
            }
            //日志信息
            for (AppDataAddBO item : appDataAddBOS) {
                if(ObjectUtil.isNotNull(item)){
                    appServiceManual.addLogModify(OperateTypeEnum.IMPORT.getValue(),BeanUtil.toBean(item.getInstObj(),poClass),null,null,null);
                }
            }
        },columnMap);
        try(InputStream inputStream = file.getInputStream()){
            FesodSheet.read(inputStream, uploadDataListener)
                    .sheet()
                    .headRowNumber(NumberConst.NUM_2)
                    .doRead();
            //如果错误结果为空则视为导入成功
            if(CollUtil.isEmpty(errorRecord)){
                importVO.setResult(Boolean.TRUE);
            }else{
                //返回错误结果
                importVO.setContext(errorRecord);
                importVO.setTotalNum(totalNum.get());
                importVO.setSkipNum(skipNum.get());
                importVO.setOverNum(overNum.get());
                importVO.setErrorNum(errorNum.get());
                //持久化错误信息
                ImportErrorBO errorBO = new ImportErrorBO();
                errorBO.setModuleId(moduleId);
                errorBO.setUserId(UserCacheUtil.getUserId());
                errorBO.setBatchId(importVO.getBatchId());
                errorBO.setErrorRecordMap(errorRecord);
                errorBO.setRowDataMap(dataRecord);
                //保存错误记录
                FutureHandleUtil.runNoResult(errorBO,appServiceManual::saveImportError);
            }
        }catch(Exception exception){
            log.error(exception.getMessage());
            return importVO;
        }
        return importVO;
    }

    /**
     * 功能描述:
     * 〈其他修改动作〉
     * @param dataPO dataPO
     * @param dataAddDTO dataAddDTO
     * @author 蝉鸣
     */
    public void preDbOtherAction(T dataPO, DataAddSimpDTO dataAddDTO) {}

    /**
     * 功能描述:
     * 〈其他修改动作〉
     * @param dataPO dataPO
     * @param dataAddDTO dataAddDTO
     * @author 蝉鸣
     */
    public void addOtherAction(T dataPO, DataAddSimpDTO dataAddDTO) {}

    /**
     * 功能描述:
     * 〈其他修改动作〉
     * @param dataPO dataPO
     * @param dataEditDTO dataEditDTO
     * @author 蝉鸣
     */
    public void editOtherAction(T dataPO, DataEditSimpDTO dataEditDTO) {}

    /**
     * 功能描述:
     * 〈返回详情其他处理逻辑〉
     * @param dataVO dataVO
     * @author 蝉鸣
     */
    public <E extends AppVO> E getOtherAction(E dataVO) {return dataVO;}

    /**
     * 功能描述:
     * 〈删除其他处理逻辑〉
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    public void delOtherAction(List<Long> dataIds) {}

    /**
     * 功能描述:
     * 〈其他修改动作〉
     * @param relList relList
     * @author 蝉鸣
     */
    public <R extends AppRelPO> void addDbRelBatch(List<R> relList){}

    /**
     * 功能描述:
     * 〈保存Data数据必须重写〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    public abstract <D extends AppDataPO> void addDbDataBatch(List<D> dataList);


    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    public abstract void transDbScopeBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId);
}
