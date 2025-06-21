package com.platform.mesh.app.api.modules.init.db.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.platform.mesh.app.api.modules.app.domain.bo.*;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransBO;
import com.platform.mesh.app.api.modules.init.db.exception.DbExceptionEnum;
import com.platform.mesh.app.api.modules.init.db.mapper.DbMapper;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.util.EsUtil;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.mybatis.plus.enums.MateFillEnum;
import com.platform.mesh.mybatis.plus.handler.FormatTableNameHandler;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.search.utils.SearchUtil;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.serial.SerialUtil;
import com.platform.mesh.serial.domain.bo.ColumnCompBO;
import com.platform.mesh.serial.domain.bo.SerialBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.format.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系Db数据
 * @author 蝉鸣
 */
public abstract class DbServiceManual {

    private final static Logger log = LoggerFactory.getLogger(DbServiceManual.class);

    @Autowired
    private IEsDocService esDocService;

    @Autowired
    private RemoteAppService remoteAppService;

    @Autowired
    private RemoteOrgMemberService remoteOrgMemberService;

    /**
     * 功能描述:
     * 〈实现Mapper〉
     * @return 正常返回:{@link DbMapper}
     * @author 蝉鸣
     */
    public abstract DbMapper getDbMapper();

    /**
     * 功能描述:
     * 〈获取应用表名〉
     * @return 正常返回:{@link List<String>}
     * @author 蝉鸣
     */
    public List<String> selectAppTables(){
        List<String> appTables = this.getDbMapper().selectAppTables();
        return appTables.stream().map(table -> StrUtil.replace(table, StrConst.DATA_SUFFIX, StrUtil.EMPTY)).toList();
    };

    /**
     * 功能描述:
     * 〈搜索文档〉
     * @return 正常返回:{@link List<String>}
     * @author 蝉鸣
     */
    public PageVO<Object> searchDocument(EsDocPGetDTO searchGetDTO) {
        EsDocGetBO searchGetBO = EsUtil.esPageDtoToBO(searchGetDTO, SearchUtil.getEsBoolQuery(searchGetDTO));
        return esDocService.searchDocument(searchGetBO);
    }

