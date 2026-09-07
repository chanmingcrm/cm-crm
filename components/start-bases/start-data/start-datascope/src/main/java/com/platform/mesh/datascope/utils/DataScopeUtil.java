package com.platform.mesh.datascope.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.core.enums.data.DataScopeEnum;
import com.platform.mesh.datascope.constant.DataScopeConst;
import com.platform.mesh.datascope.domain.ScopeBO;
import com.platform.mesh.datascope.exception.DataScopeExceptionEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 数据权限处理
 * @author 蝉鸣
 */
public class DataScopeUtil {

    /**
     * 功能描述:
     * 〈数据范围过滤〉
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public static Map<Integer, Map<String, List<Long>>> handleDataScopeWithScope() {
        // 获取当前的用户
        LoginUserBO loginUser = UserCacheUtil.getLoginUser();
        if (ObjectUtil.isNotNull(loginUser)) {
            // 如果是超级管理员，则不过滤数据
            if (!SecurityUtils.isAdmin(loginUser.getUserId())) {
                return dataScopeWithScope(loginUser.getAccountId());
            }else{
                return new HashMap<>();
            }
        }else{
            //当前登录人员为空则抛出异常
            throw  DataScopeExceptionEnum.DATA_SCOPE_LOGIN_USER.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈数据范围过滤〉
     * @param accountId accountId
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public static Map<Integer, Map<String, List<Long>>> dataScopeWithScope(Long accountId) {
        Object scopeCache = UserCacheUtil.getUserPrefixCache(accountId, CacheConstants.SYS_ACCOUNT_SCOPE, Object.class);
        if(ObjectUtil.isNotEmpty(scopeCache)){
            //重置key有效时间
            RedissonUtil.expire(CacheConstants.SYS_ACCOUNT_SCOPE.concat(SymbolConst.COLON).concat(accountId.toString()), Duration.ofHours(NumberConst.NUM_2));
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(
                    scopeCache,
                    mapper.getTypeFactory().constructMapType(
                            Map.class,
                            mapper.getTypeFactory().constructType(Integer.class),
                            mapper.getTypeFactory().constructMapType(
                                    Map.class,
                                    mapper.getTypeFactory().constructType(String.class),
                                    mapper.getTypeFactory().constructCollectionType(List.class, Long.class)
                            )
                    )
            );
        }
        SysAccountBO userAccountCache = UserCacheUtil.getAccountInfoCache(accountId);
        List<SysOrgBO> userOrgCache = UserCacheUtil.getAccountOrgCache(accountId);
        Long userId = userAccountCache.getUserId();
        //权限范围,数据隔离字段,数据集合
        Map<Integer, Map<String, List<Long>>> dataScopeMap = new HashMap<>();
        //如果不存在组织以及相关数据权限配置则默认本人
        List<SysOrgBO> sysOrgBOS = userOrgCache.stream().filter(sysOrgBO -> ObjectUtil.isNotEmpty(sysOrgBO) && ObjectUtil.isNotEmpty(sysOrgBO.getDataScope())).toList();
        if(CollUtil.isEmpty(sysOrgBOS)){
            //查询自己
            Map<String, List<Long>> scopeMap = new HashMap<>();
            scopeMap.put(DataScopeConst.DEFAULT_SCOPE_USER_ID, CollUtil.newArrayList(userId));
            dataScopeMap.put(DataScopeEnum.SELF.getValue(),scopeMap);
            return dataScopeMap;
        }
        //转换数据格式
        Map<Integer, List<SysOrgBO>> dataScopeGroup = sysOrgBOS.stream().filter(scope->ObjectUtil.isNotEmpty(scope.getDataScope())).collect(Collectors.groupingBy(SysOrgBO::getDataScope));

        //权限数据组装
        dataScopeGroup.forEach((dataScope,orgList)->{
            //域map
            Map<String, List<Long>> scopeMap = new HashMap<>();
            //组织Ids
            List<Long> levelIdList;
            //查询所在部门
            //包含下属部门
            if (DataScopeEnum.ALL.getValue().equals(dataScope) || DataScopeEnum.SUB.getValue().equals(dataScope)) {
                levelIdList = dataOrgScopeIdList(dataScope, orgList);
                scopeMap(scopeMap,DataScopeConst.DEFAULT_SCOPE_ORG_ID,levelIdList);
                dataScopeMap.put(DataScopeEnum.SUB.getValue(),scopeMap);
            }
            else if (DataScopeEnum.LEVEL.getValue().equals(dataScope)) {
                levelIdList = dataOrgScopeIdList(dataScope, orgList);
                scopeMap(scopeMap,DataScopeConst.DEFAULT_SCOPE_ORG_ID,levelIdList);
                dataScopeMap.put(DataScopeEnum.LEVEL.getValue(),scopeMap);
            }
            else if (DataScopeEnum.SELF.getValue().equals(dataScope)) {
                //查询自己
                scopeMap(scopeMap,DataScopeConst.DEFAULT_SCOPE_USER_ID,CollUtil.newArrayList(userId));
                dataScopeMap.put(DataScopeEnum.SELF.getValue(),scopeMap);
            }
            else if (DataScopeEnum.CUSTOM.getValue().equals(dataScope)) {
                //查询自定义权限
                levelIdList = dataOrgScopeIdList(dataScope, orgList);
                List<Long> userIdList = dataUserScopeIdList(dataScope, orgList);
                userIdList.add(userId);
                scopeMap(scopeMap,DataScopeConst.DEFAULT_SCOPE_ORG_ID,levelIdList);
                scopeMap(scopeMap,DataScopeConst.DEFAULT_SCOPE_USER_ID,userIdList);
                dataScopeMap.put(DataScopeEnum.CUSTOM.getValue(),scopeMap);
            }
        });
        UserCacheUtil.setUserPrefixCache(accountId, CacheConstants.SYS_ACCOUNT_SCOPE, dataScopeMap);
        return dataScopeMap;
    }

    /**
     * 功能描述:
     * 〈获取人员关联数据〉
     * @param scopeMap scopeMap
     * @param scopeName scopeName
     * @param scopeList scopeList
     * @author 蝉鸣
     */
    public static void scopeMap(Map<String, List<Long>> scopeMap,String scopeName,List<Long> scopeList){
        //0代表所有人都可看到的初始化数据
        List<Long> mutableScopeList = new ArrayList<>(scopeList);
        mutableScopeList.add(NumberConst.NUM_0.longValue());
        if(scopeMap.containsKey(scopeName)){
            scopeMap.get(scopeName).addAll(mutableScopeList);
        }else{
            scopeMap.put(scopeName,mutableScopeList);
        }
    }


