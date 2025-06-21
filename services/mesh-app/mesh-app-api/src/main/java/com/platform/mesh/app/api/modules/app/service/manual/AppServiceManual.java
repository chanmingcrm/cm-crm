package com.platform.mesh.app.api.modules.app.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppDataAddBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.AppFormColumnAddDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.exception.AppExceptionEnum;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.datascope.constant.DataScopeConst;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.util.EsUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.search.utils.SearchUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        EsDocGetBO esDocGetBO = EsUtil.esPageDtoToBO(pageDTO, SearchUtil.getEsBoolQuery(pageDTO));
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
     * @param moduleId moduleId
     * @author 蝉鸣
     */
    public List<AppFormColumnBO> getFormColumnInfo(Long moduleId, Long formId) {
        //获取模块信息
        return remoteAppService.getFormColumnList(moduleId, formId).getData();
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
     * 〈简易字段信息保存〉
     * @param moduleBaseBO moduleBaseBO
     * @param formId formId
     * @param docData docData
     * @author 蝉鸣
     */
    public <D extends AppDataPO> List<D> getDbDataSimp(AppModuleBaseBO moduleBaseBO, Long formId, Long dataId,Class<D> dataClass, List<AppFormColumnBO> columnBOS, Map<String, Object> docData) {
        if(ObjectUtil.isEmpty(moduleBaseBO.getId()) || ObjectUtil.isEmpty(formId) || ObjectUtil.isEmpty(docData)){
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
            if(columnMap.containsKey(StrUtil.toUnderlineCase(key)) && ObjectUtil.isNotEmpty(value)) {
                AppFormColumnBO formColumnBO = columnMap.get(StrUtil.toUnderlineCase(key));
                D dataPO = BeanUtil.copyProperties(new AppDataPO(), dataClass);
                dataPO.setModuleId(moduleBaseBO.getId());
                dataPO.setParentModuleId(moduleBaseBO.getParentId());
                dataPO.setAddFormId(formId);
                dataPO.setDataId(dataId);
                dataPO.setColumnId(formColumnBO.getId());
                dataPO.setColumnMac(formColumnBO.getColumnMac());
                dataPO.setColumnName(formColumnBO.getColumnName());
                dataPO.setDataType(formColumnBO.getDefaultDataType());
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
            dataPO.setAddFormId(dataAddCompDTO.getFormId());
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
     * @param poClass poClass
     * @param docData docData
     * @author 蝉鸣
     */
    public <E extends AppPO> void addEsData(String indexName, E poClass, Map<String, Object> docData) {
        EsDocPutBO esDocPutBO = new EsDocPutBO();
        esDocPutBO.setIndexName(indexName);
        esDocPutBO.setDataIds(CollUtil.newArrayList(poClass.getId()));
        //将对象转化map形式
        Map<String, Object> dataMap = BeanUtil.beanToMap(poClass,Boolean.TRUE,Boolean.TRUE);
        //覆盖对象最新数据
        docData.putAll(dataMap);
        //填充扩展数据
        Map<String, Object> scopeMap = fileEsScopeData(poClass.getScopeUserId(), poClass.getScopeOrgId());
        docData.putAll(scopeMap);
        //保存数据
        esDocPutBO.setDocMap(docData);
        String document = esDocService.createDocument(esDocPutBO);
        log.info(document);
    }

    /**
     * 功能描述:
     * 〈获取当前客户关系客户对象信息〉
     * @param indexName indexName
     * @param dataId dataId
     * @param docData docData
     * @author 蝉鸣
     */
    public void editEsData(String indexName, Long dataId, Map<String, Object> docData) {
        EsDocPutBO esDocPutBO = new EsDocPutBO();
        esDocPutBO.setIndexName(indexName);
        esDocPutBO.setDataIds(CollUtil.newArrayList(dataId));
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
        Map<String, Object> scopeMap = fileEsScopeData(transScopeDTO.getScopeUserId(), transScopeDTO.getScopeOrgId());
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
        return this.getDbDataSimp(addBO.getModuleBaseBO(), addBO.getFormId(), BeanUtil.toBean(addBO.getInstObj(), poClass).getId(), dataPoClass, addBO.getColumnBOS(), addBO.getDataDoc());
    }

    /**
     * 功能描述:
     * 〈导入数据〉
     * @author 蝉鸣
     */
    public <T extends AppPO> Boolean importEs(AppDataAddBO addBO, Class<T> poClass) {
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
    public Map<String,Object> fileEsScopeData(Long userId,Long orgId) {
        Map<String,Object> map  = new HashMap<>();
        //填充扩展数据
        SysUserBO sysUserBO = UserCacheUtil.getSysUserInfoCache(userId);
        if(ObjectUtil.isNotEmpty(sysUserBO)){
            JSONArray array = JSONUtil.createArray();
            JSONObject object = JSONUtil.createObj();
            object.set(CompTypeEnum.USER.getIdMac(), userId);
            object.set(CompTypeEnum.USER.getNameMac(), sysUserBO.getNickName());
            array.add(object);
            map.put(DataScopeConst.DEFAULT_SCOPE_USER_ID,userId);
            map.put(DataScopeConst.DEFAULT_SCOPE_USER,array);
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


}