    /**
     * 功能描述:
     * 〈获取客户关系转化设置〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public PageVO<AppModuleSetTransBO> getModuleSetTransPage(ModulePageDTO pageDTO) {
        return remoteAppService.getModuleSetTransPage(pageDTO).getData();
    }

    /**
     * 功能描述:
     * 〈获取客户关系转化设置〉
     * @param transId transId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public AppModuleSetTransBO getModuleSetTransById(Long transId) {
        return remoteAppService.getModuleSetTransById(transId).getData();
    }

    /**
     * 功能描述:
     * 〈获取转换的字段信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public List<AppFormColumnBO> getTransColumns(Long moduleId) {
        //获取模块下字段信息
        return remoteAppService.fastColumnByModuleAndFormType(moduleId, FormTypeEnum.FORM_ADD.getValue()).getData();
    }

    /**
     * 功能描述:
     * 〈获取转换的字段信息〉
     * @param transMappingList transMappingList
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Map<String,String> getTransMapping(List<AppModuleSetTransMappingBO> transMappingList) {
        if(CollUtil.isEmpty(transMappingList)) {
            return new HashMap<>();
        }
        return transMappingList.stream().collect(Collectors.toMap(AppModuleSetTransMappingBO::getFromColumnMac,AppModuleSetTransMappingBO::getToColumnMac));
    }

    /**
     * 功能描述:
     * 〈获取转换的字段信息〉
     * @param moduleIndex moduleIndex
     * @param dataIds dataIds
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public List<Object> searchFromData(String moduleIndex, List<Long> dataIds) {
        if(ObjectUtil.isEmpty(moduleIndex) || CollUtil.isEmpty(dataIds)) {
            return CollUtil.newArrayList();
        }
        List<String> ids = dataIds.stream().map(StrUtil::toString).toList();
        //查询来源模块数据
        EsDocPGetDTO esDocPGetDTO = new EsDocPGetDTO();
        esDocPGetDTO.setIndexName(moduleIndex);
        CondDTO condDTO = new CondDTO();
        condDTO.setColumnMac(StrConst.ID);
        condDTO.setCondRef(LogicRefEnum.IN);
        condDTO.setSearchValues(ids);
        esDocPGetDTO.setCondDTO(CollUtil.newArrayList(condDTO));
        return this.searchDocument(esDocPGetDTO).getRecords();
    }

    /**
     * 功能描述:
     * 〈获取转换的字段信息〉
     * @param searchBO searchBO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public PageVO<Object> searchSearchData(AppModuleSetTransSearchBO searchBO) {
        if(ObjectUtil.isEmpty(searchBO.getModuleSearch()) || ObjectUtil.isEmpty(searchBO.getRuleMac())) {
            return new PageVO<>();
        }
        AppModuleBaseBO moduleSearch = searchBO.getModuleSearch();
        Object defaultValue = BaseEnum.getEnumByValue(DataTypeEnum.class, searchBO.getRoleDataType()).getDefaultValue(searchBO.getRoleDataValue());
        if(ObjectUtil.isNull(defaultValue)) {
            return new PageVO<>();
        }
        //查询来源模块数据
        EsDocPGetDTO esDocPGetDTO = new EsDocPGetDTO();
        List<CondDTO> condDTOS = CollUtil.newArrayList();
        CondDTO moduleDTO = new CondDTO();
        moduleDTO.setColumnMac(StrConst.MODULE_ID);
        moduleDTO.setCondRef(LogicRefEnum.EQ);
        moduleDTO.setSearchValues(CollUtil.newArrayList(moduleSearch.getId().toString()));
        condDTOS.add(moduleDTO);
        if(CompMacEnum.DATE.getDesc().equals(searchBO.getRuleMac())){
            LocalDateTime localDateTime = LocalDateTime.now().minusDays(Long.parseLong(searchBO.getRoleDataValue()));
            String dateTimeToStr = DateTimeUtil.localDateTimeToStr(localDateTime);
            CondDTO dateDTO = new CondDTO();
            dateDTO.setColumnMac(searchBO.getRuleMac());
            dateDTO.setCondRef(LogicRefEnum.LE);
            dateDTO.setSearchValues(CollUtil.newArrayList(dateTimeToStr));
            condDTOS.add(dateDTO);
        }
        esDocPGetDTO.setIndexName(moduleSearch.getModuleIndex());
        esDocPGetDTO.setCondDTO(condDTOS);
        return this.searchDocument(esDocPGetDTO);

    };

    /**
     * 功能描述:
     * 〈获取目标ID〉
     * @param records records
     * @param searchColumn searchColumn
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    public List<Long> getDataIds(List<Object> records,String searchColumn) {
        return records.stream().map(record -> {
            Map<String, Object> searchMap = BeanUtil.beanToMap(record);
            if (searchMap.containsKey(searchColumn)) {
                Object objectValue = searchMap.get(searchColumn);
                if (searchColumn.matches(EsConst.MAPPING_MATCH_JSON)) {
                    Map<String, Object> valueMap = BeanUtil.beanToMap(objectValue);
                    if (valueMap.containsKey(StrConst.DATA_ID)) {
                        Object object = valueMap.get(StrConst.DATA_ID);
                        if (ObjectUtil.isNotEmpty(object)) {
                            return Long.parseLong(object.toString());
                        }
                    }
                } else {
                    return Long.parseLong(objectValue.toString());
                }
            }
            return NumberConst.NUM_0.longValue();
        }).toList();
    }

    /**
     * 功能描述:
     * 〈转化数据〉
     * @param transBO transBO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean transData(DbTransBO transBO) {
        AppModuleSetTransBO setTransBO = transBO.getTransBO();
        if(ObjectUtil.isEmpty(setTransBO) || ObjectUtil.isEmpty(setTransBO.getModuleFrom()) || ObjectUtil.isEmpty(setTransBO.getModuleTo())){
            return Boolean.FALSE;
        }
        AppModuleBaseBO from = setTransBO.getModuleFrom();
        AppModuleBaseBO to = setTransBO.getModuleTo();
        //是否同一数据表内转移
        boolean isMove = from.getModuleSchema().equals(to.getModuleSchema());
        //获取来源目标字段映射配置
        Map<String, String> transMapping = this.getTransMapping(setTransBO.getMappingBOList());
        if(!isMove && CollUtil.isEmpty(transMapping)){
            throw DbExceptionEnum.DB_TRANS_COLUMN_MAPPING_INVALID.getBaseException();
        }
        //获取目标模块字段信息
        List<AppFormColumnBO> transColumns = this.getTransColumns(to.getId());
        if(CollUtil.isEmpty(transColumns)){
            throw DbExceptionEnum.DB_TRANS_COLUMN_INVALID.getBaseException();
        }
        //获取来源数据
        List<Object> fromDataList = this.searchFromData(from.getModuleIndex(), transBO.getDataIds());
        if(CollUtil.isEmpty(fromDataList)){
            throw DbExceptionEnum.DB_TRANS_DATA_INVALID.getBaseException();
        }
        //如果上级模块相同则视为相同模块转化，需要删除原有模块数据，如果上级模块不相同,则视为跨模块生成数据不需要删除原模块数据

        //递归保存数据
        fromDataList.forEach(fromData -> {
            //转换数据
            Map<String, Object> transDataMap = this.transMap(fromData, to, isMove, transMapping);
            //设置数据权限
            transDataMap = this.setDataScope(to,transBO.getUserRelBO(),transDataMap);
            //如果上级模块相同则视为相同模块转化，主数据ID不变批量修改moduleId更新时间等
            if(isMove){
//                this.dynamicDbUpdate(to.getModuleSchema(),to.getId(),CollUtil.newArrayList(dataId));
                //保存ES数据
                this.dynamicEsUpdate(to.getModuleIndex(), transDataMap);
            }else{
                //保存DB数据
                this.dynamicDbInsert(to.getModuleSchema(),transColumns, transDataMap);
                //保存ES数据
                this.dynamicEsInsert(to.getModuleIndex(), transDataMap);
            }
            //保存DATA数据
            this.dynamicDbDataInsert(to, transColumns, transDataMap);
        });
        //批量处理数据
        List<Long> dataIds = getDataIds(fromDataList);
        //修改模块
        if(isMove){
            this.dynamicDbUpdate(to.getModuleSchema(),to.getId(),dataIds);
        }else{
            //删除旧数据
            this.dynamicEsDelete(from.getModuleIndex(),dataIds);
        }
        //删除旧数据
        this.dynamicDBDataDelete(from.getModuleSchema().concat(StrConst.DATA_SUFFIX),dataIds);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈将字段key转换目标字段key〉
     * @param fromData transMap
     * @param isMove isMove
     * @param transMapping transMapping
     * @return 正常返回:{@link Map<String,Object>}
     * @author 蝉鸣
     */
    public Map<String, Object> transMap(Object fromData,AppModuleBaseBO to,Boolean isMove, Map<String,String> transMapping) {
        Map<String, Object> dataMap = new HashMap<>();
        if(ObjectUtil.isEmpty(fromData)) {
            return dataMap;
        }
        if(!isMove && CollUtil.isEmpty(transMapping)) {
            return dataMap;
        }
        Map<String, Object> fromMap = BeanUtil.beanToMap(fromData, Boolean.TRUE, Boolean.TRUE);
        //如果上级是相同模块直接赋值
        if(isMove){
            dataMap.putAll(fromMap);
        }
        //添加固定字段
        //字段转换
        transMapping.forEach((key, value) -> {
            if(fromMap.containsKey(StrUtil.toUnderlineCase(key))) {
                dataMap.put(StrUtil.toUnderlineCase(value), fromMap.get(StrUtil.toUnderlineCase(key)));
            }
        });
        //更新ID
        if(!isMove){
            dataMap.put(StrConst.ID, IdUtil.getSnowflake().nextId());
        }
        //更新ModuleID
        dataMap.put(StrConst.MODULE_ID, to.getId());
        dataMap.put(StrConst.PARENT_MODULE_ID, to.getParentId());
        //更新创建时间
        dataMap.put(StrConst.CREATE_TIME, DateTimeUtil.localDateTimeNow());
        //更新修改时间
        dataMap.put(StrConst.UPDATE_TIME, DateTimeUtil.localDateTimeNow());
        return dataMap;
    }

