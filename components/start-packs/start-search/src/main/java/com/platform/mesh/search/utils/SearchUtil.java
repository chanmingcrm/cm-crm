package com.platform.mesh.search.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.mesh.core.application.domain.dto.CondDTO;
import com.platform.mesh.core.application.domain.dto.QueryDTO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import com.platform.mesh.datascope.constant.DataScopeConst;
import com.platform.mesh.datascope.utils.DataScopeUtil;
import com.platform.mesh.es.domain.dto.EsDocPGetDTO;
import com.platform.mesh.es.enums.EsBoolEnum;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.util.EsUtil;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import com.platform.mesh.search.logic.type.LogicTypeService;
import com.platform.mesh.search.logic.type.factory.LogicTypeFactory;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseValueBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description 搜索工具:
 * @author 蝉鸣
 */
@Component
public class SearchUtil {

    private static LogicTypeFactory logicTypeFactory;

    private static IEsDocService esDocService;

    @Autowired
    public SearchUtil(LogicTypeFactory logicTypeFactory,
                      IEsDocService esDocService) {
        SearchUtil.logicTypeFactory = logicTypeFactory;
        SearchUtil.esDocService = esDocService;
    }

    /**
     * 功能描述: 
     * 〈获取sql查询wrapper〉
     * @param clazz clazz  
     * @param queryDTO queryDTO  
     * @return 正常返回:{@link QueryWrapper<T>}
     * @author 蝉鸣
     */
    public static <T> QueryWrapper<T> getSqlQueryWrapper(Class<T> clazz, QueryDTO queryDTO){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if(CollUtil.isEmpty(queryDTO.getCondDTO())){
        return queryWrapper;
        }
        //将条件根据逻辑类型分组：Map<逻辑类型-条件列表>
        Map<LogicTypeEnum, List<CondDTO>> typeMap = queryDTO.getCondDTO().stream()
                .filter(item->CollUtil.isNotEmpty(item.getSearchValues()))
                .collect(Collectors.groupingBy(CondDTO::getCondType));
        //使用工厂对应方法执行逻辑
        typeMap.forEach((typeEnum,condDTOS)->{
            LogicTypeService typeService = logicTypeFactory.getLogicTypeService(typeEnum);
            typeService.sqlWrapper(queryWrapper,condDTOS);
        });
        return queryWrapper;
    }

    /**
     * 功能描述:
     * 〈获取es查询Query〉
     * @param queryDTO queryDTO
     * @return 正常返回:{@link BoolQuery.Builder}
     * @author 蝉鸣
     */
    public static BoolQuery.Builder getEsBoolQuery(EsDocPGetDTO queryDTO){
        BoolQuery.Builder builderBool = EsUtil.getSearchBuilderBool();
        Map<EsBoolEnum, List<Query>> boolMap = new HashMap<>();
        //设置权限条件
        List<Query> authQueries = setSearchBuilderAuth(queryDTO.getIgnoreScope());
        if(CollUtil.isNotEmpty(authQueries)){
            boolMap.put(EsBoolEnum.FILTER, authQueries);
        }
        if(CollUtil.isEmpty(queryDTO.getCondDTO())){
            //设置bool查询
            EsUtil.setSearchBuilderBool(builderBool, boolMap);
            return builderBool;
        }
        //2:增加业务筛选查询过滤
        //将条件根据逻辑类型分组：Map<逻辑类型-条件列表>
        Map<LogicTypeEnum, List<CondDTO>> typeMap = queryDTO.getCondDTO().stream()
                .filter(item-> ObjectUtil.isNotEmpty(item.getColumnMac()))
//                .filter(item-> CollUtil.isNotEmpty(item.getSearchValues()))
                .collect(Collectors.groupingBy(CondDTO::getCondType));
        //使用工厂对应方法执行逻辑
        typeMap.forEach((typeEnum,condDTOS)->{
            LogicTypeService typeService = logicTypeFactory.getLogicTypeService(typeEnum);
            Map<EsBoolEnum, List<Query>> boolQuery = typeService.esBoolQuery(condDTOS);
            boolQuery.forEach((key,value)-> {
                if(boolMap.containsKey(key)){
                    boolMap.get(key).addAll(value);
                }else{
                    boolMap.put(key,value);
                }
            });
        });
        if(CollUtil.isEmpty(boolMap)){
            return builderBool;
        }
        //设置bool查询
        EsUtil.setSearchBuilderBool(builderBool, boolMap);
        return builderBool;
    }

