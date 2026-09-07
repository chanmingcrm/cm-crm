package com.platform.mesh.app.api.modules.init.es.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.bo.InitDataBO;
import com.platform.mesh.app.api.modules.app.domain.dto.InitEsDTO;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.api.modules.init.es.domian.bo.InitEsModuleBO;
import com.platform.mesh.app.api.modules.init.es.mapper.AppInitEsMapper;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SearchColumnConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.datascope.constant.DataScopeConst;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.service.IEsIndexService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.handler.DataScopeHandler;
import com.platform.mesh.mybatis.plus.handler.FormatTableNameHandler;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.api.modules.team.domain.bo.TeamMemberRelBO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseGetDTO;
import com.platform.mesh.upms.api.modules.team.feign.RemoteTeamService;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 初始化Es
 * @author 蝉鸣
 */
public abstract class AppInitEsServiceManual {

    private final static Logger log = LoggerFactory.getLogger(AppInitEsServiceManual.class);

    @Autowired
    private RemoteAppService remoteAppService;

    @Autowired
    private RemoteTeamService remoteTeamService;

    @Autowired
    private IEsDocService esDocService;

    @Autowired
    private IEsIndexService esIndexService;

    /**
     * 功能描述:
     * 〈实现Mapper〉
     * @return 正常返回:{@link AppInitEsMapper}
     * @author 蝉鸣
     */
    public abstract AppInitEsMapper getInitEsMapper();