    /**
     * 功能描述:
     * 〈新增db固定表数据〉
     * @param tableName tableName
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    public void dynamicDbInsert(String tableName, List<AppFormColumnBO> columnBOS, Map<String, Object> dataMap) {
        if(ObjectUtil.isEmpty(dataMap) || CollUtil.isEmpty(dataMap)) {
            return;
        }
        //保存DB数据
        //获取当前表单信息
        TableInfo tableInfo = SqlUtil.getTableInfo(tableName);
        Object obj = BeanUtil.fillBeanWithMap(dataMap, tableInfo.newInstance(), Boolean.TRUE);;
        //转换Map形式
        Map<String, Object> dbMap = BeanUtil.beanToMap(obj, Boolean.TRUE, Boolean.TRUE);
        if(CollUtil.isNotEmpty(columnBOS)) {
            //生成序列号
            ColumnCompBO serialComp = SerialUtil.getSerialComp(BeanUtil.copyToList(columnBOS, ColumnCompBO.class));
            LocalDateTime reSetTime = SerialUtil.getReSetTime(serialComp);
            Map<String,Object> maxMap = this.dynamicDBMaxOne(tableName, reSetTime);
            if(CollUtil.isNotEmpty(maxMap) && maxMap.containsKey(StrConst.DATA_SERIAL)) {
                dataMap.put(StrConst.DATA_SERIAL,maxMap.get(StrConst.DATA_SERIAL));
            }
            SerialBO serialBO = SerialUtil.genDataSerial(serialComp,dataMap);
            dbMap.put(StrConst.DATA_MAC,serialBO.getDataMac());
            dbMap.put(StrConst.DATA_SERIAL,serialBO.getDataSerial());
        }

        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(tableName);
        this.getDbMapper().dynamicDbInsert(dbMap);
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();

        log.info("dynamicDbInsert success");
    }

    /**
     * 功能描述:
     * 〈新增DB  data表数据〉
     * @param to to
     * @param columnBOS columnBOS
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    public void dynamicDbDataInsert(AppModuleBaseBO to, List<AppFormColumnBO> columnBOS, Map<String, Object> dataMap) {
        if(ObjectUtil.isEmpty(to.getId()) || ObjectUtil.isEmpty(dataMap)){
            return;
        }
        //获取DATA表名
        String moduleSchema = to.getModuleSchema();
        String dataTableName = moduleSchema.concat(StrConst.DATA_SUFFIX);
        //获取ID
        //判断是否包含ID
        if(!dataMap.containsKey(StrConst.ID) || ObjectUtil.isEmpty(dataMap.get(StrConst.ID)) ) {
            return;
        }
        Object dataId = dataMap.get(StrConst.ID);
        //获取当前表单信息
        TableInfo tableInfo = SqlUtil.getTableInfo(dataTableName);
        //转换表单对象,过滤多余参数
        Object obj = tableInfo.newInstance();
        //过滤不需要赋值的字段信息
        Map<String, AppFormColumnBO> columnMap = columnBOS.stream()
                .filter(item-> !DataTypeEnum.INIT.getValue().equals(item.getDefaultDataType()))
                .collect(Collectors.toMap(AppFormColumnBO::getColumnMac, Function.identity(),(v1, v2)->v2));
        //转化存储信息
        dataMap.forEach((key, value) -> {
            if(columnMap.containsKey(StrUtil.toUnderlineCase(key))) {
                AppFormColumnBO formColumnBO = columnMap.get(StrUtil.toUnderlineCase(key));
                if(ObjectUtil.isNotEmpty(formColumnBO) && ObjectUtil.isNotEmpty(formColumnBO.getId())) {
                    //转换Data表Map形式
                    Map<String, Object> dbDataMap = BeanUtil.beanToMap(obj, Boolean.TRUE, Boolean.TRUE);
                    dbDataMap.put(StrConst.ID,IdUtil.getSnowflake().nextId());
                    dbDataMap.put(StrConst.MODULE_ID,to.getId());
                    dbDataMap.put(StrConst.PARENT_MODULE_ID,to.getParentId());
                    dbDataMap.put(StrConst.DATA_ID,dataId);
                    dbDataMap.put(StrConst.FORM_ID,formColumnBO.getFormId());
                    dbDataMap.put(StrConst.COLUMN_ID,formColumnBO.getId());
                    dbDataMap.put(StrConst.COLUMN_MAC,formColumnBO.getColumnMac());
                    dbDataMap.put(StrConst.COLUMN_NAME,formColumnBO.getColumnName());
                    dbDataMap.put(StrConst.DATA_TYPE,formColumnBO.getDefaultDataType());
                    dbDataMap.put(StrConst.DATA_VALUE,BaseEnum.getEnumByValue(DataTypeEnum.class, formColumnBO.getDefaultDataType(),DataTypeEnum.STRING).getDefaultValueStr(value));
                    //动态新增
                    dynamicDbInsert(dataTableName,null,dbDataMap);
                }
            }
        });
        log.info("dynamicDbDataInsert success");
    }

    /**
     * 功能描述:
     * 〈新增ES数据〉
     * @param moduleIndex moduleIndex
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    public void dynamicEsInsert(String moduleIndex, Map<String, Object> dataMap) {
        if(ObjectUtil.isEmpty(dataMap) || CollUtil.isEmpty(dataMap)) {
            return;
        }
        //判断是否包含ID
        if(!dataMap.containsKey(StrConst.ID) || ObjectUtil.isEmpty(dataMap.get(StrConst.ID)) ) {
            return;
        }
        Object id = dataMap.get(StrConst.ID);
        //新增数据
        EsDocPutBO docPutBO = new EsDocPutBO();
        docPutBO.setIndexName(moduleIndex);
        docPutBO.setDataIds(CollUtil.newArrayList(id));
        docPutBO.setDocMap(dataMap);
        esDocService.createDocument(docPutBO);
//        boolean existed = esDocService.existDocument(docPutBO);
//        if(existed) {
//            esDocService.updateDocument(docPutBO);
//        }else {
//            esDocService.createDocument(docPutBO);
//        }
        log.info("dynamicEsInsert success");
    }

    /**
     * 功能描述:
     * 〈修改db固定表数据〉
     * @param tableName tableName
     * @param moduleId moduleId
     * @author 蝉鸣
     */
    public void dynamicDbUpdate(String tableName,Long moduleId, List<Long> dataIds) {
        if(ObjectUtil.isEmpty(tableName) || ObjectUtil.isEmpty(moduleId) || CollUtil.isEmpty(dataIds)) {
            return;
        }
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(tableName);
        this.getDbMapper().dynamicDbUpdate(moduleId,dataIds);
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();

        log.info("dynamicDbUpdate success");
    }

