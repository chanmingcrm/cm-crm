package com.platform.mesh.tmp.biz.modules.appr.approval.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppDataAddBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.AppFormColumnAddDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddCompDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.dto.TransScopeDTO;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.datascope.constant.DataScopeConst;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.domain.dto.EsDocSGetDTO;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.util.EsUtil;
import com.platform.mesh.search.utils.SearchUtil;
import com.platform.mesh.tmp.biz.modules.appr.approval.domain.po.OaApproval;
import com.platform.mesh.tmp.biz.modules.appr.approvaldata.domain.po.OaApprovalData;
import com.platform.mesh.tmp.biz.modules.appr.approvaldata.service.IOaApprovalDataService;
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
 * @description OA办公审批
 * @author 蝉鸣
 */
@Service
public class OaApprovalServiceManual {

    private final static Logger log = LoggerFactory.getLogger(OaApprovalServiceManual.class);

    @Autowired
    private IEsDocService esDocService;

    @Autowired
    private RemoteAppService remoteAppService;

    @Autowired
    private IOaApprovalDataService oaApprovalDataService;
    

    /**
     * 功能描述:
     * 〈获取数据分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<Object>}
     * @author 蝉鸣
     */
    public PageVO<Object> selectEsPage(EsDocPGetDTO pageDTO) {
        EsDocGetBO esDocGetBO = EsUtil.esPageDtoToBO(pageDTO, SearchUtil.getEsBoolQuery(pageDTO));
        return esDocService.searchDocument(esDocGetBO);
    }
    
    /**
     * 功能描述: 
     * 〈获取当前OA办公审批信息〉
     * @param esDocSGetDTO esDocSGetDTO
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    public Object getOaApprovalInfoById(EsDocSGetDTO esDocSGetDTO) {
        return esDocService.getDocumentById(esDocSGetDTO.getIndexName(), esDocSGetDTO.getDataId());
    }
        
    /**
     * 功能描述:
     * 〈获取当前OA办公审批模块信息〉
     * @param moduleId moduleId
     * @author 蝉鸣
     */
    public AppModuleBaseBO getModuleInfo(Long moduleId) {
        //获取模块信息
        return remoteAppService.getModuleBaseInfoById(moduleId).getData();
    }
    
    /**
     * 功能描述:
     * 〈获取当前OA办公审批表单字段信息〉
     * @param moduleId moduleId
     * @author 蝉鸣
     */
    public List<AppFormColumnBO> getFormColumnInfo(Long moduleId, Long formId) {
        //获取模块信息
        return remoteAppService.getFormColumnList(moduleId, formId).getData();
    }
    
    /**
     * 功能描述:
     * 〈简易字段信息保存〉
     * @param moduleBaseBO moduleBaseBO
     * @param formId formId
     * @param columnBOS columnBOS
     * @param docData docData
     * @author 蝉鸣
     */
    public Map<String, Object> addDbDataSimp(AppModuleBaseBO moduleBaseBO, Long formId, OaApproval data, List<AppFormColumnBO> columnBOS, Map<String, Object> docData) {
       if(ObjectUtil.isEmpty(moduleBaseBO.getId()) || ObjectUtil.isEmpty(formId) || ObjectUtil.isEmpty(docData)){
           return new HashMap<>();
        }
        //过滤不需要赋值的字段信息
        Map<String, AppFormColumnBO> columnMap = columnBOS.stream()
                .filter(item-> !DataTypeEnum.INIT.getValue().equals(item.getDefaultDataType()))
                .collect(Collectors.toMap(AppFormColumnBO::getColumnMac, Function.identity(),(v1,v2)->v2));
        //扩展字段收集
        List<OaApprovalData> onBusinessDataList = CollUtil.newArrayList();
        Map<String, Object> transData = new HashMap<>();
        //转化存储信息
        docData.forEach((key, value) -> {
            OaApprovalData onBusinessData = new OaApprovalData();
            onBusinessData.setModuleId(moduleBaseBO.getId());
            onBusinessData.setParentModuleId(moduleBaseBO.getParentId());
            onBusinessData.setAddFormId(formId);
            onBusinessData.setDataId(data.getId());
            if(columnMap.containsKey(StrUtil.toUnderlineCase(key))) {
                AppFormColumnBO formColumnBO = columnMap.get(StrUtil.toUnderlineCase(key));
                onBusinessData.setColumnId(formColumnBO.getId());
                onBusinessData.setColumnMac(formColumnBO.getColumnMac());
                onBusinessData.setColumnName(formColumnBO.getColumnName());
                onBusinessData.setDataType(formColumnBO.getDefaultDataType());
            }
            onBusinessData.setDataValue(BaseEnum.getEnumByValue(DataTypeEnum.class, onBusinessData.getDataType(),DataTypeEnum.STRING).getDefaultValueStr(value));
            transData.put(key, BaseEnum.getEnumByValue(DataTypeEnum.class, onBusinessData.getDataType(),DataTypeEnum.STRING).getDefaultValue(value));
            onBusinessDataList.add(onBusinessData);
        });
        //批量新增信息
        oaApprovalDataService.saveBatch(onBusinessDataList);
        return transData;
    }
    
