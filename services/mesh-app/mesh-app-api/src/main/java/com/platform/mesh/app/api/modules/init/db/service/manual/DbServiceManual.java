package com.platform.mesh.app.api.modules.init.db.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.platform.mesh.app.api.modules.app.constant.AppConst;
import com.platform.mesh.app.api.modules.app.domain.bo.*;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.api.modules.app.enums.trans.PickTypeEnum;
import com.platform.mesh.app.api.modules.app.enums.trans.TransFlagEnum;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.api.modules.init.db.constant.DbConst;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbBusBO;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransBO;
import com.platform.mesh.app.api.modules.init.db.domain.bo.DbTransResBO;
import com.platform.mesh.app.api.modules.init.db.exception.DbExceptionEnum;
import com.platform.mesh.app.api.modules.init.db.mapper.DbMapper;
import com.platform.mesh.app.api.modules.serial.SerialUtil;
import com.platform.mesh.app.api.modules.serial.domain.bo.ColumnCompBO;
import com.platform.mesh.app.api.modules.serial.domain.bo.SerialBO;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.datascope.constant.DataScopeConst;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.util.EsUtil;
import com.platform.mesh.mybatis.plus.enums.MateFillEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;

import com.platform.mesh.mybatis.plus.handler.FormatTableNameHandler;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.search.utils.SearchUtil;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberTransBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.format.DateTimeUtil;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系Db数据
 * @author 蝉鸣
 */
public abstract class DbServiceManual {

    private final static Logger log = LoggerFactory.getLogger(DbServiceManual.class);