    /**
     * 功能描述:
     * 〈修改ES表数据〉
     * @param moduleIndex moduleIndex
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    public void dynamicEsUpdate(String moduleIndex, Map<String, Object> dataMap) {
        if(ObjectUtil.isEmpty(moduleIndex) || CollUtil.isEmpty(dataMap)) {
            return;
        }
        //判断是否包含ID
        if(!dataMap.containsKey(StrConst.ID) || ObjectUtil.isEmpty(dataMap.get(StrConst.ID)) ) {
            return;
        }
        Object id = dataMap.get(StrConst.ID);
        //新增数据
        EsDocPutBO docPutBO = new EsDocPutBO();
        docPutBO.setIndexName(moduleIndex);
        docPutBO.setDataIds(CollUtil.newArrayList(id));
        docPutBO.setDocMap(dataMap);
        esDocService.updateDocument(docPutBO);
    }

    /**
     * 功能描述:
     * 〈新增ES数据〉
     * @param from from
     * @param fromDataList fromDataList
     * @author 蝉鸣
     */
    public void deleteFromData(AppModuleBaseBO from, List<Object> fromDataList) {
        //获取数据ID
        List<Long> dataIds = getDataIds(fromDataList);
        //删除DB
        this.dynamicDBDelete(from.getModuleSchema(),dataIds);
        //删除DATA
        this.dynamicDBDataDelete(from.getModuleSchema().concat(StrConst.DATA_SUFFIX),dataIds);
        //删除ES
        this.dynamicEsDelete(from.getModuleIndex(),dataIds);
    }