    /**
     * 功能描述:
     * 〈客户关系缓存初始化索引和字段〉
     * @param tableSchemas tableSchemas
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public List<AppModuleBaseBO> initEsIndex(List<String> tableSchemas) {
        //初始化索引和字段信息
        List<Result<List<AppModuleBaseBO>>> results = FutureHandleUtil.runWithResult(tableSchemas, remoteAppService::initModuleBaseEs);
        if(CollUtil.isEmpty(results)){
            return CollUtil.newArrayList();
        }
        return results.stream().map(Result::getData).flatMap(List::stream).toList();
    }

    /**
     * 功能描述:
     * 〈客户关系缓存初始化数据〉
     * @param initEsModuleBO initDataBO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean initEsData(InitEsModuleBO initEsModuleBO) {
        if(ObjectUtil.isEmpty(initEsModuleBO)){
            return false;
        }
        AppModuleBaseBO appModuleBaseBO = initEsModuleBO.getModuleBaseBO();
        Boolean ignoreScope = initEsModuleBO.getIgnoreScope();
        if(ObjectUtil.isEmpty(appModuleBaseBO) || ObjectUtil.isEmpty(appModuleBaseBO.getModuleSchema())) {
            return false;
        }
        //初始化实体对象
        //获取分页总数量
        Long modelPages = this.initPageCount(ignoreScope,appModuleBaseBO.getModuleSchema());
        //获取分页封装对象
        List<InitDataBO> modelBOS = this.initAllPage(ignoreScope,modelPages, appModuleBaseBO.getModuleSchema(), appModuleBaseBO.getModuleIndex());
        //执行初始化数据
        FutureHandleUtil.runWithResult(modelBOS,this::initEsModel);
        return true;
    }

    /**
     * 功能描述:
     * 〈获取当前数据总共多少页〉
     * @param dataScope dataScope
     * @param moduleSchema moduleSchema
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Long initPageCount(Boolean dataScope, String moduleSchema) {
        //关闭数据权限隔离
        DataScopeHandler.setEnableDataScope(!dataScope);
        //使用mybatis-plus 动态表名插件,根据module配置表名信息，动态切换表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(moduleSchema);
        MPage<Map<String, Object>> mapMPage = this.getInitEsMapper().selectPageMaps(new MPage<>().setSize(NumberConst.NUM_100));
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
        //取消数据权限隔离
        DataScopeHandler.unEnableDataScope();
        return mapMPage.getPages();
    }


    /**
     * 功能描述:
     * 〈获取所有页封装对象〉
     * @param pages pages
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public List<InitDataBO> initAllPage(Boolean ignoreScope, Long pages, String moduleSchema, String moduleIndex) {
        List<InitDataBO> dataBOS = CollUtil.newArrayList();
        for (int i = 0; i < pages; i++) {
            InitDataBO initDataBO = new InitDataBO();
            initDataBO.setCurrent(i+NumberConst.NUM_1.longValue());
            initDataBO.setModuleSchema(moduleSchema);
            initDataBO.setModuleIndex(moduleIndex);
            initDataBO.setIgnoreScope(ignoreScope);
            dataBOS.add(initDataBO);
        }
        return dataBOS;
    }

    /**
     * 功能描述:
     * 〈客户关系缓存初始化数据〉
     * @param initDataBO initDataBO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean initEsModel(InitDataBO initDataBO) {
        if(ObjectUtil.isNull(initDataBO)) {
            return false;
        }
        if(ObjectUtil.isEmpty(initDataBO.getModuleSchema()) || ObjectUtil.isEmpty(initDataBO.getModuleIndex())) {
            return false;
        }
        //关闭数据权限隔离
        DataScopeHandler.setEnableDataScope(!initDataBO.getIgnoreScope());
        //使用mybatis-plus 动态表名插件,根据module配置表名信息，动态切换表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(initDataBO.getModuleSchema());
        MPage<Map<String, Object>> mapMPage = this.getInitEsMapper().selectPageMaps(new MPage<>().setCurrent(initDataBO.getCurrent()).setSize(NumberConst.NUM_100));
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
        //取消数据权限隔离
        DataScopeHandler.unEnableDataScope();
        List<Long> ids = mapMPage.getRecords().stream().map(item -> {
            Object dataId = item.get(StrConst.ID);
            return Long.parseLong(dataId.toString());
        }).toList();
        //获取Data数据
        Map<Long, Map<String, Object>> dataMap = initEsModelData(initDataBO,ids);

        List<EsDocPutBO> list = mapMPage.getRecords().stream().map(item -> {
            Long dataId = Long.parseLong(item.get(StrConst.ID).toString());
            Map<String, Object> docMap = new HashMap<>();
            if(dataMap.containsKey(dataId)){
                docMap.putAll(dataMap.get(dataId));
            }
            docMap.putAll(item);
            EsDocPutBO esDocPutBO = new EsDocPutBO();
            esDocPutBO.setIndexName(initDataBO.getModuleIndex());
            esDocPutBO.setDataIds(CollUtil.newArrayList(dataId));
            //填充额外数据
            fillEsScopeData(docMap);
            //填充团队成员数据
            fillEsTeamData(docMap);
            //填充审批流程数据
            fillEsBpmData(docMap);
            //其他处理
            fillOtherData(docMap);
            //保存数据
            esDocPutBO.setDocMap(docMap);
            return esDocPutBO;
        }).toList();
        FutureHandleUtil.runNoResult(list,esDocService::createBatchDocument);
        return true;
    }

    /**
     * 功能描述:
     * 〈客户关系缓存初始化数据〉
     * @param initDataBO initDataBO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Map<Long, Map<String, Object>> initEsModelData(InitDataBO initDataBO,List<Long> dataIds) {
        if(ObjectUtil.isNull(initDataBO) || CollUtil.isEmpty(dataIds)){
            return new HashMap<>();
        }
        if(ObjectUtil.isEmpty(initDataBO.getModuleSchema()) || ObjectUtil.isEmpty(initDataBO.getModuleIndex())) {
            return new HashMap<>();
        }
        //关闭数据权限隔离
        DataScopeHandler.setEnableDataScope(!initDataBO.getIgnoreScope());
        //使用mybatis-plus 动态表名插件,根据module配置表名信息，动态切换表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(initDataBO.getModuleSchema().concat(StrConst.DATA_SUFFIX));
        List<Map<String, Object>> mapList = this.getInitEsMapper().selectModelDataByDataIds(dataIds);
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
        //取消数据权限隔离
        DataScopeHandler.unEnableDataScope();
        //将data数据根据主数据dataId分类
        return mapList.stream()
                .filter(map -> ObjectUtil.isNotEmpty(map.get(StrConst.DATA_VALUE)))
                .filter(map -> ObjectUtil.isNotEmpty(map.get(StrConst.DATA_ID)))
                .collect(Collectors.groupingBy(
                        // 依据dataId分组
                        map -> Long.parseLong(map.get(StrConst.DATA_ID).toString()),
                        Collectors.collectingAndThen(
                                // 先将分组结果收集为List
                                Collectors.toList(),
                                groupList -> {
                                    // 创建一个新的Map作为合并后的结果
                                    Map<String, Object> mergedMap = new HashMap<>();
                                    // 对每个分组的List进行处理
                                    groupList.forEach(map->{
                                        if(CollUtil.isNotEmpty(map) && map.containsKey(SearchColumnConst.DATA_COLUMN_MAC) && ObjectUtil.isNotEmpty(map.get(SearchColumnConst.DATA_COLUMN_MAC))) {
                                            Object dataValue = map.get(SearchColumnConst.DATA_COLUMN_VALUE);
                                            if(map.containsKey(StrConst.DATA_TYPE) && ObjectUtil.isNotEmpty(map.get(StrConst.DATA_TYPE))) {
                                                Integer dataType = Integer.parseInt(map.get(StrConst.DATA_TYPE).toString());
                                                Object relValue = BaseEnum.getEnumByValue(DataTypeEnum.class, dataType, DataTypeEnum.INIT).getDefaultValue(dataValue);
                                                mergedMap.put(map.get(SearchColumnConst.DATA_COLUMN_MAC).toString(), relValue);
                                            }else{
                                                mergedMap.put(map.get(SearchColumnConst.DATA_COLUMN_MAC).toString(), dataValue);
                                            }
                                        }
                                    });
                                    return mergedMap;
                                }
                        )
                ));
    }

    /**
     * 功能描述:
     * 〈客户关系缓存清除〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public Boolean deleteES(InitEsDTO initEsDTO) {
        List<AppModuleBaseBO> moduleBaseBOS = remoteAppService.getModuleBaseInfoByIds(initEsDTO.getModuleIds()).getData();
        List<String> indexNames = moduleBaseBOS.stream().map(AppModuleBaseBO::getModuleIndex).toList();
        esIndexService.deleteIndex(indexNames);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈填充默认数据〉
     * @author 蝉鸣
     */
    public void fillEsScopeData(Map<String,Object> dataMap) {
        //填充扩展数据
        if(dataMap.containsKey(DataScopeConst.DEFAULT_SCOPE_USER_ID) && NumberUtil.isNumber(StrUtil.toString(dataMap.get(DataScopeConst.DEFAULT_SCOPE_USER_ID)))) {
            Long userId = Long.parseLong(dataMap.get(DataScopeConst.DEFAULT_SCOPE_USER_ID).toString());
            SysUserBO sysUserBO = UserCacheUtil.getSysUserInfoCache(userId);
            if(ObjectUtil.isNotEmpty(sysUserBO)){
                JSONArray array = JSONUtil.createArray();
                JSONObject object = JSONUtil.createObj();
                object.set(CompTypeEnum.USER.getIdMac(), userId);
                object.set(CompTypeEnum.USER.getNameMac(), sysUserBO.getNickName());
                array.add(object);
                dataMap.put(DataScopeConst.DEFAULT_SCOPE_USER,array);
            }
        }
        if(dataMap.containsKey(DataScopeConst.DEFAULT_SCOPE_ORG_ID) && NumberUtil.isNumber(StrUtil.toString(dataMap.get(DataScopeConst.DEFAULT_SCOPE_ORG_ID)))) {
            Long orgId = Long.parseLong(dataMap.get(DataScopeConst.DEFAULT_SCOPE_ORG_ID).toString());
            SysOrgInfoBO orgInfoBO = UserCacheUtil.getSysOrgInfoCache(orgId);
            if(ObjectUtil.isNotEmpty(orgInfoBO)){
                JSONArray array = JSONUtil.createArray();
                JSONObject object = JSONUtil.createObj();
                object.set(CompTypeEnum.DEP.getIdMac(), orgId);
                object.set(CompTypeEnum.DEP.getNameMac(), orgInfoBO.getLevelName());
                array.add(object);
                dataMap.put(DataScopeConst.DEFAULT_SCOPE_ORG,array);
            }
        }
    }