    /**
     * 功能描述:
     * 〈获取es查询Query〉
     * @return 正常返回:{@link List<Query>}
     * @author 蝉鸣
     */
    public static List<Query> getAuthQuery(Integer dataScope, Integer dataFlag,List<Long> dataIds){
        List<Query> queries = CollUtil.newArrayList();
        //增加数据权限条件
        if(!DataScopeEnum.ALL.getValue().equals(dataScope)){
            if(DataFlagEnum.ORG.getValue().equals(dataFlag)){
                Query orgQuery = QueryBuilders.terms(eq->{
                    eq.field(DataScopeConst.DEFAULT_SCOPE_ORG_ID);
                    eq.terms(item -> item.value(dataIds.stream().map(FieldValue::of).toList()));
                    return eq;
                });
                queries.add(orgQuery);
            }else{
                Query userQuery = QueryBuilders.terms(eq->{
                    eq.field(DataScopeConst.DEFAULT_SCOPE_USER_ID);
                    eq.terms(item -> item.value(dataIds.stream().map(FieldValue::of).toList()));
                    return eq;
                });
                queries.add(userQuery);
            }
        }
        return queries;
    }

    /**
     * 功能描述:
     * 〈获取es查询Query〉
     * @return 正常返回:{@link List<Query>}
     * @author 蝉鸣
     */
    public static List<Query> setSearchBuilderAuth(Boolean ignoreScope){
        List<Query> authQueries = CollUtil.newArrayList();
        Query authBool = QueryBuilders.bool(bo -> {
            if(ObjectUtil.isNotEmpty(ignoreScope) && ignoreScope){
                return bo;
            }
            //数据权限
            //1:增加登录人员数据权限过滤
            Map<Integer, Map<String, List<Long>>> scopeMap = DataScopeUtil.handleDataScopeWithScope();
            bo.must(bo_must->{
                bo_must.bool(bo_must_bool->{
                    //岗位数据权限
                    scopeMap.forEach((scope,data)->{
                        bo_must_bool.should(sh -> {
                            data.forEach((key,value)->{
                                sh.terms(in -> {
                                    in.field(key);
                                    value.add(NumberConst.NUM_0.longValue());
                                    in.terms(item -> item.value(value.stream().map(StrUtil::toString).map(FieldValue::of).toList()));
                                    return in;
                                });
                            });
                            return sh;
                        });
                    });
                    //团队成员
                    bo_must_bool.should(
                            // 情况1：member_user_json 字段不存在
                            Query.of(sq -> sq
                                    .bool(sb -> sb
                                            .mustNot(mn -> mn
                                                    .exists(e -> e
                                                            .field(StrConst.MEMBER_USER)
                                                    )
                                            )
                                            .must(mt->mt
                                                    .terms(t->{
                                                        t.field(DataScopeConst.DEFAULT_SCOPE_USER_ID);
                                                        List<Long> userIds = CollUtil.newArrayList(NumberConst.NUM_0.longValue(), UserCacheUtil.getUserId());
                                                        t.terms(item -> item.value(userIds.stream().map(StrUtil::toString).map(FieldValue::of).toList()));
                                                        return t;
                                                        }
                                                    )
                                            )
                                    )
                            ),
                            // 情况2：member_user_json 字段存在且符合条件
                            Query.of(sq -> sq
                                    .term(t -> t
                                            .field(StrConst.MEMBER_USER.concat(SymbolConst.PERIOD).concat(StrConst.ID))
                                            .value(UserCacheUtil.getUserId())
                                    )
                            )
                    );
                    return bo_must_bool;
                });
                return bo_must;
            });
            return bo;
        });
        authQueries.add(authBool);
        return authQueries;
    }