    /**
     * 功能描述:
     * 〈获取人员关联数据〉
     * @param dataScope dataScope
     * @param orgBOList orgBOList
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    public static List<Long> dataOrgScopeIdList(Integer dataScope,List<SysOrgBO> orgBOList) {
        List<Long> dataIds = CollUtil.newArrayList();
        if(DataScopeEnum.ALL.getValue().equals(dataScope) || DataScopeEnum.SUB.getValue().equals(dataScope)){
            return orgBOList.stream()
                    .flatMap(levelList -> {
                        List<Long> ids = CollUtil.newArrayList(levelList.getLevelId());
                        ids.addAll(levelList.getLevelIds());
                        return ids.stream();
                    }).filter(ObjectUtil::isNotEmpty).distinct()
                    .toList();
        }
        if(DataScopeEnum.LEVEL.getValue().equals(dataScope)){
            return orgBOList.stream()
                    .map(SysOrgBO::getLevelId).filter(ObjectUtil::isNotEmpty).distinct()
                    .toList();
        }
        if(DataScopeEnum.SELF.getValue().equals(dataScope)){
            return orgBOList.stream()
                    .map(SysOrgBO::getLevelId).filter(ObjectUtil::isNotEmpty).distinct()
                    .toList();
        }
        if(DataScopeEnum.CUSTOM.getValue().equals(dataScope)){
            return orgBOList.stream()
                    .flatMap(levelList -> {
                        List<Long> ids = CollUtil.newArrayList(levelList.getDataId());
                        ids.addAll(levelList.getLevelIds());
                        return ids.stream();
                    }).filter(ObjectUtil::isNotEmpty).distinct()
                    .toList();
        }
        return dataIds;
    }

    /**
     * 功能描述:
     * 〈获取人员关联数据〉
     * @param dataScope dataScope
     * @param orgBOList orgBOList
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    public static List<Long> dataUserScopeIdList(Integer dataScope,List<SysOrgBO> orgBOList) {
        List<Long> dataIds = CollUtil.newArrayList();
        if(DataScopeEnum.CUSTOM.getValue().equals(dataScope)){
            return orgBOList.stream()
                    .filter(item -> item.getDataScope().equals(dataScope))
                    .filter(item -> item.getDataFlag().equals(DataFlagEnum.USER.getValue()))
                    .map(SysOrgBO::getDataId).filter(ObjectUtil::isNotEmpty).distinct()
                    .toList();
        }
        return dataIds;
    }

    /**
     * 功能描述:
     * 〈获取当前数据权限下数据〉
     * @param dataScope dataScope
     * @param dataFlag dataFlag
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    public static ScopeBO parseBiDTO(Integer dataScope,Integer dataFlag,List<Long> dataIds) {
        ScopeBO scopeBO = new ScopeBO();
        scopeBO.setDataScope(dataScope);
        scopeBO.setDataFlag(dataFlag);
        //解析dataScope
        DataScopeEnum enumByValue = BaseEnum.getEnumByValue(DataScopeEnum.class, dataScope, DataScopeEnum.SELF);
        switch (enumByValue) {
            case ALL,SUB:{
                //获取当前人员的同级以及下属部门
                LoginUserBO loginUser = Objects.requireNonNull(UserCacheUtil.getLoginUser());
                SysAccountBO accountBO = UserCacheUtil.getAccountInfoCache(loginUser.getAccountId());
                List<SysOrgBO> accountOrgCache = UserCacheUtil.getAccountOrgCache(accountBO.getAccountId());
                dataIds =  accountOrgCache.stream().filter(ObjectUtil::isNotEmpty).flatMap(item -> {
                    List<Long> levelIds = CollUtil.newArrayList();
                    if(ObjectUtil.isNotEmpty(item.getLevelId())){
                        levelIds.add(item.getLevelId());
                    }
                    if(CollUtil.isNotEmpty(item.getLevelIds())){
                        levelIds.addAll(item.getLevelIds());
                    }
                    return levelIds.stream();
                }).collect(Collectors.toList());
                if(CollUtil.isEmpty(dataIds)){
                    dataIds = CollUtil.newArrayList(NumberConst.NUM_0.longValue(), loginUser.getUserId());
                    scopeBO.setDataFlag(DataFlagEnum.USER.getValue());
                    scopeBO.setDataIds(dataIds);
                    return scopeBO;
                }
                //O 代表无限制的数据
                dataIds.add(NumberConst.NUM_0.longValue());
                scopeBO.setDataFlag(DataFlagEnum.ORG.getValue());
                scopeBO.setDataIds(dataIds);
                return scopeBO;
            }
            case LEVEL:{
                //获取当前人员的同级部门
                LoginUserBO loginUser = Objects.requireNonNull(UserCacheUtil.getLoginUser());
                SysAccountBO accountBO = UserCacheUtil.getAccountInfoCache(loginUser.getAccountId());
                List<SysOrgBO> accountOrgCache = UserCacheUtil.getAccountOrgCache(accountBO.getAccountId());
                dataIds = accountOrgCache.stream().map(SysOrgBO::getLevelId).distinct().toList();
                if(CollUtil.isEmpty(dataIds)){
                    dataIds = CollUtil.newArrayList(NumberConst.NUM_0.longValue(), loginUser.getUserId());
                    scopeBO.setDataFlag(DataFlagEnum.USER.getValue());
                    scopeBO.setDataIds(dataIds);
                    return scopeBO;
                }
                //O 代表无限制的数据
                dataIds.add(NumberConst.NUM_0.longValue());
                scopeBO.setDataFlag(DataFlagEnum.ORG.getValue());
                scopeBO.setDataIds(dataIds);
                return scopeBO;
            }
            case CUSTOM:{
                if(CollUtil.isEmpty(scopeBO.getDataIds())){
                    scopeBO.setDataFlag(DataFlagEnum.USER.getValue());
                    //返回不存在的值避免过滤条件失效
                    scopeBO.setDataIds(CollUtil.newArrayList(IdUtil.getSnowflakeNextId()));
                    return scopeBO;
                }
                //去除非下属人员
                LoginUserBO loginUser = Objects.requireNonNull(UserCacheUtil.getLoginUser());
                SysAccountBO accountBO = UserCacheUtil.getAccountInfoCache(loginUser.getAccountId());
                List<OrgMemberRelBO> accountChildCache = UserCacheUtil.getAccountChildCache(accountBO.getAccountId());
                if(CollUtil.isEmpty(accountChildCache)){
                    //判断是否包含当前人员
                    if(scopeBO.getDataIds().contains(loginUser.getUserId())){
                        scopeBO.setDataFlag(DataFlagEnum.USER.getValue());
                        scopeBO.setDataIds(CollUtil.newArrayList(loginUser.getUserId()));
                        return scopeBO;
                    }else{
                        //返回不存在的值避免过滤条件失效
                        scopeBO.setDataIds(CollUtil.newArrayList(IdUtil.getSnowflakeNextId()));
                        return scopeBO;
                    }
                }
                if(scopeBO.getDataFlag().equals(DataFlagEnum.USER.getValue())){
                    List<Long> userIds = accountChildCache.stream().map(OrgMemberRelBO::getUserId)
                            .filter(dataIds::contains).distinct().toList();
                    if(CollUtil.isEmpty(userIds)){
                        scopeBO.setDataIds(CollUtil.newArrayList(loginUser.getUserId()));
                        return scopeBO;
                    }else{
                        scopeBO.setDataIds(userIds);
                        return scopeBO;
                    }
                }else{
                    //去除非下属部门
                    List<Long> levelIds = accountChildCache.stream().map(OrgMemberRelBO::getLevelId)
                            .filter(dataIds::contains).distinct().toList();
                    if(CollUtil.isEmpty(levelIds)){
                        scopeBO.setDataIds(CollUtil.newArrayList(loginUser.getUserId()));
                        return scopeBO;
                    }else{
                        scopeBO.setDataIds(levelIds);
                        return scopeBO;
                    }
                }
            }
            default: {
                Long userId = UserCacheUtil.getUserId();
                scopeBO.setDataScope(DataScopeEnum.SELF.getValue());
                scopeBO.setDataFlag(DataFlagEnum.USER.getValue());
                scopeBO.setDataIds(CollUtil.newArrayList(userId));
                return scopeBO;
            }
        }
    }

}
