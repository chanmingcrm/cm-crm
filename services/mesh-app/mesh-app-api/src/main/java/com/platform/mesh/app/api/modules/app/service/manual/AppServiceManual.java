package com.platform.mesh.app.api.modules.app.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.*;
import com.platform.mesh.app.api.modules.app.domain.dto.AppFormColumnAddDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppRelPO;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.api.modules.app.exception.AppExceptionEnum;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.api.modules.app.util.MsgUtil;
import com.platform.mesh.app.api.modules.pub.type.app.domain.bo.MsgAppBO;
import com.platform.mesh.app.api.modules.pub.type.app.enums.AppActionEnum;
import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.datascope.constant.DataScopeConst;
import com.platform.mesh.datascope.domain.ScopeBO;
import com.platform.mesh.datascope.utils.DataScopeUtil;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.util.EsUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.search.utils.SearchUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.event.SysModifyLogEvent;
import com.platform.mesh.upms.api.modules.event.SysMsgNoticeEvent;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.ModifyDataBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import com.platform.mesh.upms.api.pub.upms.enums.UpmsActionEnum;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class AppServiceManual {


    private final static Logger log = LoggerFactory.getLogger(AppServiceManual.class);

    @Autowired
    private IEsDocService esDocService;

    @Autowired
    private RemoteAppService remoteAppService;

    @Autowired
    private RemoteOrgMemberService remoteOrgMemberService;


    /**
     * 功能描述:
     * 〈获取ES数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO <Object>}
     * @author 蝉鸣
     */
    public PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO) {
        Boolean ignoreScope = ignoreEsScope(pageDTO);
        List<Query> authQuery = CollUtil.newArrayList();
        if(ObjectUtil.isEmpty(pageDTO.getDataScope())){
            pageDTO.setIgnoreScope(ignoreScope);
        }else{
            pageDTO.setIgnoreScope(Boolean.TRUE);
            //设置过滤数据权限
            ScopeBO scopeBO = DataScopeUtil.parseBiDTO(pageDTO.getDataScope(), pageDTO.getDataFlag(), pageDTO.getDataIds());
            //获取分析过滤参数
            authQuery = SearchUtil.getAuthQuery(scopeBO.getDataScope(),scopeBO.getDataFlag(),scopeBO.getDataIds());
        }
        //设置条件过滤
        BoolQuery.Builder esBoolQuery = SearchUtil.getEsBoolQuery(pageDTO);
        //添加权限处理
        esBoolQuery.filter(authQuery);
        EsDocGetBO esDocGetBO = EsUtil.esPageDtoToBO(pageDTO, esBoolQuery);
        return esDocService.searchDocument(esDocGetBO);
    }

    /**
     * 功能描述:
     * 〈获取当前数据对象信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    public Object getDataInfoById(EsDocSGetDTO esDocSGetDTO) {
        return esDocService.getDocumentById(esDocSGetDTO.getIndexName(), esDocSGetDTO.getDataId());
    }


    /**
     * 功能描述:
     * 〈获取当前客户关系客户对象信息〉
     * @param moduleId moduleId
     * @author 蝉鸣
     */
    public AppModuleBaseBO getModuleInfo(Long moduleId) {
        //获取模块信息
        return remoteAppService.getModuleBaseInfoById(moduleId).getData();
    }

    /**
     * 功能描述:
     * 〈获取当前客户关系客户对象信息〉
     * @param moduleIds moduleIds
     * @author 蝉鸣
     */
    public List<AppModuleBaseBO> getModuleInfo(List<Long> moduleIds) {
        //获取模块信息
        return remoteAppService.getModuleBaseInfoByIds(moduleIds).getData();
    }

    /**
     * 功能描述:
     * 〈根据Schema获取module对象〉
     * @param tableName tableName
     * @author 蝉鸣
     */
    public AppModuleBaseBO getModuleIdBySchema(String tableName) {
        List<AppModuleBaseBO> baseBOS = remoteAppService.getModuleBaseInfoBySchema(CollUtil.newArrayList(tableName)).getData();
        if(CollUtil.isEmpty(baseBOS)){
            return null;
        }
        return CollUtil.getFirst(baseBOS);
    }


    /**
     * 功能描述:
     * 〈获取当前客户关系客户对象信息〉
     * @param moduleId moduleId
     * @author 蝉鸣
     */
    public List<AppFormColumnBO> getFormColumnInfo(Long moduleId, Long formId) {
        //获取模块信息
        return remoteAppService.getFormColumnList(moduleId, formId).getData();
    }


    /**
     * 功能描述:
     * 〈根据moduleId 表单类型获取默认字段〉
     * @param moduleId moduleId
     * @param formType formType
     * @author 蝉鸣
     */
    public List<AppFormColumnBO> getFormColumnInfo(Long moduleId, Integer formType) {
        if(ObjectUtil.isEmpty(moduleId)){
            return CollUtil.newArrayList();
        }
        if(ObjectUtil.isEmpty(formType)){
            formType = FormTypeEnum.FORM_ADD.getValue();
        }
        return remoteAppService.fastColumnByModuleAndFormType(moduleId, formType).getData();
    }


    /**
     * 功能描述:
     * 〈校验数据〉
     * @author 蝉鸣
     */
    public <T extends AppPO> void checkData(List<AppFormColumnBO> columnBOS, AppModuleBaseBO moduleBaseBO, Map<String, Object> docMap, Class<T> poClass) {
        //校验对象数据表与模块配置是否一致
        String tableName = SqlUtil.getTableName(poClass, TableName.class);
        if(!moduleBaseBO.getModuleSchema().equals(tableName)){
            throw AppExceptionEnum.ADD_MODULE_INVALID.getBaseException();
        }
        //校验是否唯一
        Map<String, Object> uniqueMap = AppUtil.checkUniqueMap(columnBOS, docMap);
        Boolean dataUnique = SearchUtil.checkDataUnique(moduleBaseBO.getModuleIndex(), uniqueMap);
        if(dataUnique){
            throw AppExceptionEnum.ADD_EXISTS_INVALID.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈校验数据〉
     * @author 蝉鸣
     */
    public <T extends AppPO> T checkUniqueMap(List<AppFormColumnBO> columnBOS, AppModuleBaseBO moduleBaseBO, Map<String, Object> docMap, Class<T> poClass) {
        //校验是否唯一
        Map<String, Object> uniqueMap = AppUtil.checkUniqueMap(columnBOS, docMap);
        Object dataUnique = SearchUtil.getExistData(moduleBaseBO.getModuleIndex(), uniqueMap);
        if(ObjectUtil.isEmpty(dataUnique)){
            return null;
        }
        return BeanUtil.toBean(dataUnique, poClass);
    }

    /**
     * 功能描述:
     * 〈校验数据〉
     * @param columnBOS columnBOS
     * @param docMap docMap
     * @author 蝉鸣
     */
    public List<String> checkedMap(List<AppFormColumnBO> columnBOS, Map<String, Object> docMap) {
        List<String> rowList = CollUtil.newArrayList();
        //校验是否必填
        Map<String, String> emptyMap = AppUtil.checkEmptyMap(columnBOS, docMap);
        if(CollUtil.isNotEmpty(emptyMap)){
            StringBuilder builder = StrUtil.builder();
            builder.append(AppExceptionEnum.EXCEL_IMPORT_DATA_EMPTY.getDesc());
            //添加空信息
            String emptyColumn = String.join(SymbolConst.COMMA, emptyMap.values());
            builder.append(SymbolConst.COLON).append(emptyColumn);
            rowList.add(builder.toString());
        }
        //校验人员信息
        Map<String, String> userMap = AppUtil.checkUserMap(columnBOS, emptyMap.keySet(), docMap);
        if(CollUtil.isNotEmpty(userMap)){
            StringBuilder builder = StrUtil.builder();
            builder.append(AppExceptionEnum.EXCEL_IMPORT_USER_EMPTY.getDesc());
            //添加空信息
            String userColumn = String.join(SymbolConst.COMMA, userMap.values());
            builder.append(SymbolConst.COLON).append(userColumn);
            rowList.add(builder.toString());
        }
        //校验组织信息
        Map<String, String> orgMap = AppUtil.checkOrgMap(columnBOS, emptyMap.keySet(), docMap);
        if(CollUtil.isNotEmpty(orgMap)){
            StringBuilder builder = StrUtil.builder();
            builder.append(AppExceptionEnum.EXCEL_IMPORT_ORG_EMPTY.getDesc());
            //添加空信息
            String orgColumn = String.join(SymbolConst.COMMA, orgMap.values());
            builder.append(SymbolConst.COLON).append(orgColumn);
            rowList.add(builder.toString());
        }
        return rowList;
    }

    /**
     * 功能描述:
     * 〈简易字段信息保存〉
     * @param moduleBaseBO moduleBaseBO
     * @param docData docData
     * @author 蝉鸣
     */
    public <D extends AppDataPO> List<D> getDbDataSimp(AppModuleBaseBO moduleBaseBO, Long dataId,Class<D> dataClass, List<AppFormColumnBO> columnBOS, Map<String, Object> docData) {
        if(ObjectUtil.isEmpty(moduleBaseBO.getId()) || ObjectUtil.isEmpty(docData)){
            return CollUtil.newArrayList();
        }
        //过滤不需要赋值的字段信息
        Map<String, AppFormColumnBO> columnMap = columnBOS.stream()
                .filter(item-> !DataTypeEnum.INIT.getValue().equals(item.getDefaultDataType()))
                .collect(Collectors.toMap(AppFormColumnBO::getColumnMac, Function.identity(),(v1, v2)->v2));
        //扩展字段收集
        List<D> dataList = CollUtil.newArrayList();
        //转化存储信息
        docData.forEach((key, value) -> {
            if(ObjectUtil.isNotEmpty(AppUtil.getMapKey(columnMap,key)) && ObjectUtil.isNotEmpty(value)) {
                AppFormColumnBO formColumnBO = columnMap.get(AppUtil.getMapKey(columnMap,key));
                D dataPO = BeanUtil.copyProperties(new AppDataPO(), dataClass);
                dataPO.setModuleId(moduleBaseBO.getId());
                dataPO.setParentModuleId(moduleBaseBO.getParentId());
                dataPO.setDataId(dataId);
                dataPO.setColumnId(formColumnBO.getId());
                dataPO.setColumnMac(formColumnBO.getColumnMac());
                dataPO.setColumnName(formColumnBO.getColumnName());
                if(ObjectUtil.isEmpty(formColumnBO.getDefaultDataType())){
                    dataPO.setDataType(EsUtil.getDefaultDataType(formColumnBO.getColumnMac()));
                }else{
                    dataPO.setDataType(formColumnBO.getDefaultDataType());
                }
                Object dataValue;
                if(ObjectUtil.isEmpty(value)){
                    dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class, dataPO.getDataType(), DataTypeEnum.STRING).getDefaultValue(formColumnBO.getDefaultValue());
                }else{
                    dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class, dataPO.getDataType(), DataTypeEnum.STRING).getDefaultValue(value);
                }
                dataPO.setDataValue(dataValue);
                dataList.add(dataPO);
            }
        });
        return dataList;
    }


    /**
     * 功能描述:
     * 〈全量字段信息保存〉
     * @param dataId dataId
     * @param dataAddCompDTO dataAddCompDTO
     * @author 蝉鸣
     */
    public <D extends AppDataPO> List<D> getDbDataComp(Long dataId, DataAddCompDTO dataAddCompDTO, Class<D> dataClass) {
        List<D> dataList = CollUtil.newArrayList();
        for (AppFormColumnAddDTO columnAddDTO : dataAddCompDTO.getDocDataList()) {
            D dataPO = BeanUtil.copyProperties(new AppDataPO(), dataClass);
            dataPO.setDataId(dataId);
            dataPO.setModuleId(dataAddCompDTO.getModuleId());
            dataPO.setParentModuleId(dataAddCompDTO.getParentModuleId());
//            dataPO.setAddFormId(dataAddCompDTO.getFormId());
            dataPO.setColumnId(columnAddDTO.getId());
            dataPO.setColumnMac(columnAddDTO.getColumnMac());
            dataPO.setColumnName(columnAddDTO.getColumnName());
            dataPO.setDataType(columnAddDTO.getDefaultDataType());
            Object dataValue;
            if(ObjectUtil.isEmpty(columnAddDTO.getValue())){
                dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class, dataPO.getDataType(), DataTypeEnum.STRING).getDefaultValue(columnAddDTO.getDefaultValue());
            }else{
                dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class, dataPO.getDataType(), DataTypeEnum.STRING).getDefaultValue(columnAddDTO.getValue());
            }
            dataPO.setDataValue(dataValue);
            dataList.add(dataPO);
        }
        return dataList;
    }

    /**
     * 功能描述:
     * 〈简易字段信息保存〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    public <D extends AppDataPO> Map<String, Object> getDataMap(List<D> dataList) {
        if(ObjectUtil.isEmpty(dataList)){
            return new HashMap<>();
        }
        //扩展字段收集
        Map<String, Object> dataMap = new HashMap<>();
        //转化存储信息
        dataList.forEach(data -> {
            dataMap.put(data.getColumnMac(), data.getDataValue());
        });
        return dataMap;
    }

    /**
     * 功能描述:
     * 〈获取当前客户关系客户对象信息〉
     * @param indexName indexName
     * @param dataId dataId
     * @author 蝉鸣
     */
    public Map<String, Object> getEsData(String indexName, Long dataId) {
        Object document = esDocService.getDocumentById(indexName, dataId);
        if(ObjectUtil.isEmpty(document)){
            return new HashMap<>();
        }
        return AppUtil.beanToMap(document);
    }


    public List<Object> getEsByIds(String indexName, List<Long> ids) {
        return esDocService.getDocumentByIds(indexName, ids);
    }


    /**
     * 功能描述:
     * 〈添加当前客户关系客户对象信息〉
     * @param indexName indexName
     * @param poClass poClass
     * @param docData docData
     * @author 蝉鸣
     */
    public <E extends AppPO> void addEsData(String indexName, E poClass, Map<String, Object> docData) {
        EsDocPutBO esDocPutBO = new EsDocPutBO();
        esDocPutBO.setIndexName(indexName);
        esDocPutBO.setDataIds(CollUtil.newArrayList(poClass.getId()));
        //将对象转化map形式
        Map<String, Object> dataMap = AppUtil.beanToMap(poClass);
        //覆盖对象最新数据
        docData.putAll(dataMap);
        //填充扩展数据
        Map<String, Object> scopeMap = fillEsScopeData(poClass);
        docData.putAll(scopeMap);
        //其他数据数据
        fillOtherData(docData);
        //保存数据
        esDocPutBO.setDocMap(docData);
        String document = esDocService.createDocument(esDocPutBO);
        log.info(document);
    }

    /**
     * 功能描述:
     * 〈获取当前客户关系客户对象信息〉
     * @param indexName indexName
     * @param poClass poClass
     * @param docData docData
     * @author 蝉鸣
     */
    public <E extends AppPO> void editEsData(String indexName, E poClass, Map<String, Object> docData) {
        EsDocPutBO esDocPutBO = new EsDocPutBO();
        esDocPutBO.setIndexName(indexName);
        esDocPutBO.setDataIds(CollUtil.newArrayList(poClass.getId()));
        //将对象转化map形式
        Map<String, Object> dataMap = AppUtil.beanToMap(poClass);
        //覆盖对象最新数据
        docData.putAll(dataMap);
        //填充扩展数据
        Map<String, Object> scopeMap = fillUpdateData(poClass);
        docData.putAll(scopeMap);
        //其他数据数据
        fillOtherData(docData);
        //更新数据
        esDocPutBO.setDocMap(docData);
        esDocService.updateDocument(esDocPutBO);
    }


    /**
     * 功能描述:
     * 〈删除当前客户关系客户对象信息〉
     * @param indexName indexName
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    public void deleteEsData(String indexName,List<Long> dataIds) {
        EsDocPutBO esDocPutBO = new EsDocPutBO();
        esDocPutBO.setIndexName(indexName);
        esDocPutBO.setDataIds(dataIds);
        esDocService.deleteDocument(esDocPutBO);
    }


    /**
     * 功能描述:
     * 〈转移数据权限〉
     * @author 蝉鸣
     */
    public void transEsData(TransScopeDTO transScopeDTO) {
        EsDocPutBO putBO = new EsDocPutBO();
        putBO.setIndexName(transScopeDTO.getIndexName());
        putBO.setDataIds(transScopeDTO.getIds());
        Map<String, Object> scopeMap = fillEsScopeData(transScopeDTO.getScopeUserId(), transScopeDTO.getScopeOrgId());
        Map<String, Object> docMap = new HashMap<>(scopeMap);
        putBO.setDocMap(docMap);
        esDocService.updateDocument(putBO);
    }

    /**
     * 功能描述:
     * 〈导入数据〉
     * @author 蝉鸣
     */
    public <T extends AppPO,D extends AppDataPO> List<D> importData(AppDataAddBO addBO,Class<T> poClass, Class<D> dataPoClass) {
        if(ObjectUtil.isEmpty(addBO)){
            return CollUtil.newArrayList();
        }
        return this.getDbDataSimp(addBO.getModuleBaseBO(), BeanUtil.toBean(addBO.getInstObj(), poClass).getId(), dataPoClass, addBO.getColumnBOS(), addBO.getDataDoc());
    }

    /**
     * 功能描述:
     * 〈导入数据〉
     * @author 蝉鸣
     */
    public <T extends AppPO> Boolean importAddEs(AppDataAddBO addBO, Class<T> poClass) {
        if(ObjectUtil.isEmpty(addBO)){
            return Boolean.FALSE;
        }
        this.addEsData(addBO.getModuleBaseBO().getModuleIndex(),BeanUtil.toBean(addBO.getInstObj(),poClass),addBO.getDataDoc());
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈导入数据〉
     * @author 蝉鸣
     */
    public <T extends AppPO> Boolean importEditEs(AppDataAddBO addBO, Class<T> poClass) {
        if(ObjectUtil.isEmpty(addBO)){
            return Boolean.FALSE;
        }
        this.editEsData(addBO.getModuleBaseBO().getModuleIndex(),BeanUtil.toBean(addBO.getInstObj(),poClass),addBO.getDataDoc());
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈导入数据〉
     * @author 蝉鸣
     */
    public <T extends AppPO> Boolean importModifyLog(AppDataAddBO addBO, Class<T> poClass) {
        if(ObjectUtil.isEmpty(addBO)){
            return Boolean.FALSE;
        }
        this.addEsData(addBO.getModuleBaseBO().getModuleIndex(),BeanUtil.toBean(addBO.getInstObj(),poClass),addBO.getDataDoc());
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈根据成员ID获取默认关系〉
     * @author 蝉鸣
     */
    public OrgMemberRelBO getDefaultRelByMemberId(Long memberId) {
        return remoteOrgMemberService.getOrgMemberUserDefaultRelByMemberId(memberId).getData();
    }

    /**
     * 功能描述:
     * 〈根据成员ID获取默认关系〉
     * @author 蝉鸣
     */
    public Boolean ignoreEsScope(EsDocPGetDTO pageDTO) {
        List<Long> moduleIds = pageDTO.getCondDTO().stream()
                .filter(cond -> ObjectUtil.isNotEmpty(cond) && ObjectUtil.isNotEmpty(cond.getColumnMac()) && cond.getColumnMac().equals(StrConst.MODULE_ID))
                .map(CondDTO::getSearchValues).flatMap(List::stream).map(Long::parseLong)
                .toList();
        //如果大于1则不可以忽略权限
        if(CollUtil.isEmpty(moduleIds) || moduleIds.size() > NumberConst.NUM_1){
            return Boolean.FALSE;
        }
        Long moduleId = CollUtil.getFirst(moduleIds);
        AppModuleBaseBO baseBO = remoteAppService.getModuleBaseInfoById(moduleId).getData();
        if(ObjectUtil.isEmpty(baseBO)){
            return Boolean.FALSE;
        }
        if(ObjectUtil.isEmpty(baseBO.getOpenFlag())){
            return Boolean.FALSE;
        }
        if(baseBO.getOpenFlag().equals(YesOrNoEnum.YES.getValue())){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 功能描述:
     * 〈根据成员ID获取默认关系〉
     * @author 蝉鸣
     */
    public Map<String,Object> fillEsScopeData(AppPO appPO) {
        Map<String,Object> map  = new HashMap<>();
        //填充扩展数据
        SysUserBO sysUserBO = UserCacheUtil.getSysUserInfoCache(appPO.getCreateUserId());
        if(ObjectUtil.isNotEmpty(sysUserBO)){
            JSONArray array = JSONUtil.createArray();
            JSONObject object = JSONUtil.createObj();
            object.set(CompTypeEnum.USER.getIdMac(), appPO.getCreateUserId());
            object.set(CompTypeEnum.USER.getNameMac(), sysUserBO.getNickName());
            array.add(object);
            map.put(DataScopeConst.DEFAULT_CREATE_USER,array);
            map.put(DataScopeConst.DEFAULT_UPDATE_USER,array);
        }
        Map<String, Object> scopeData = fillEsScopeData(appPO.getScopeUserId(), appPO.getScopeOrgId());
        map.putAll(scopeData);
        return map;
    }

    /**
     * 功能描述:
     * 〈根据成员ID获取默认关系〉
     * @author 蝉鸣
     */
    public Map<String,Object> fillEsScopeData(Long userId,Long orgId) {
        Map<String,Object> map  = new HashMap<>();
        //填充扩展数据
        SysUserBO userCache = UserCacheUtil.getSysUserInfoCache(userId);
        //解析成员
        if(ObjectUtil.isNotEmpty(userCache)){
            JSONArray array = JSONUtil.createArray();
            JSONObject object = JSONUtil.createObj();
            object.set(CompTypeEnum.USER.getIdMac(), userId);
            object.set(CompTypeEnum.USER.getNameMac(), userCache.getNickName());
            array.add(object);
            map.put(DataScopeConst.DEFAULT_SCOPE_USER_ID,userId);
            map.put(DataScopeConst.DEFAULT_SCOPE_USER,array);
            if(!map.containsKey(DataScopeConst.DEFAULT_CREATE_USER)){
                map.put(DataScopeConst.DEFAULT_CREATE_USER,array);
                map.put(DataScopeConst.DEFAULT_UPDATE_USER,array);
            }
        }
        SysOrgInfoBO sysOrgBO = UserCacheUtil.getSysOrgInfoCache(orgId);
        if(ObjectUtil.isNotEmpty(sysOrgBO)){
            JSONArray array = JSONUtil.createArray();
            JSONObject object = JSONUtil.createObj();
            object.set(CompTypeEnum.DEP.getIdMac(), orgId);
            object.set(CompTypeEnum.DEP.getNameMac(), sysOrgBO.getLevelName());
            array.add(object);
            map.put(DataScopeConst.DEFAULT_SCOPE_ORG_ID,orgId);
            map.put(DataScopeConst.DEFAULT_SCOPE_ORG,array);
        }
        return map;
    }

    /**
     * 功能描述:
     * 〈根据成员ID获取默认关系〉
     * @author 蝉鸣
     */
    public Map<String,Object> fillUpdateData(AppPO appPO) {
        Map<String,Object> map  = new HashMap<>();
        if(ObjectUtil.isEmpty(appPO)){
            return map;
        }
        //填充扩展数据
        SysUserBO sysUserBO = UserCacheUtil.getSysUserInfoCache(appPO.getUpdateUserId());
        if(ObjectUtil.isNotEmpty(sysUserBO)){
            JSONArray array = JSONUtil.createArray();
            JSONObject object = JSONUtil.createObj();
            object.set(CompTypeEnum.USER.getIdMac(), appPO.getCreateUserId());
            object.set(CompTypeEnum.USER.getNameMac(), sysUserBO.getNickName());
            array.add(object);
            map.put(DataScopeConst.DEFAULT_UPDATE_USER,array);
        }
        return map;
    }

    /**
     * 功能描述:
     * 〈额外处理:Nested类型全部转换为Flattened类型，Flattened本身不支持聚合查询,需要提取数据组合新字段用于支持聚合查询〉
     * @author 蝉鸣
     */
    public void fillOtherData(Map<String, Object> docMap) {
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        if(CollUtil.isEmpty(docMap)){
            return;
        }
        //如果是JSON_ARRAY数据，则把值扁平化处理，方便聚合查询
        docMap.forEach((key,value)->{
            if(key.endsWith(EsConst.MAPPING_SUFFIX_JSON)){
                List<String> idValue = AppUtil.getUniColumnIdValue(key, value);
                ids.addAll(idValue);

                List<String> nameValue = AppUtil.getUniColumnNameValue(key, value);
                names.addAll(nameValue);
            }
        });
        //id聚合字段
        docMap.put(StrConst.ID_UNI,ids);
        //name聚合字段
        docMap.put(StrConst.NAME_UNI,names);
    }

    /**
     * 功能描述:
     * 〈自定义解析〉
     * @param dataPO dataPO
     * @author 蝉鸣
     */
    public <T extends AppPO> List<AppRelPO> parseRelPO(T dataPO, List<AppFormColumnBO> columnBOS, Map<String, Object> docData){
        List<AppRelPO> relPOS = CollUtil.newArrayList();
        if(ObjectUtil.isEmpty(dataPO) || CollUtil.isEmpty(columnBOS) || CollUtil.isEmpty(docData)){
            return relPOS;
        }
        //获取关联类型的字段
        List<AppFormColumnBO> relColumns = columnBOS.stream().filter(column ->
                column.getCompMac().equals(CompTypeEnum.RELEVANCE.getDesc())
                || column.getCompMac().equals(CompTypeEnum.RELEVANCE_ALL_FIELD.getDesc())
        ).toList();
        if(CollUtil.isEmpty(relColumns)){
            return relPOS;
        }
        for (AppFormColumnBO relColumn : relColumns) {
            //模块Id
            Long relModuleId = NumberConst.NUM_0.longValue();
            if(relColumn.getCompMac().equals(CompTypeEnum.RELEVANCE.getDesc())){
                //获取关联配置信息
                Object setDataValue = relColumn.getSetDataValue();
                if(ObjectUtil.isEmpty(setDataValue)){
                    continue;
                }
                relModuleId = parseModuleId(setDataValue);
            }
            //获取参数值
            Object object = docData.get(relColumn.getColumnMac());
            if(ObjectUtil.isEmpty(object)){
                continue;
            }
            //解析参数值
            String jsonStr = JSONUtil.toJsonStr(object);
            JSONArray jsonArray = JSONUtil.parseArray(jsonStr);
            for (Object json : jsonArray) {
                JSONObject entries = JSONUtil.parseObj(json);
                Object value = entries.get(CompTypeEnum.RELEVANCE.getIdMac());
                if (relColumn.getCompMac().equals(CompTypeEnum.RELEVANCE_ALL_FIELD.getDesc())) {
                    Object moduleId = entries.get(StrUtil.toCamelCase(StrConst.MODULE_ID));
                    relModuleId = ObjectUtil.isEmpty(moduleId)? NumberConst.NUM_0.longValue():Long.parseLong(StrUtil.toString(moduleId));
                }
                if (ObjectUtil.isNotEmpty(value) && NumberUtil.isNumber(value.toString())) {
                    AppRelPO appRelPO = new AppRelPO();
                    appRelPO.setModuleId(dataPO.getModuleId());
                    appRelPO.setDataId(dataPO.getId());
                    appRelPO.setColumnId(relColumn.getId());
                    appRelPO.setRelModuleId(relModuleId);
                    appRelPO.setRelDataId(Long.valueOf(value.toString()));
                    relPOS.add(appRelPO);
                }
            }
        }
        return relPOS;
    }

    /**
     * 功能描述:
     * 〈发送消息提醒〉
     * @param setDataValue setDataValue
     * @author 蝉鸣
     */
    public Long parseModuleId(Object setDataValue){
        try{
            if (setDataValue instanceof String) {
                List<Long> moduleIds = Arrays.stream(StrUtil.toString(setDataValue).split(SymbolConst.COMMA)).map(StrUtil::trim).map(Long::parseLong).toList();
                return CollUtil.getLast(moduleIds);
            }
            if (setDataValue instanceof Map) {
                BindColumnBO bindColumnBO = BeanUtil.toBean(setDataValue, BindColumnBO.class);
                return bindColumnBO.getBindParams().getModuleId();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        List<MsgNoticeBO> msgBOS = MsgUtil.getMsgBO(moduleInfo, columnBOS, docMap);
        //无效信息不发送
        if(CollUtil.isEmpty(msgBOS)){
            return;
        }
        msgBOS.forEach(msgBO -> {
            if(CollUtil.isEmpty(msgBO.getMsgUserIds())){
                return;
            }
            SpringContextHolderUtil.publishEvent(new SysMsgNoticeEvent(msgBO));
        });
    }

    /**
     * 功能描述:
     * 〈发送消息提醒〉
     * @param po po
     * @param oldMap oldMap
     * @param newMap newMap
     * @author 蝉鸣
     */
    public <T extends AppPO> void addLogModify(Integer operateType, T po, List<AppFormColumnBO> columnBOS, Map<String, Object> oldMap, Map<String, Object> newMap){
        //无效信息不发送
        if(ObjectUtil.isEmpty(po.getId())){
            return;
        }
        LogModifyBO modifyBO = new LogModifyBO();
        modifyBO.setModuleId(po.getModuleId());
        modifyBO.setDataId(po.getId());
        modifyBO.setKeyName(po.getDataName());
        modifyBO.setOperateType(operateType);
        List<ModifyDataBO> diffData = AppUtil.getDiffData(columnBOS, oldMap, newMap);
        if(CollUtil.isNotEmpty(diffData)){
            modifyBO.setValueJson(JSONUtil.toJsonStr(diffData));
        }
        SpringContextHolderUtil.publishEvent(new SysModifyLogEvent(modifyBO));
    }

    /**
     * 功能描述:
     * 〈导入错误信息保存〉
     * @param errorBO errorBO
     * @author 蝉鸣
     */
    public void saveImportError(ImportErrorBO errorBO) {
        remoteAppService.saveImportError(errorBO);
    }

    /**
     * 功能描述:
     * 〈同步关联模块信息名称〉
     * @param po po
     * @author 蝉鸣
     */
    public <T extends AppPO> void pubSyncName(T po) {
        //查询关联当前模块的所有模块以及字段信息
        List<SyncDataBO> syncDataBOS = remoteAppService.getRelModuleToSync(po.getModuleId()).getData();
        if(CollUtil.isEmpty(syncDataBOS)){
            return;
        }
        for (SyncDataBO dataBO : syncDataBOS) {
            MsgAppBO msgAppBO = new MsgAppBO();
            msgAppBO.setDataId(po.getId());
            msgAppBO.setModuleId(dataBO.getModuleId());
            msgAppBO.setModuleIndex(dataBO.getModuleIndex());
            msgAppBO.setModuleSchema(dataBO.getModuleSchema());
            msgAppBO.setActionType(AppActionEnum.SYNC_NAME.getValue());
            Map<String,Object> hashMap = new HashMap<>();
            hashMap.put(StrConst.APP_DATA_COLUMN,dataBO.getFiledList());
            hashMap.put(StrConst.NAME,po.getDataName());
            msgAppBO.setExtendJson(hashMap);
            RedissonUtil.publish(dataBO.getModuleSchema(),msgAppBO);
        }
    }

    /**
     * 功能描述:
     * 〈删除第三方关联数据〉
     * @param dataIds dataIds
     * @param moduleInfo moduleInfo
     * @author 蝉鸣
     */
    public void delRel(List<Long> dataIds, AppModuleBaseBO moduleInfo) {
        //通过Redisson异步发送
        MsgUpmsBO msgUpmsBO = new MsgUpmsBO();
        Map<String, Object> map = new HashMap<>();
        map.put(StrConst.VALUE,dataIds);
        map.put(StrConst.MODULE_ID,moduleInfo.getId());
        msgUpmsBO.setExtendJson(map);
        msgUpmsBO.setActionType(UpmsActionEnum.DEL_CRM_SYNC_THIRD_REL.getValue());
        RedissonUtil.publish(ServiceNameConst.CRM_SERVICE,msgUpmsBO);
    }

    /**
     * 功能描述:
     * 〈删除关联流程数据〉
     * @param dataIds dataIds
     * @param moduleInfo moduleInfo
     * @author 蝉鸣
     */
    public void delBpm(List<Long> dataIds, AppModuleBaseBO moduleInfo) {
        //通过Redisson异步发送
        MsgUpmsBO msgUpmsBO = new MsgUpmsBO();
        Map<String, Object> map = new HashMap<>();
        map.put(StrConst.VALUE,dataIds);
        map.put(StrConst.MODULE_ID,moduleInfo.getId());
        msgUpmsBO.setExtendJson(map);
        msgUpmsBO.setActionType(UpmsActionEnum.DEL_BPM_DATA_REL.getValue());
        RedissonUtil.publish(ServiceNameConst.BPM_SERVICE,msgUpmsBO);
    }


}
