package com.platform.mesh.app.api.modules.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.idev.excel.FastExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.platform.mesh.app.api.modules.app.domain.bo.AppDataAddBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.*;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.domain.vo.AppVO;
import com.platform.mesh.app.api.modules.app.exception.AppExceptionEnum;
import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.app.api.modules.app.service.manual.AppServiceManual;
import com.platform.mesh.app.api.modules.app.util.MsgUtil;
import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.search.utils.SearchUtil;
import com.platform.mesh.serial.SerialUtil;
import com.platform.mesh.serial.domain.bo.ColumnCompBO;
import com.platform.mesh.upms.api.modules.event.SysMsgNoticeEvent;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.listener.UploadDataListener;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public abstract class AppServiceAbstract<M extends BaseMapper<T>, T extends AppPO> extends CrudRepository<M, T> implements IAppService<T>  {

    @Autowired
    private AppServiceManual appServiceManual;


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
     * 〈获取当前数据对象信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link AppVO}
     * @author 蝉鸣
     */
    @Override
    public <E extends AppVO> E getDataInfoById(EsDocSGetDTO esDocSGetDTO, Class<E> voClass) {
        T dataPO = this.getById(esDocSGetDTO.getDataId());
        if(ObjectUtil.isEmpty(dataPO)){
            return BeanUtil.copyProperties(new AppVO(), voClass);
        }
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(esDocSGetDTO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return BeanUtil.copyProperties(new AppVO(), voClass);
        }
        esDocSGetDTO.setIndexName(moduleInfo.getModuleIndex());
        Object esData = appServiceManual.getDataInfoById(esDocSGetDTO);
        E dataVO = BeanUtil.copyProperties(dataPO, voClass);
        dataVO.setEsData(BeanUtil.beanToMap(esData));
        return dataVO;
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
        //自定义解析
        this.parsePO(dataPO,columnBOS,dataAddSimpDTO.getDocData());
        //保存数据库
        this.save(dataPO);
        //保存data数据
        List<D> dataList = appServiceManual.getDbDataSimp(moduleInfo, dataAddSimpDTO.getFormId(),dataPO.getId(),dataPoClass, columnBOS, dataAddSimpDTO.getDocData());
        Map<String, Object> transMap = appServiceManual.getDataMap(dataList);
        //保存DbData数据
        this.addDbDataBatch(dataList);
        //保存ES数据
        appServiceManual.addEsData(moduleInfo.getModuleIndex(),dataPO, transMap);
        //发送消息
        this.addMsgNotice(moduleInfo,columnBOS,transMap);
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
    public T editData(DataEditSimpDTO dataEditDTO, Class<T> poClass) {
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
        //校验数据
        appServiceManual.checkData(columnBOS,moduleInfo,dataEditDTO.getDocData(),poClass);
        //转化数据
        T dataPO = BeanUtil.copyProperties(dataEditDTO.getDocData(), poClass);
        dataPO.setId(dataEditDTO.getDataId());
        this.updateById(dataPO);
        //修改ES数据
        this.editDbDataBatch(dataPO.getId(),dataEditDTO);
        //修改ES数据
        appServiceManual.editEsData(moduleInfo.getModuleIndex(),dataPO.getId(),dataEditDTO.getDocData());
        return dataPO;
    }

    /**
     * 功能描述:
     * 〈删除数据对象〉
     * @param preCustomerId preCustomerId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteData(Long preCustomerId) {
        T dataPO = getById(preCustomerId);
        if(ObjectUtil.isEmpty(dataPO)){
            return Boolean.FALSE;
        }
        //获取模块信息
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(dataPO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return Boolean.FALSE;
        }
        //删除ES数据
        appServiceManual.deleteEsData(moduleInfo.getModuleIndex(),CollUtil.newArrayList(preCustomerId));
        //删除数据库数据
        this.removeById(preCustomerId);
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
        //获取模块信息
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(delDTO.getModuleId());
        if(ObjectUtil.isEmpty(moduleInfo)){
            return Boolean.FALSE;
        }
        //删除ES数据
        appServiceManual.deleteEsData(moduleInfo.getModuleIndex(),delDTO.getDataIds());
        //删除数据库数据
        this.removeBatchByIds(delDTO.getDataIds());
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
            return Boolean.FALSE;
        }
        transScopeDTO.setScopeUserId(defaultRel.getUserId());
        if(ObjectUtil.isEmpty(transScopeDTO.getScopeOrgId())){}{
            transScopeDTO.setScopeOrgId(defaultRel.getLevelId());
        }
        if(ObjectUtil.isEmpty(transScopeDTO.getScopeOrgId())){
            throw AppExceptionEnum.ADD_NO_ORG_SCOPE_INVALID.getBaseException();
        }
        //修改DB_DATA
        this.transDbDataBatch(transScopeDTO.getIds(),transScopeDTO.getScopeUserId(),transScopeDTO.getScopeOrgId());
        //修改ES
        appServiceManual.transEsData(transScopeDTO);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈导入数据对象〉
     * @param moduleId moduleId
     * @param file file
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <D extends AppDataPO> Boolean importData(Long moduleId, Long formId, MultipartFile file,Class<T> poClass ,Class<D> dataPoClass) {
        //获取模块信息
        AppModuleBaseBO moduleInfo = appServiceManual.getModuleInfo(moduleId);
        //获取模块对应的字段信息
        List<AppFormColumnBO> formColumnInfo = appServiceManual.getFormColumnInfo(moduleId, formId);
        Map<String, String> columnMap = formColumnInfo.stream().collect(Collectors.toMap(AppFormColumnBO::getColumnName, AppFormColumnBO::getColumnMac));
        List<String> transMacs = Arrays.stream(CompTypeEnum.values()).map(CompTypeEnum::getDesc).toList();
        List<String> objFieldNames = ObjFieldUtil.getObjFieldNames(poClass);
        //transMacs需要解析的字段,objFieldNames固定表字段则不解析
        Map<String, AppFormColumnBO> compMap = formColumnInfo.stream()
                .filter(comp->transMacs.contains(comp.getCompMac()))
                .filter(comp->!objFieldNames.contains(StrUtil.toCamelCase(comp.getColumnMac())))
                .collect(Collectors.toMap(AppFormColumnBO::getColumnMac, Function.identity()));
        //分页读取信息,默认100
        UploadDataListener uploadDataListener = new UploadDataListener(dataList -> {
            // dataList分页批量处理
            //保存db对象
            log.debug(dataList.toString());
            List<T> poList = CollUtil.newArrayList();
            List<AppDataAddBO> appDataAddBOS = CollUtil.newArrayList();
            for (Map<String, Object> dataMap : dataList) {
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
                dataPO.setModuleId(moduleId);
                poList.add(dataPO);
                //data保存数据
                AppDataAddBO addBO = new AppDataAddBO();
                addBO.setModuleBaseBO(moduleInfo);
                addBO.setFormId(formId);
                addBO.setInstObj(dataPO);
                addBO.setColumnBOS(formColumnInfo);
                addBO.setDataDoc(dataMap);
                appDataAddBOS.add(addBO);
            }
            //保存实体
            this.saveBatch(poList);
            //保存data
            FutureHandleUtil.runWithResult(appDataAddBOS,item-> {
                List<D> dbDataList = appServiceManual.importData(item, poClass, dataPoClass);
                this.addDbDataBatch(dbDataList);
                return Boolean.TRUE;
            });
            //保存ES数据
            FutureHandleUtil.runWithResult(appDataAddBOS,item-> appServiceManual.importEs(item,poClass));
        },columnMap);
        try{
            FastExcel.read(file.getInputStream(), uploadDataListener)
                    .sheet()
                    .headRowNumber(NumberConst.NUM_2)
                    .doRead();
        }catch(Exception ignore){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈自定义解析〉
     * @param dataPO dataPO
     * @author 蝉鸣
     */
    public void parsePO(T dataPO,List<AppFormColumnBO> columnBOS, Map<String, Object> docData){
    }

    /**
     * 功能描述:
     * 〈发送消息提醒〉
     * @param moduleInfo moduleInfo
     * @param columnBOS columnBOS
     * @param docMap docMap
     * @author 蝉鸣
     */
    public void addMsgNotice(AppModuleBaseBO moduleInfo, List<AppFormColumnBO> columnBOS, Map<String, Object> docMap){
        MsgNoticeBO msgBO = MsgUtil.getMsgBO(moduleInfo, columnBOS, docMap);
        //无效信息不发送
        if(ObjectUtil.isEmpty(msgBO) || ObjectUtil.isEmpty(msgBO.getMsgUserIds())){
            return;
        }
        SpringContextHolderUtil.publishEvent(new SysMsgNoticeEvent(msgBO));
    }

    /**
     * 功能描述:
     * 〈保存Data数据必须重写〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    public abstract  <D extends AppDataPO> void addDbDataBatch(List<D> dataList);

    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    public abstract void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO);

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    public abstract void transDbDataBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId);
}