    /**
     * 功能描述:
     * 〈校验数据唯一性〉
     * @author 蝉鸣
     */
    public static Boolean checkDataUnique(String moduleIndex,Map<String, Object> docData) {
        if(ObjectUtil.isEmpty(moduleIndex) || CollUtil.isEmpty(docData)){
            return Boolean.FALSE;
        }
        return esDocService.existDocument(moduleIndex,docData);
    }

    /**
     * 功能描述:
     * 〈是否存在文档〉
     * @author 蝉鸣
     */
    public static Object getExistData(String moduleIndex,Map<String, Object> docData) {
        if(ObjectUtil.isEmpty(moduleIndex) || CollUtil.isEmpty(docData)){
            return null;
        }
        return esDocService.getExistData(moduleIndex,docData);
    }

    /**
     * 功能描述:
     * 〈解析导入数据〉
     * @author 蝉鸣
     */
    public static Object parseImportData(String compMac, Object relData, String columnMac, Object value) {
        if(ObjectUtil.isNull(compMac) || ObjectUtil.isEmpty(columnMac) || ObjectUtil.isEmpty(value)){
            return value;
        }
        CompTypeEnum enumByDesc = BaseEnum.getEnumByDesc(CompTypeEnum.class, compMac);
        if(ObjectUtil.isEmpty(enumByDesc)){
            return value;
        }
        switch (enumByDesc){
            case CHECKBOX, RADIO, SELECT ->{
                if(ObjectUtil.isEmpty(relData)){
                    return null;
                }
                //解析字典类
                DictBaseValueBO sysDict = UserCacheUtil.getSysDictByName(Long.parseLong(relData.toString()),StrUtil.toString(value));
                if(ObjectUtil.isEmpty(sysDict)){
                    return null;
                }
                return JSONUtil.createArray()
                        .put(JSONUtil.createObj()
                                    .putOpt(enumByDesc.getIdMac(),sysDict.getDictValue())
                                    .putOpt(enumByDesc.getNameMac(),sysDict.getDictName())
                                    .putOpt(StrConst.DATA_COLOR,sysDict.getDictColor())
                            );
            }
            case USER -> {
                //解析成员
                OrgMemberBO memberCache = UserCacheUtil.getTenantMemberByName(StrUtil.toString(value));
                if(ObjectUtil.isEmpty(memberCache)){
                    return null;
                }
                Long dataId = memberCache.getId();
                if(columnMac.equals(DataScopeConst.DEFAULT_SCOPE_USER)){
                    //需要获取user_id
                    dataId = memberCache.getUserId();
                }
                return JSONUtil.createArray()
                        .put(JSONUtil.createObj()
                                .putOpt(enumByDesc.getIdMac(),dataId)
                                .putOpt(enumByDesc.getNameMac(),memberCache.getMemberName())
                        );
            }
            case DEP -> {
                //解析组织
                OrgLevelBO levelCache = UserCacheUtil.getTenantLevelByName(StrUtil.toString(value));
                if(ObjectUtil.isEmpty(levelCache)){
                    return null;
                }
                return JSONUtil.createArray()
                        .put(JSONUtil.createObj()
                                .putOpt(enumByDesc.getIdMac(),levelCache.getId())
                                .putOpt(enumByDesc.getNameMac(),levelCache.getLevelName())
                        );
            }
            case RELEVANCE -> {
                if(ObjectUtil.isEmpty(relData)){
                    return null;
                }
                //解析关联数据
                String id = esDocService.getDocumentIdByName(StrUtil.toString(relData), StrConst.DATA_NAME,value.toString());
                if(ObjectUtil.isNotEmpty(id) && NumberUtil.isNumber(id)) {
                    return JSONUtil.createArray()
                            .put(JSONUtil.createObj()
                                    .putOpt(enumByDesc.getIdMac(),Long.parseLong(id))
                                    .putOpt(enumByDesc.getNameMac(),value)
                            );
                }else{
                    return null;
                }
            }
            default -> {return value;}
        }
    }

}