    /**
     * 功能描述:
     * 〈新增db数据〉
     * @param tableName tableName
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    public void dynamicDBDelete(String tableName, List<Long> dataIds) {
        if(ObjectUtil.isEmpty(tableName) || CollUtil.isEmpty(dataIds)) {
            return;
        }
        //删除DATA
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(tableName);
        this.getDbMapper().dynamicDbDelete(dataIds);
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
    }


    /**
     * 功能描述:
     * 〈新增DATA数据〉
     * @param tableName tableName
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    public void dynamicDBDataDelete(String tableName, List<Long> dataIds) {
        if(ObjectUtil.isEmpty(tableName) || CollUtil.isEmpty(dataIds)) {
            return;
        }
        //删除DATA
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(tableName);
        this.getDbMapper().dynamicDbDataDelete(dataIds);
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
    }

    /**
     * 功能描述:
     * 〈新增DATA数据〉
     * @param moduleIndex moduleIndex
     * @param dataIds dataIds
     * @author 蝉鸣
     */
    public void dynamicEsDelete(String moduleIndex, List<Long> dataIds) {
        if(ObjectUtil.isEmpty(moduleIndex) || CollUtil.isEmpty(dataIds)) {
            return;
        }
        EsDocPutBO docPutBO = new EsDocPutBO();
        docPutBO.setIndexName(moduleIndex);
        docPutBO.setDataIds(dataIds);
        esDocService.deleteDocument(docPutBO);
    }