    /**
     * 功能描述:
     * 〈全量字段信息保存〉
     * @param dataId dataId
     * @param dataAddCompDTO dataAddCompDTO
     * @author 蝉鸣
     */
    public Map<String, Object> addDbDataComp(Long dataId, DataAddCompDTO dataAddCompDTO) {
        Map<String, Object> dataMap = new HashMap<>();
        List<OaApprovalData> onBusinessDataList = CollUtil.newArrayList();
        for (AppFormColumnAddDTO columnAddDTO : dataAddCompDTO.getDocDataList()) {
            OaApprovalData onBusinessData = new OaApprovalData();
            onBusinessData.setDataId(dataId);
            onBusinessData.setModuleId(dataAddCompDTO.getModuleId());
            onBusinessData.setParentModuleId(dataAddCompDTO.getParentModuleId());
            onBusinessData.setAddFormId(dataAddCompDTO.getFormId());
            onBusinessData.setColumnId(columnAddDTO.getId());
            onBusinessData.setColumnMac(columnAddDTO.getColumnMac());
            onBusinessData.setColumnName(columnAddDTO.getColumnName());
            onBusinessData.setDataType(columnAddDTO.getDefaultDataType());
            String dataValue;
            if(ObjectUtil.isEmpty(columnAddDTO.getValue())){
                dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class, onBusinessData.getDataType(), DataTypeEnum.STRING).getDefaultValueStr(columnAddDTO.getDefaultValue());
            }else{
                dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class, onBusinessData.getDataType(), DataTypeEnum.STRING).getDefaultValueStr(columnAddDTO.getValue());
            }
            onBusinessData.setDataValue(dataValue);
            onBusinessDataList.add(onBusinessData);
            //设置返回参数
            dataMap.put(columnAddDTO.getColumnMac(), onBusinessData.getDataValue());
        }
        //保存data数据
        oaApprovalDataService.saveBatch(onBusinessDataList);

        return dataMap;
    }
    
    /**
     * 功能描述:
     * 〈添加OA办公审批ES数据〉
     * @param indexName indexName
     * @param data data
     * @param docData docData
     * @author 蝉鸣
     */
    public void addEsData(String indexName, OaApproval data, Map<String, Object> docData) {
        EsDocPutBO esDocPutBO = new EsDocPutBO();
        esDocPutBO.setIndexName(indexName);
        esDocPutBO.setDataIds(CollUtil.newArrayList(data.getId()));
        //将对象转化map形式
        Map<String, Object> dataMap = BeanUtil.beanToMap(data,Boolean.TRUE,Boolean.TRUE);
        //覆盖对象最新数据
        docData.putAll(dataMap);
        esDocPutBO.setDocMap(docData);
        String document = esDocService.createDocument(esDocPutBO);
    }
    
    /**
     * 功能描述:
     * 〈修改OA办公审批数据库数据〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    public void editDbData(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //查询已经存在的新增数据
        List<OaApprovalData> onBusinessDataList = oaApprovalDataService.lambdaQuery().eq(OaApprovalData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(OaApprovalData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onBusinessDataList)) {
            return;
        }
        Map<String, Object> docData = dataEditSimpDTO.getDocData();
        for (OaApprovalData onBusinessData : onBusinessDataList) {
            onBusinessData.setEditFormId(dataEditSimpDTO.getFormId());
            if(docData.containsKey(onBusinessData.getColumnMac())) {
                Object value = docData.get(onBusinessData.getColumnMac());
                String dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class,onBusinessData.getDataType(),DataTypeEnum.STRING).getDefaultValueStr(value);
                onBusinessData.setDataValue(dataValue);
            }
        }
        if(CollUtil.isEmpty(onBusinessDataList)){
            return;
        }
        oaApprovalDataService.updateBatchById(onBusinessDataList);
    }
    
    /**
     * 功能描述:
     * 〈修改OA办公审批ES数据〉
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
     * 〈删除OA办公审批ES数据〉
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
        Map<String, Object> docMap = new HashMap<>();
        docMap.put(DataScopeConst.DEFAULT_SCOPE_USER_ID,transScopeDTO.getScopeUserId());
        docMap.put(DataScopeConst.DEFAULT_SCOPE_ORG_ID,transScopeDTO.getScopeOrgId());
        putBO.setDocMap(docMap);
        esDocService.updateDocument(putBO);
    }
    
    
    /**
     * 功能描述:
     * 〈导入DB数据〉
     * @param addBO addBO
     * @author 蝉鸣
     */
    public Boolean importData(AppDataAddBO addBO) {
        if(ObjectUtil.isEmpty(addBO)){
            return Boolean.FALSE;
        }
        this.addDbDataSimp(addBO.getModuleBaseBO(),addBO.getFormId(),BeanUtil.toBean(addBO.getInstObj(),OaApproval.class),addBO.getColumnBOS(),addBO.getDataDoc());
        return Boolean.TRUE;
    }
    
    /**
     * 功能描述:
     * 〈导入ES数据〉
     * @param addBO addBO
     * @author 蝉鸣
     */
    public Boolean importEs(AppDataAddBO addBO) {
        if(ObjectUtil.isEmpty(addBO)){
            return Boolean.FALSE;
        }
        this.addEsData(addBO.getModuleBaseBO().getModuleIndex(),BeanUtil.toBean(addBO.getInstObj(),OaApproval.class),addBO.getDataDoc());
        return Boolean.TRUE;
    }

}