    private static final Pattern SQL_IDENTIFIER_PATTERN = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]{0,63}$");


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
    }

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
    public PageVO<AppModuleSetTransBO> getModuleSetTransAutoPage(ModulePageDTO pageDTO) {
        return remoteAppService.getModuleSetTransAutoPage(pageDTO).getData();
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
        return transMappingList.stream().collect(Collectors.toMap(AppModuleSetTransMappingBO::getToColumnMac,AppModuleSetTransMappingBO::getFromColumnMac,(v1,v2)->v2));
    }

    /**
     * 功能描述:
     * 〈获取转换的字段信息〉
     * @param pickBOList pickBOList
     * @author 蝉鸣
     */
    public void getTransPick(List<AppModuleSetTransPickBO> pickBOList) {
        if(CollUtil.isEmpty(pickBOList)) {
            return;
        }
        List<Long> memberIds = pickBOList.stream().map(AppModuleSetTransPickBO::getMemberId).toList();
        List<OrgMemberRelBO> relBOS = remoteOrgMemberService.getOrgMemberUserDefaultRelByIds(memberIds).getData();
        Map<Long, OrgMemberRelBO> userMap = relBOS.stream().collect(Collectors.toMap(OrgMemberRelBO::getUserId, Function.identity()));
        for (AppModuleSetTransPickBO pickBO : pickBOList) {
            if(userMap.containsKey(pickBO.getUserId())){
                pickBO.setLevelId(userMap.get(pickBO.getUserId()).getLevelId());
                pickBO.setLevelName(userMap.get(pickBO.getUserId()).getLevelName());
            }
        }
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
        esDocPGetDTO.setIgnoreScope(Boolean.TRUE);
        List<Object> objectList = this.searchDocument(esDocPGetDTO).getRecords();
        
        return objectList;
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
        Object defaultValue = BaseEnum.getEnumByValue(DataTypeEnum.class, searchBO.getRuleDataType()).getDefaultValue(searchBO.getRuleDataValue());
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
            LocalDateTime localDateTime = LocalDateTime.now().minusDays(Long.parseLong(searchBO.getRuleDataValue()));
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

    }

    /**
     * 功能描述:
     * 〈获取应用表名〉
     * @return 正常返回:{@link MPage<String>}
     * @author 蝉鸣
     */
    public MPage<Long> getTransDataIdsPage(AppModuleSetTransSearchBO pageDTO){
        AppModuleBaseBO moduleFrom = pageDTO.getModuleFrom();
        AppModuleBaseBO moduleSearch = pageDTO.getModuleSearch();
        String ruleMac = pageDTO.getRuleMac();
        String day = pageDTO.getRuleDataValue();
        long parseLong = Long.parseLong(day);
        LocalDateTime dateTime = LocalDateTime.now().minusDays(parseLong);
        MPage<Long> longMPage = MPageUtil.pageEntityToMPage(pageDTO, Long.class);
        //开启动态表名
        Map<String, String> tableNameMap = new HashMap<>();
        tableNameMap.put(DbConst.TABLE_1,moduleFrom.getModuleSchema());
        //如果没有第三方查询模块，则设置roleMac为空用来表示从当前模块查询数据，以update_time为维度
        if(ObjectUtil.isEmpty(moduleSearch)){
            ruleMac = null;
        }else{
            tableNameMap.put(DbConst.TABLE_2,moduleSearch.getModuleSchema().concat(StrConst.DATA_SUFFIX));
        }
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(tableNameMap);
        MPage<Long> dataIdsPage = this.getDbMapper().getTransDataIdsPage(longMPage, ruleMac, dateTime, moduleFrom.getId());
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
        return dataIdsPage;
    }

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
            Map<String, Object> searchMap = AppUtil.beanToMap(record);
            if (searchMap.containsKey(searchColumn)) {
                Object objectValue = searchMap.get(searchColumn);
                if (searchColumn.matches(EsConst.MAPPING_MATCH_JSON)) {
                    Map<String, Object> valueMap = AppUtil.beanToMap(objectValue);
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
    public DbTransResBO transData(DbTransBO transBO) {
        DbTransResBO transResBO = new DbTransResBO();
        Map<Long, Long> dataMap = new HashMap<>();
        AppModuleSetTransBO setTransBO = transBO.getTransBO();
        if(ObjectUtil.isEmpty(setTransBO) || ObjectUtil.isEmpty(setTransBO.getModuleFrom()) || ObjectUtil.isEmpty(setTransBO.getModuleTo())){
            return transResBO;
        }
        AppModuleBaseBO from = setTransBO.getModuleFrom();
        AppModuleBaseBO to = setTransBO.getModuleTo();
        transResBO.setFromModuleId(from.getId());
        transResBO.setToModuleId(to.getId());
        //是否同一数据表内转移
        boolean isMove = from.getModuleSchema().equals(to.getModuleSchema());
        //获取来源目标字段映射配置
        Map<String, String> transMapping = this.getTransMapping(setTransBO.getMappingBOList());
        if(!isMove && CollUtil.isEmpty(transMapping)){
            throw DbExceptionEnum.DB_TRANS_COLUMN_MAPPING_INVALID.getBaseException();
        }
        //获取转移分配规则
        this.getTransPick(setTransBO.getPickBOList());
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
        //是否需要删除来源数据
        Boolean delFrom = Boolean.FALSE;
        //如果上级模块相同则视为相同模块转化，需要删除原有模块数据，如果上级模块不相同,则视为跨模块生成数据不需要删除原模块数据
        //递归保存数据
        fromDataList.forEach(fromData -> {
            //转换数据
            Map<String, Object> transDataMap = this.transMap(fromData, to, isMove, transMapping);
            //设置数据权限
            transDataMap = this.setDataScope(to,setTransBO.getPickBOList(),transDataMap);
            //不删除则增加转移状态数据
            if(!delFrom){
                JSONArray transJson = AppUtil.getJsonData(TransFlagEnum.DONE.getValue().longValue(), TransFlagEnum.DONE.getDesc());
                transDataMap.put(StrConst.TRANS_JSON, transJson);
            }
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
            //获取数据映射
            Long formDataId = getDataId(fromData);
            Long toDataId = getDataId(transDataMap);
            dataMap.put(formDataId,toDataId);
        });
        //批量处理数据
        List<Long> dataIds = getDataIds(fromDataList);
        //修改模块
        if(isMove){
            this.dynamicDbUpdate(to.getModuleSchema(),to.getId(),dataIds);
            //删除旧数据
            this.dynamicDBDataDelete(from.getModuleSchema().concat(StrConst.DATA_SUFFIX),dataIds);
        }else{
            if(delFrom){
                this.dynamicDBDelete(from.getModuleSchema(),dataIds);
                //删除来源数据
                this.dynamicEsDelete(from.getModuleIndex(),dataIds);
                //删除旧数据
                this.dynamicDBDataDelete(from.getModuleSchema().concat(StrConst.DATA_SUFFIX),dataIds);
            }
        }
        //删除旧数据
        this.dynamicDBDataDelete(from.getModuleSchema().concat(StrConst.DATA_SUFFIX),dataIds);
        
        transResBO.setDataMap(dataMap);
        return transResBO;
    }

    /**
     * 功能描述:
     * 〈将字段key转换目标字段key〉
     * @param fromData transMap
     * @param isMove isMove
     * @param transMapping transMapping
     * @return 正常返回:{@link Map}
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
        Map<String, Object> fromMap = AppUtil.beanToMap(fromData);
        //如果上级是相同模块直接赋值
        if(isMove){
            dataMap.putAll(fromMap);
        }
        //添加固定字段
        transMapping.put(MateFillEnum.SCOPE_USER_ID.getDesc(),MateFillEnum.SCOPE_USER_ID.getDesc());
        transMapping.put(MateFillEnum.SCOPE_ORG_ID.getDesc(),MateFillEnum.SCOPE_ORG_ID.getDesc());
        //字段转换
        transMapping.forEach((key, value) -> {
            if(fromMap.containsKey(StrUtil.toUnderlineCase(value))) {
                dataMap.put(StrUtil.toUnderlineCase(key), fromMap.get(StrUtil.toUnderlineCase(value)));
            }
        });
        //更新ID
        if(!isMove){
            dataMap.put(StrConst.ID, IdUtil.getSnowflake().nextId());
        }
        //来源数据ID
        dataMap.put(StrConst.FROM_DATA_ID, getDataId(fromData));
        //更新ModuleID
        dataMap.put(StrConst.MODULE_ID, to.getId());
        dataMap.put(StrConst.PARENT_MODULE_ID, to.getParentId());
        //更新创建时间
        dataMap.put(StrConst.CREATE_TIME, DateTimeUtil.localDateTimeNow());
        //更新修改时间
        dataMap.put(StrConst.UPDATE_TIME, DateTimeUtil.localDateTimeNow());
        dataMap.put(StrConst.DEL_FLAG, YesOrNoEnum.YES.getValue());
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
        if(CollUtil.isNotEmpty(columnBOS)) {
            //生成序列号
            ColumnCompBO serialComp = SerialUtil.getSerialComp(BeanUtil.copyToList(columnBOS, ColumnCompBO.class));
            LocalDateTime reSetTime = SerialUtil.getReSetTime(serialComp);
            Map<String,Object> maxMap = this.dynamicDBMaxOne(tableName, reSetTime);
            if(CollUtil.isNotEmpty(maxMap) && maxMap.containsKey(StrConst.DATA_SERIAL)) {
                dataMap.put(StrConst.DATA_SERIAL,maxMap.get(StrConst.DATA_SERIAL));
            }
            SerialBO serialBO = SerialUtil.genDataSerial(serialComp,dataMap);
            dataMap.put(StrConst.DATA_MAC, serialBO.getDataMac());
            dataMap.put(StrConst.DATA_SERIAL, serialBO.getDataSerial());
        }
        //校验表信息
        validateTableName(tableName);
        //获取当前表单信息
        TableInfo tableInfo = SqlUtil.getTableInfo(tableName);
        Object obj = BeanUtil.fillBeanWithMap(dataMap, tableInfo.newInstance(), Boolean.TRUE);;
        //转换Map形式
        Map<String, Object> dbMap = AppUtil.beanToMap(obj);
        validateColumnKeys(dbMap);
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
                    Map<String, Object> dbDataMap = AppUtil.beanToMap(obj);
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
        Map<String, Object> fromMap = AppUtil.beanToMap(fromData);
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
     * @param pickBOS pickBOS
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    public Map<String, Object> setDataScope(AppModuleBaseBO to,List<AppModuleSetTransPickBO> pickBOS, Map<String, Object> dataMap) {
        //如果是转入公海则不需要改变
        if(YesOrNoEnum.YES.getValue().equals(to.getOpenFlag())){
            return dataMap;
        }
        //获取人员
        OrgMemberRelBO relBO = this.getScopeRel(pickBOS);
        if(ObjectUtil.isEmpty(relBO)){
            return dataMap;
        }
        //更新所属组织
        dataMap.put(MateFillEnum.SCOPE_ORG_ID.getDesc(), relBO.getLevelId());
        //更新所属人员
        dataMap.put(MateFillEnum.SCOPE_USER_ID.getDesc(), relBO.getUserId());
        //更新部门信息
        JSONArray userJson = AppUtil.getJsonData(relBO.getUserId(), relBO.getMemberName());
        dataMap.put(StrConst.SCOPE_USER,userJson);
        //更新人员信息
        JSONArray orgJson = AppUtil.getJsonData(relBO.getLevelId(), relBO.getLevelName());
        dataMap.put(StrConst.SCOPE_ORG,orgJson);
        return dataMap;
    }

    /**
     * 功能描述:
     * 〈获取下一个分配人员信息〉
     * @param pickBOS dataMap
     * @author 蝉鸣
     */
    public OrgMemberRelBO getScopeRel(List<AppModuleSetTransPickBO> pickBOS) {
        OrgMemberRelBO relBO = new OrgMemberRelBO();
        //当前登录人
        if (CollUtil.isEmpty(pickBOS)){
            LoginUserBO loginUser = SecurityUtils.getLoginUser();
            if(ObjectUtil.isNull(loginUser)) {
                //如果当前登录人员为空则取原始数据信息
                return null;
            }else{
                return UserCacheUtil.getMemberByUserId(UserCacheUtil.getUserId());
            }
        }
        AppModuleSetTransPickBO first = CollUtil.getFirst(pickBOS);
        Long transId = first.getTransId();
        Integer pickType = first.getPickType();
        UserPickBO pickBO = new UserPickBO();
        Object object = RedissonUtil.getCacheObject(AppConst.PICK_APP_DATA_TRANS.concat(transId.toString()));
        if(ObjectUtil.isNotEmpty(object)){
            pickBO = JSONUtil.toBean(JSONUtil.toJsonStr(object), UserPickBO.class);
        }
        PickTypeEnum enumByValue = BaseEnum.getEnumByValue(PickTypeEnum.class, pickType);
        switch (enumByValue){
            case LOOP -> {
                return getOrgMemberRelBO(transId,pickBOS,pickBO);
            }
            case RATIO -> {
                //重置分配值
                Integer pickNum = pickBO.getPickNum();
                //获取当前分配总数
                long value = RedissonUtil.getAtomicValue(AppConst.PICK_APP_DATA_COUNT.concat(transId.toString()));
                //四舍五入
                int pickRate = BigDecimal.valueOf(pickNum)
                        .multiply(BigDecimal.valueOf(NumberConst.NUM_100))
                        .divide(BigDecimal.valueOf(value), NumberConst.NUM_0, RoundingMode.HALF_UP)
                        .intValue();
                pickBO.setPickNum(pickRate);
                return getOrgMemberRelBO(transId,pickBOS,pickBO);
            }
            default -> {
                AppModuleSetTransPickBO any = RandomUtil.randomEle(pickBOS);
                BeanUtil.copyProperties(any,relBO);
            }
        }
        return relBO;
    }

    /**
     * 功能描述:
     * 〈获取分配人员〉
     * @param pickBOS dataMap
     * @author 蝉鸣
     */
    public OrgMemberRelBO getOrgMemberRelBO(Long transId,List<AppModuleSetTransPickBO> pickBOS,UserPickBO pickBO) {
        Map<Long, AppModuleSetTransPickBO> userMap = pickBOS.stream().collect(Collectors.toMap(AppModuleSetTransPickBO::getUserId, Function.identity()));
        //查询当前人员是否满足
        RLock rLock = RedissonUtil.getLock(AppConst.PICK_APP_DATA_LOCK.concat(transId.toString()));
        try {
            //加锁
            rLock.lock();
            //没有人
            if(ObjectUtil.isEmpty(pickBO) || ObjectUtil.isEmpty(pickBO.getUserId())){
                OrgMemberRelBO relBO = BeanUtil.copyProperties(CollUtil.getFirst(pickBOS), OrgMemberRelBO.class);
                //设置当前转化缓存
                UserPickBO userPickBO = BeanUtil.copyProperties(relBO, UserPickBO.class);
                userPickBO.setPickNum(NumberConst.NUM_1);
                String jsonStr = JSONUtil.toJsonStr(userPickBO);
                RedissonUtil.setCacheObject(AppConst.PICK_APP_DATA_TRANS.concat(transId.toString()),jsonStr);
                return relBO;
            }else{
                AppModuleSetTransPickBO transPickBO = userMap.get(pickBO.getUserId());
                OrgMemberRelBO relBO;
                //校验当前分配数量是否足够
                Integer pickValue = transPickBO.getPickValue();
                if(ObjectUtil.isEmpty(pickValue)){
                    pickValue = NumberConst.NUM_1;
                }
                if(pickBO.getPickNum() < pickValue){
                    relBO = BeanUtil.copyProperties(transPickBO, OrgMemberRelBO.class);;
                }else {
                    //已经足够,下一个人
                    // 查找当前 userId 的索引
                    AtomicInteger index = new AtomicInteger(-1);
                    pickBOS.stream()
                            .filter(bo -> bo.getUserId().equals(pickBO.getUserId()))
                            .findFirst()
                            .ifPresent(bo -> index.set(pickBOS.indexOf(bo)));
                    if (index.get() == -1) {
                        return null; // 没找到
                    }
                    // 返回下一个，如果是最后一个返回第一个
                    int nextIndex = (index.get() + 1) % pickBOS.size();
                    relBO = BeanUtil.copyProperties(pickBOS.get(nextIndex),OrgMemberRelBO.class);
                }
                //设置当前转化缓存
                pickBO.setPickNum(pickBO.getPickNum()+NumberConst.NUM_1);
                String jsonStr = JSONUtil.toJsonStr(pickBO);
                RedissonUtil.setCacheObject(AppConst.PICK_APP_DATA_TRANS.concat(transId.toString()),jsonStr);
                return relBO;
            }
        }catch (Exception ignored){
            log.error("分配人员权限数据有误");
        }finally {
            //释放锁
            rLock.unlock();
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈获取原始数据ID〉
     * @param dataMap dataMap
     * @param fieldName fieldName
     * @author 蝉鸣
     */
    public Long getOriginId(Map<String, Object> dataMap, String fieldName){
        return Optional.ofNullable(dataMap.get(fieldName))
                .filter(ObjectUtil::isNotEmpty)
                .map(JSONUtil::parseArray)
                .filter(jsonArray -> !jsonArray.isEmpty())
                .flatMap(jsonArray -> jsonArray.stream().findFirst())
                .filter(ObjectUtil::isNotEmpty)
                .map(JSONUtil::parseObj)
                .filter(jsonObj -> jsonObj.containsKey(StrConst.ID))
                .map(jsonObj -> MapUtil.getLong(jsonObj, StrConst.ID))
                .orElse(NumberConst.NUM_0.longValue());
    }

    /**
     * 功能描述:
     * 〈修改组织权限数据〉
     * @param memberBO memberBO
     * @author 蝉鸣
     */
    public void syncUserName(OrgMemberBO memberBO) {

        List<DbBusBO> busList = this.getBusBOList();
        if(CollUtil.isEmpty(busList)){
            return;
        }
        FutureHandleUtil.runWithResult(busList,busBO->{
            //处理数据
            this.handleSyncUserBusBO(busBO,memberBO);
            return Boolean.TRUE;
        });
    }

    /**
     * 功能描述:
     * 〈修改组织权限数据〉
     * @param levelBO levelBO
     * @author 蝉鸣
     */
    public void syncOrgName(OrgLevelBO levelBO) {

        List<DbBusBO> busList = this.getBusBOList();
        if(CollUtil.isEmpty(busList)){
            return;
        }
        FutureHandleUtil.runWithResult(busList,busBO->{
            //处理数据
            this.handleSyncOrgBusBO(busBO,levelBO);
            return Boolean.TRUE;
        });
    }

    /**
     * 功能描述:
     * 〈修改组织权限数据〉
     * @param transBO transBO
     * @author 蝉鸣
     */
    public void transOrgData(OrgMemberTransBO transBO) {

        List<DbBusBO> busList = this.getBusBOList();
        if(CollUtil.isEmpty(busList)){
            return;
        }
        FutureHandleUtil.runWithResult(busList,busBO->{
            //处理数据
            this.handleTransOrgBusBO(busBO,transBO);
            return Boolean.TRUE;
        });
    }

    /**
     * 功能描述:
     * 〈组装数据〉
     * @author 蝉鸣
     */
    public List<DbBusBO> getBusBOList(){
        //获取所有表单
        List<String> tableNames = this.selectAppTables();
        if(CollUtil.isEmpty(tableNames)){
            return CollUtil.newArrayList();
        }
        //获取所有索引
        List<AppModuleBaseBO> moduleInfos = remoteAppService.getModuleBaseInfoBySchema(tableNames).getData();
        if(CollUtil.isEmpty(moduleInfos)){
            return CollUtil.newArrayList();
        }
        return moduleInfos.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                AppModuleBaseBO::getModuleSchema,
                                Collectors.mapping(AppModuleBaseBO::getModuleIndex, Collectors.toList())
                        ),
                        map -> map.entrySet().stream()
                                .map(entry -> {
                                    DbBusBO busBO = new DbBusBO();
                                    busBO.setTableName(entry.getKey());
                                    busBO.setIndexList(entry.getValue());
                                    return busBO;
                                })
                                .collect(Collectors.toList())
                ));
    }

    /**
     * 功能描述:
     * 〈组装数据〉
     * @author 蝉鸣
     */
    public void handleSyncUserBusBO(DbBusBO busBO, OrgMemberBO memberBO){
        if(ObjectUtil.isEmpty(busBO) || ObjectUtil.isEmpty(memberBO) ){
            return;
        }
        //修改数据
        Integer pageNum = NumberConst.NUM_1;
        try{
            while(true){
                List<Long> ids = getIds(busBO.getTableName(),pageNum,memberBO.getUserId());
                if(CollUtil.isEmpty(ids)){
                    break;
                }
                //更新DbData
                //组装数据
                JSONArray userJson = AppUtil.getJsonData(memberBO.getUserId(), memberBO.getMemberName());
                updateDbData(busBO.getTableName(),ids,null,null,JSONUtil.toJsonStr(userJson),null);
                //更新Es
                updateEsData(busBO.getIndexList(),ids,null,null,userJson,null);
                pageNum ++;
            }
        }catch (Exception e){
            log.error(DbExceptionEnum.DB_TRANS_DATA_ERROR.getDesc() + SymbolConst.COLON +e.getMessage());
        }

    }

    /**
     * 功能描述:
     * 〈组装数据〉
     * @author 蝉鸣
     */
    public void handleSyncOrgBusBO(DbBusBO busBO, OrgLevelBO levelBO){
        if(ObjectUtil.isEmpty(busBO) || ObjectUtil.isEmpty(levelBO) ){
            return;
        }
        //修改数据
        Integer pageNum = NumberConst.NUM_1;
        try{
            while(true){
                List<Long> ids = getIds(busBO.getTableName(),pageNum,levelBO.getId());
                if(CollUtil.isEmpty(ids)){
                    break;
                }
                //更新DbData
                //组装数据
                JSONArray orgJson = AppUtil.getJsonData(levelBO.getId(), levelBO.getLevelName());
                updateDbData(busBO.getTableName(),ids,null,null,null,JSONUtil.toJsonStr(orgJson));
                //更新Es
                updateEsData(busBO.getIndexList(),ids,null,null,null,orgJson);
                pageNum ++;
            }
        }catch (Exception e){
            log.error(DbExceptionEnum.DB_TRANS_DATA_ERROR.getDesc() + SymbolConst.COLON +e.getMessage());
        }

    }

    /**
     * 功能描述:
     * 〈组装数据〉
     * @author 蝉鸣
     */
    public void handleTransOrgBusBO(DbBusBO busBO,OrgMemberTransBO transBO){
        if(ObjectUtil.isEmpty(busBO) || ObjectUtil.isEmpty(transBO) ){
            return;
        }
        //修改数据
        Integer pageNum = NumberConst.NUM_1;
        try{
            while(true){
                List<Long> ids = getIds (busBO.getTableName(),pageNum,transBO.getSourceUserId());
                if(CollUtil.isEmpty(ids)){
                    break;
                }
                //更新Db
                updateDb(busBO,transBO,ids);
                //更新DbData
                //组装数据
                JSONArray userJson = AppUtil.getJsonData(transBO.getTargetUserId(), transBO.getTargetMemberName());
                JSONArray orgJson = AppUtil.getJsonData(transBO.getTargetLevelId(), transBO.getTargetLevelName());
                updateDbData(busBO.getTableName(),ids,transBO.getTargetUserId(),transBO.getTargetLevelId(),userJson,orgJson);
                //更新Es
                updateEsData(busBO.getIndexList(),ids,transBO.getTargetUserId(),transBO.getTargetLevelId(),userJson,orgJson);
                pageNum ++;
            }
        }catch (Exception e){
            log.error(DbExceptionEnum.DB_TRANS_DATA_ERROR.getDesc() + SymbolConst.COLON +e.getMessage());
        }

    }

    /**
     * 功能描述:
     * 〈获取需要更新的ID〉
     * @param tableName tableName
     * @param userId userId
     * @author 蝉鸣
     */
    public List<Long> getIds(String tableName,Integer pageNum,Long userId){
        //修改数据
        MPage<Long> mPage = new MPage<>();
        mPage.setCurrent(pageNum);
        mPage.setSize(NumberConst.NUM_100);
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(tableName);
        MPage<Long> ids = this.getDbMapper().getIds(mPage,userId);
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
        return ids.getRecords();
    }

    /**
     * 功能描述:
     * 〈更新db〉
     * @param busBO busBO
     * @param transBO transBO
     * @author 蝉鸣
     */
    public void updateDb(DbBusBO busBO,OrgMemberTransBO transBO,List<Long> ids){
        if(ObjectUtil.isEmpty(transBO.getTargetUserId()) && ObjectUtil.isEmpty(transBO.getTargetLevelId())){
            return;
        }
        //更新Db
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(busBO.getTableName());
        try{
            this.getDbMapper().dynamicDbUpdateOrg(transBO.getTargetUserId(),transBO.getTargetLevelId(),ids);
        }finally {
            //取消动态表名
            FormatTableNameHandler.removeTableName();
            FormatTableNameHandler.unEnableTableName();
        }
    }

    /**
     * 功能描述:
     * 〈更新db_data〉
     * @param tableName tableName
     * @param ids ids
     * @param userId userId
     * @param orgId orgId
     * @param userJson userJson
     * @param orgJson orgJson
     * @author 蝉鸣
     */
    public void updateDbData(String tableName,List<Long> ids,Long userId,Long orgId,Object userJson,Object orgJson){
        if(ObjectUtil.isEmpty(userId) && ObjectUtil.isEmpty(orgId) && ObjectUtil.isEmpty(userJson) && ObjectUtil.isEmpty(orgJson)){
            return;
        }
        //更新DbData
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(tableName.concat(StrConst.DATA_SUFFIX));
        try{
            this.getDbMapper().dynamicDbDataUpdateOrg(ids, userId, orgId,userJson, orgJson);

        }finally {
            //取消动态表名
            FormatTableNameHandler.removeTableName();
            FormatTableNameHandler.unEnableTableName();
        }
    }

    /**
     * 功能描述:
     * 〈更新db_data〉
     * @param indexList indexList
     * @param ids ids
     * @param userId userId
     * @param orgId orgId
     * @param userJson userJson
     * @param orgJson orgJson
     * @author 蝉鸣
     */
    public void updateEsData(List<String> indexList,List<Long> ids,Long userId,Long orgId,Object userJson,Object orgJson){
        if(ObjectUtil.isEmpty(userId) && ObjectUtil.isEmpty(orgId) && ObjectUtil.isEmpty(userJson) && ObjectUtil.isEmpty(orgJson)){
            return;
        }
        //组装数据
        Map<String, Object> map = new HashMap<>();
        if(ObjectUtil.isNotEmpty(userId)){
            map.put(DataScopeConst.DEFAULT_SCOPE_USER_ID,userId);
        }
        if(ObjectUtil.isNotEmpty(userJson)){
            map.put(StrConst.SCOPE_USER,userJson);
        }
        if(ObjectUtil.isNotEmpty(orgId)){
            map.put(DataScopeConst.DEFAULT_SCOPE_ORG_ID,orgId);
        }
        if(ObjectUtil.isNotEmpty(orgJson)){
            map.put(StrConst.SCOPE_ORG,orgJson);
        }
        for (String moduleIndex : indexList) {
            //新增数据
            EsDocPutBO docPutBO = new EsDocPutBO();
            docPutBO.setIndexName(moduleIndex);
            docPutBO.setDataIds(ids);
            docPutBO.setDocMap(map);
            esDocService.updateDocument(docPutBO);
        }
    }

    /**
     * 功能描述:
     * 〈校验表名〉
     * @param tableName tableName
     * @author 蝉鸣
     */
    private void validateTableName(String tableName) {
        if (CharSequenceUtil.isBlank(tableName) || !SQL_IDENTIFIER_PATTERN.matcher(tableName).matches()) {
            throw DbExceptionEnum.DB_TRANS_DATA_ERROR.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈校验字段名〉
     * @param dataMap dataMap
     * @author 蝉鸣
     */
    private void validateColumnKeys(Map<String, Object> dataMap) {
        if (ObjectUtil.isEmpty(dataMap)) {
            throw DbExceptionEnum.DB_TRANS_DATA_ERROR.getBaseException();
        }
        dataMap.keySet().forEach(this::validateTableName);
    }

}