    /**
     * 功能描述:
     * 〈获取数据ID〉
     * @param fromDataList fromDataList
     * @author 蝉鸣
     */
    public List<Long> getDataIds(List<Object> fromDataList) {
        if(CollUtil.isEmpty(fromDataList)) {
            return CollUtil.newArrayList();
        }
        return fromDataList.stream().map(this::getDataId).toList();
    }

    /**
     * 功能描述:
     * 〈获取数据ID〉
     * @param fromData fromData
     * @author 蝉鸣
     */
    public Long getDataId(Object fromData) {
        if(ObjectUtil.isEmpty(fromData)) {
            return NumberConst.NUM_0.longValue();
        }
        Map<String, Object> fromMap = BeanUtil.beanToMap(fromData, Boolean.TRUE, Boolean.TRUE);
        Object idObj = fromMap.get(StrConst.ID);
        if (ObjectUtil.isEmpty(idObj)) {
            return NumberConst.NUM_0.longValue();
        } else {
            return Long.parseLong(idObj.toString());
        }
    }

    /**
     * 功能描述:
     * 〈新增ES数据〉
     * @param tableName tableName
     * @param createTime createTime
     * @author 蝉鸣
     */
    public Map<String,Object> dynamicDBMaxOne(String tableName, LocalDateTime createTime) {
        if(ObjectUtil.isEmpty(tableName)) {
            return null;
        }
        //查询最新的一条数据
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(tableName);
        Map<String,Object> objMap = this.getDbMapper().dynamicDBMaxOne(createTime);
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
        return objMap;
    }

    /**
     * 功能描述:
     * 〈设置数据权限信息〉
     * @param memberId memberId
     * @author 蝉鸣
     */
    public OrgMemberRelBO getDataScopeByMemberId(Long memberId) {
        return remoteOrgMemberService.getOrgMemberUserDefaultRelByMemberId(memberId).getData();
    }

    /**
     * 功能描述:
     * 〈设置数据权限信息〉
     * @param to to
     * @param userRelBO userRelBO
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    public Map<String, Object> setDataScope(AppModuleBaseBO to, OrgMemberRelBO userRelBO, Map<String, Object> dataMap) {
        //如果是转入公海则不需要改变
        if(YesOrNoEnum.YES.getValue().equals(to.getOpenFlag())){
            return dataMap;
        }
        //如果成员memberId为空则视为领取,赋值当前登录人员信息
        if(ObjectUtil.isEmpty(userRelBO) || ObjectUtil.isEmpty(userRelBO.getMemberId())) {
            LoginUserBO loginUser = SecurityUtils.getLoginUser();
            Long accountId = loginUser.getAccountId();
            SysAccountBO accountBO = UserCacheUtil.getAccountInfoCache(accountId);
            //更新所属组织
            dataMap.put(MateFillEnum.SCOPE_ORG_ID.getDesc(), accountBO.getScopeOrgId());
            //更新所属人员
            dataMap.put(MateFillEnum.SCOPE_USER_ID.getDesc(), accountBO.getUserId());
            return dataMap;
        }
        //如果成员memberId不为空则视为分配,赋值分配人员信息
        if(ObjectUtil.isEmpty(userRelBO.getUserId()) || ObjectUtil.isEmpty(userRelBO.getLevelId())) {
            OrgMemberRelBO data = remoteOrgMemberService.getOrgMemberUserDefaultRelByMemberId(userRelBO.getMemberId()).getData();
            userRelBO.setUserId(data.getUserId());
            userRelBO.setLevelId(data.getLevelId());
        }
        //更新所属组织
        dataMap.put(MateFillEnum.SCOPE_ORG_ID.getDesc(), userRelBO.getLevelId());
        //更新所属人员
        dataMap.put(MateFillEnum.SCOPE_USER_ID.getDesc(), userRelBO.getUserId());
        return dataMap;
    }

}