    /**
     * 功能描述:
     * 〈填充团队成员数据〉
     * @author 蝉鸣
     */
    public void fillEsTeamData(Map<String,Object> dataMap) {
        //填充扩展数据
        TeamBaseGetDTO getDTO = new TeamBaseGetDTO();
        getDTO.setModuleId(MapUtil.getLong(dataMap, StrConst.MODULE_ID));
        getDTO.setDataId(MapUtil.getLong(dataMap, StrConst.ID));
        Result<List<TeamMemberRelBO>> teamMember = remoteTeamService.getTeamMember(getDTO);
        List<TeamMemberRelBO> relBOS = teamMember.getData();
        if(CollUtil.isEmpty(relBOS)){
            return;
        }
        dataMap.put(StrConst.MEMBER_USER,relBOS);
    }
    /**
     * 功能描述:
     * 〈填充团队成员数据〉
     * @author 蝉鸣
     */
    public void fillEsBpmData(Map<String,Object> dataMap) {
        //判断是否有流程状态字段
        if(dataMap.containsKey(EsConst.BPM_PROCESS_PASS)
                && ObjectUtil.isNotEmpty(dataMap.get(EsConst.BPM_PROCESS_PASS))
                && NumberUtil.isNumber(StrUtil.toString(dataMap.get(EsConst.BPM_PROCESS_PASS)))) {
            Integer processPass = Integer.parseInt(dataMap.get(EsConst.BPM_PROCESS_PASS).toString());
            JSONArray array = AppUtil.getProcessPassEsData(processPass);
            dataMap.put(EsConst.BPM_PROCESS_PASS_JSON,array);
        }
    }

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
}
