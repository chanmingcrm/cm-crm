package com.platform.mesh.security.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseValueBO;
import com.platform.mesh.upms.api.modules.dict.base.feign.RemoteDictService;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgLevelBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.*;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @description Cache工具:仅为了快速处理人员相关缓存，通常业务请正常引入 CacheManager 操作
 * @author 蝉鸣
 */
@Component
@ConditionalOnClass(CacheManager.class)
public class UserCacheUtil {

    private static CacheManager cacheManager;

    private static RemoteDictService remoteDictService;

    private static RemoteUserService remoteUserService;

    private static RemoteOrgMemberService remoteOrgMemberService;


    @Autowired
    public UserCacheUtil(CacheManager cacheManager
            , RemoteDictService remoteDictService
            , RemoteUserService remoteUserService
            , RemoteOrgMemberService remoteOrgMemberService) {
        UserCacheUtil.cacheManager = cacheManager;
        UserCacheUtil.remoteDictService = remoteDictService;
        UserCacheUtil.remoteUserService = remoteUserService;
        UserCacheUtil.remoteOrgMemberService = remoteOrgMemberService;
    }

    /**
     * 功能描述:
     * 〈获取登录人员Id〉
     * @author 蝉鸣
     */
    public static Long getUserId() {
        return Objects.requireNonNull(getLoginUser()).getUserId();
    }

    /**
     * 功能描述:
     * 〈获取登录账户Id〉
     * @author 蝉鸣
     */
    public static Long getAccountId() {
        return Objects.requireNonNull(getLoginUser()).getAccountId();
    }

    /**
     * 功能描述:
     * 〈获取登录人员〉
     * @author 蝉鸣
     */
    public static LoginUserBO getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    /**
     * 功能描述:
     * 〈获取用户缓存〉
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static SysAccountInfoBO getSysAccountInfoCache(Long accountId) {

        SysAccountInfoBO accountInfoBO = new SysAccountInfoBO();
        //获取账户信息
        SysAccountBO accountBO = getAccountInfoCache(accountId);
        if(ObjectUtil.isEmpty(accountBO)){
            return accountInfoBO;
        }
        //获取用户ID
        Long userId = accountBO.getUserId();
        //获取用户信息
        accountInfoBO.setSysUserBO(getSysUserInfoCache(userId));
        //获取账户信息
        accountInfoBO.setAccountBO(accountBO);
        //获取角色信息
        accountInfoBO.setRoleBOS(getAccountRoleCache(accountId));
        //获取菜单信息
        accountInfoBO.setMenuBOS(getAccountMenuCache(accountId));
        //获取组织信息
        accountInfoBO.setOrgBOS(getAccountOrgCache(accountId));
        return accountInfoBO;
    }

    /**
     * 功能描述:
     * 〈清空用户缓存〉
     * @author 蝉鸣
     */
    public static void clearSysAccountInfoCache(Long accountId) {
        //获取账户信息
        SysAccountBO accountBO = getAccountInfoCache(accountId);
        if(ObjectUtil.isEmpty(accountBO)){
            return;
        }
        //获取用户ID
        Long userId = accountBO.getUserId();
        //清空用户信息
        delSysUserInfoCache(userId);
        //清空账户信息
        delAccountInfoCache(accountId);
        //清空角色信息
        delAccountRoleCache(accountId);
        //清空菜单信息
        delAccountMenuCache(accountId);
        //清空组织信息
        delAccountOrgCache(accountId);
        //清空权限信息
        delDataPrefixCache(CacheConstants.SYS_ACCOUNT_SCOPE,accountId);
    }

    /**
     * 功能描述:
     * 〈设置用户账户缓存〉
     * @param sysAccountBO sysAccountBO
     * @author 蝉鸣
     */
    public static void setAccountInfoCache(SysAccountBO sysAccountBO) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.USER_ACCOUNT_DETAILS));
        cacheOptional.ifPresent(cache -> cache.put(sysAccountBO.getAccountId(), sysAccountBO));
    }

    /**
     * 功能描述:
     * 〈获取用户账户缓存〉
     * @param accountId accountId
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static SysAccountBO getAccountInfoCache(Long accountId) {
        SysAccountBO sysAccountBO = null;
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.USER_ACCOUNT_DETAILS));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper;
            try {
                valueWrapper = Optional.ofNullable(cache.get(accountId));
            } catch (SerializationException exception) {
                valueWrapper = Optional.empty();
            }
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof SysAccountBO accountBO){
                //重置key有效时间
                resetExpire(CacheConstants.USER_ACCOUNT_DETAILS, accountId);
                return accountBO;
            }else{
                cache.evictIfPresent(accountId);
            }
        }
        if(ObjectUtil.isEmpty(sysAccountBO)){
            //重新请求
            Result<SysAccountBO> accountResult =  remoteUserService.getAccountInfoByAccountId(accountId);
            Optional<SysAccountBO> accountInfoBO = ResultUtil.of(accountResult).getData();
            if (accountInfoBO.isPresent()) {
                sysAccountBO = accountInfoBO.get();
                //设置缓存
                setAccountInfoCache(sysAccountBO);
            }
        }
        return sysAccountBO;
    }

    /**
     * 功能描述:
     * 〈清除用户帐户缓存〉
     * @param accountId accountId
     * @author 蝉鸣
     */
    public static void delAccountInfoCache(Long accountId) {
        delDataPrefixCache(CacheConstants.USER_ACCOUNT_DETAILS,accountId);
    }

    /**
     * 功能描述:
     * 〈设置用户角色缓存〉
     * @param accountId accountId
     * @param roleBOS roleBOS
     * @author 蝉鸣
     */
    public static void setAccountRoleCache(Long accountId,List<SysRoleBO> roleBOS) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(StrUtil.format(CacheConstants.USER_ROLE_DETAILS)));
        cacheOptional.ifPresent(cache -> cache.putIfAbsent(accountId, roleBOS));
    }

    /**
     * 功能描述:
     * 〈获取用户组织缓存〉
     * @param accountId accountId
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static List<SysRoleBO> getAccountRoleCache(Long accountId) {
        List<SysRoleBO> roleBOS = CollUtil.newArrayList();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(StrUtil.format(CacheConstants.USER_ROLE_DETAILS)));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(accountId));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof List<?> roleBoList){
                //重置key有效时间
                resetExpire(CacheConstants.USER_ROLE_DETAILS, accountId);
                return BeanUtil.copyToList(roleBoList,SysRoleBO.class);
            }else{
                cache.evictIfPresent(accountId);
            }
        }
        if(ObjectUtil.isEmpty(roleBOS)){
            //重新请求
            Result<List<SysRoleBO>> roleResult =  remoteUserService.getRoleInfoByAccountId(accountId);
            Optional<List<SysRoleBO>> roleInfoBO = ResultUtil.of(roleResult).getData();
            if (roleInfoBO.isPresent()) {
                roleBOS = roleInfoBO.get();
                //设置缓存
                setAccountRoleCache(accountId, roleBOS);
            }
        }
        return roleBOS;
    }

    /**
     * 功能描述:
     * 〈清除用户角色缓存〉
     * @param accountId accountId
     * @author 蝉鸣
     */
    public static void delAccountRoleCache(Long accountId) {
        delDataPrefixCache(StrUtil.format(CacheConstants.USER_ROLE_DETAILS),accountId);
    }

    /**
     * 功能描述:
     * 〈设置用户菜单缓存〉
     * @param accountId accountId
     * @param menuBOS menuBOS
     * @author 蝉鸣
     */
    public static void setAccountMenuCache(Long accountId,List<SysMenuBO> menuBOS) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(StrUtil.format(CacheConstants.USER_MENU_DETAILS)));
        cacheOptional.ifPresent(cache -> cache.putIfAbsent(accountId, menuBOS));
    }

    /**
     * 功能描述:
     * 〈获取用户菜单缓存〉
     * @param accountId accountId
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static List<SysMenuBO> getAccountMenuCache(Long accountId) {
        List<SysMenuBO> menuBOS = CollUtil.newArrayList();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(StrUtil.format(CacheConstants.USER_MENU_DETAILS)));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(accountId));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof List<?> menuBoList){
                //重置key有效时间
                resetExpire(CacheConstants.USER_MENU_DETAILS, accountId);
                return BeanUtil.copyToList(menuBoList,SysMenuBO.class);
            }else{
                cache.evictIfPresent(accountId);
            }
        }
        if(ObjectUtil.isEmpty(menuBOS)){
            //重新请求
            Result<List<SysMenuBO>> menuResult =  remoteUserService.getMenuInfoByAccountId(accountId);
            Optional<List<SysMenuBO>> menuInfoBO = ResultUtil.of(menuResult).getData();
            if (menuInfoBO.isPresent()) {
                menuBOS = menuInfoBO.get();
                //设置缓存
                setAccountMenuCache(accountId, menuBOS);
            }
        }
        return menuBOS;
    }

    /**
     * 功能描述:
     * 〈清除用户菜单缓存〉
     * @param accountId accountId
     * @author 蝉鸣
     */
    public static void delAccountMenuCache(Long accountId) {
        delDataPrefixCache(StrUtil.format(CacheConstants.USER_MENU_DETAILS),accountId);
    }

    /**
     * 功能描述:
     * 〈设置用户组织缓存〉
     * @param accountId accountId
     * @param orgBOS orgBOS
     * @author 蝉鸣
     */
    public static void setAccountOrgCache(Long accountId,List<SysOrgBO> orgBOS) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(StrUtil.format(CacheConstants.USER_ORG_DETAILS)));
        cacheOptional.ifPresent(cache -> cache.putIfAbsent(accountId, orgBOS));
    }

    /**
     * 功能描述:
     * 〈获取用户组织缓存〉
     * @param accountId accountId
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static List<SysOrgBO> getAccountOrgCache(Long accountId) {
        List<SysOrgBO> orgBOS = CollUtil.newArrayList();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(StrUtil.format(CacheConstants.USER_ORG_DETAILS)));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(accountId));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof List<?> orgBoList){
                //重置key有效时间
                resetExpire(StrUtil.format(CacheConstants.USER_ORG_DETAILS), accountId);
                return BeanUtil.copyToList(orgBoList,SysOrgBO.class);
            }else{
                cache.evictIfPresent(accountId);
            }
        }
        if(ObjectUtil.isEmpty(orgBOS)){
            //重新请求
            Result<List<SysOrgBO>> orgResult =  remoteUserService.getOrgInfoByAccountId(accountId);
            Optional<List<SysOrgBO>> orgInfoBO = ResultUtil.of(orgResult).getData();
            if (orgInfoBO.isPresent()) {
                orgBOS = orgInfoBO.get();
                //设置缓存
                setAccountOrgCache(accountId, orgBOS);
            }
        }
        return orgBOS;
    }

    /**
     * 功能描述:
     * 〈清除用户组织缓存〉
     * @param accountId accountId
     * @author 蝉鸣
     */
    public static void delAccountOrgCache(Long accountId) {
        delDataPrefixCache(StrUtil.format(CacheConstants.USER_ORG_DETAILS),accountId);
    }

    /**
     * 功能描述:
     * 〈设置用户下属缓存〉
     * @param accountId accountId
     * @param relBOS relBOS
     * @author 蝉鸣
     */
    public static void setAccountChildCache(Long accountId,List<OrgMemberRelBO> relBOS) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(StrUtil.format(CacheConstants.USER_CHILD_DETAILS)));
        cacheOptional.ifPresent(cache -> cache.putIfAbsent(accountId, relBOS));
    }

    /**
     * 功能描述:
     * 〈获取用户下属缓存〉
     * @param accountId accountId
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static List<OrgMemberRelBO> getAccountChildCache(Long accountId) {
        List<OrgMemberRelBO> relBOS = CollUtil.newArrayList();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(StrUtil.format(CacheConstants.USER_CHILD_DETAILS)));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(accountId));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof List<?> relBoList){
                //重置key有效时间
                resetExpire(StrUtil.format(CacheConstants.USER_CHILD_DETAILS), accountId);
                return BeanUtil.copyToList(relBoList,OrgMemberRelBO.class);
            }else{
                cache.evictIfPresent(accountId);
            }
        }
        if(ObjectUtil.isEmpty(relBOS)){
            //重新请求
            Result<List<OrgMemberRelBO>> childResult =  remoteOrgMemberService.getOrgChildUserRelByAccountId(accountId);
            Optional<List<OrgMemberRelBO>> childInfoBO = ResultUtil.of(childResult).getData();
            if (childInfoBO.isPresent()) {
                relBOS = childInfoBO.get();
                //设置缓存
                setAccountChildCache(accountId, relBOS);
            }
        }
        return relBOS;
    }

    /**
     * 功能描述:
     * 〈清除用户下属缓存〉
     * @param accountId accountId
     * @author 蝉鸣
     */
    public static void delAccountChildCache(Long accountId) {
        delDataPrefixCache(StrUtil.format(CacheConstants.USER_CHILD_DETAILS),accountId);
    }

    /**
     * 功能描述:
     * 〈设置用户基本缓存〉
     * @param userId userId
     * @param sysUserBO sysUserBO
     * @author 蝉鸣
     */
    public static void setSysUserInfoCache(Long userId,SysUserBO sysUserBO) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_USER_DETAILS));
        cacheOptional.ifPresent(cache -> cache.put(userId, sysUserBO));
    }

    /**
     * 功能描述:
     * 〈获取用户基本缓存〉
     * @param userId userId
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static SysUserBO getSysUserInfoCache(Long userId) {
        SysUserBO sysUserBO = null;
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_USER_DETAILS));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(userId));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof SysUserBO userBO){
                //重置key有效时间
                resetExpire(CacheConstants.SYS_USER_DETAILS, userId);
                return userBO;
            }else{
                cache.evictIfPresent(userId);
            }
        }
        if(ObjectUtil.isEmpty(sysUserBO)){
            //重新请求
            Result<SysUserBO> userResult =  remoteUserService.getUserInfoByUserId(userId);
            Optional<SysUserBO> userInfoBO = ResultUtil.of(userResult).getData();
            if (userInfoBO.isPresent()) {
                sysUserBO = userInfoBO.get();
                //设置缓存
                setSysUserInfoCache(userId,sysUserBO);
            }
        }
        return sysUserBO;
    }

    /**
     * 功能描述:
     * 〈清除用户基本信息缓存〉
     * @param userId userId
     * @author 蝉鸣
     */
    public static void delSysUserInfoCache(Long userId) {
        delDataPrefixCache(CacheConstants.SYS_USER_DETAILS,userId);
    }

    /**
     * 功能描述:
     * 〈设置系统组织缓存〉
     * @param orgResult orgResult
     * @author 蝉鸣
     */
    public static void setSysOrgInfoCache(Result<SysOrgInfoBO> orgResult) {
        SysOrgInfoBO orgInfoBO = orgResult.getData();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_ORG_DETAILS));
        cacheOptional.ifPresent(cache -> cache.put(orgInfoBO.getLevelId(), orgInfoBO));
    }

    /**
     * 功能描述:
     * 〈获取系统组织缓存〉
     * @param levelId levelId
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static SysOrgInfoBO getSysOrgInfoCache(Long levelId) {
        SysOrgInfoBO orgInfoBO = null;
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_ORG_DETAILS));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(levelId));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof SysOrgInfoBO org){
                //重置key有效时间
                resetExpire(CacheConstants.SYS_ORG_DETAILS, levelId);
                return org;
            }else{
                cache.evictIfPresent(levelId);
            }
        }
        if(ObjectUtil.isEmpty(orgInfoBO)){
            //重新请求
            Result<SysOrgInfoBO> orgResult = remoteUserService.getOrgInfoByLevelId(levelId);
            Optional<SysOrgInfoBO> orgInfo = ResultUtil.of(orgResult).getData();
            if (orgInfo.isPresent()) {
                orgInfoBO = orgInfo.get();
                //设置缓存
                setSysOrgInfoCache(orgResult);
            }
        }
        return orgInfoBO;
    }

    /**
     * 功能描述:
     * 〈清除系统组织缓存〉
     * @param levelId levelId
     * @author 蝉鸣
     */
    public static void delSysOrgInfoCache(Long levelId) {
        delDataPrefixCache(CacheConstants.SYS_ORG_DETAILS,levelId);
    }

    /**
     * 功能描述:
     * 〈设置系统字典缓存〉
     * @param orgResult orgResult
     * @author 蝉鸣
     */
    public static void setSysDictCache(String key,Result<DictBaseValueBO> orgResult) {
        DictBaseValueBO dictBO = orgResult.getData();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_DICT_NAME));
        cacheOptional.ifPresent(cache -> cache.put(key, dictBO));
    }

    /**
     * 功能描述:
     * 〈获取系统字典缓存〉
     * @param dictName dictName
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static DictBaseValueBO getSysDictByName(Long dictId,String dictName) {
        DictBaseValueBO dictBO = null;
        String key = dictId.toString();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_DICT_NAME));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(key));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof DictBaseValueBO dict){
                //重置key有效时间
                resetExpire(CacheConstants.SYS_DICT_NAME, key);
                return dict;
            }else{
                cache.evictIfPresent(key);
            }
        }
        if(ObjectUtil.isEmpty(dictBO)){
            //重新请求
            Result<DictBaseValueBO> dictResult = remoteDictService.getFistSysDictByName(dictId,dictName);
            Optional<DictBaseValueBO> dict = ResultUtil.of(dictResult).getData();
            if (dict.isPresent()) {
                dictBO = dict.get();
                //设置缓存
                setSysDictCache(key,dictResult);
            }
        }
        return dictBO;
    }

    /**
     * 功能描述:
     * 〈获取系统字典缓存〉
     * @param dictMac dictMac
     * @param dictValue dictValue
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static DictBaseValueBO getSysDictByMac(String dictMac,Integer dictValue) {
        DictBaseValueBO dictBO = null;
        String key = dictMac.concat(dictValue.toString());
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_DICT_NAME));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(key));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof DictBaseValueBO dict){
                //重置key有效时间
                resetExpire(CacheConstants.SYS_DICT_NAME, key);
                return dict;
            }else{
                cache.evictIfPresent(key);
            }
        }
        if(ObjectUtil.isEmpty(dictBO)){
            //重新请求
            Result<DictBaseValueBO> dictResult = remoteDictService.getFistSysDictByMac(dictMac,dictValue);
            Optional<DictBaseValueBO> dict = ResultUtil.of(dictResult).getData();
            if (dict.isPresent()) {
                dictBO = dict.get();
                //设置缓存
                setSysDictCache(key,dictResult);
            }
        }
        return dictBO;
    }

    /**
     * 功能描述:
     * 〈清除系统字典缓存〉
     * @author 蝉鸣
     */
    public static void delSysDictCache() {
        Long accountId = Objects.requireNonNull(getLoginUser()).getAccountId();
        delDataPrefixCache(CacheConstants.SYS_DICT_NAME,accountId.toString());
    }

    /**
     * 功能描述:
     * 〈设置系统组织缓存〉
     * @param orgResult orgResult
     * @author 蝉鸣
     */
    public static void setTenantLevelByName(Long accountId,Result<OrgLevelBO> orgResult) {
        OrgLevelBO orgLevelBO = orgResult.getData();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_TENANT_LEVEL_NAME));
        cacheOptional.ifPresent(cache -> cache.put(accountId.toString().concat(orgLevelBO.getLevelName()), orgLevelBO));
    }

    /**
     * 功能描述:
     * 〈获取系统组织缓存:主要用于Excel导入根据名称解析数据〉
     * @param levelName levelName
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static OrgLevelBO getTenantLevelByName(String levelName) {
        Long accountId = Objects.requireNonNull(getLoginUser()).getAccountId();
        OrgLevelBO orgLevelBO = null;
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_TENANT_LEVEL_NAME));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(accountId.toString().concat(levelName)));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof OrgLevelBO level){
                //重置key有效时间
                resetExpire(CacheConstants.SYS_TENANT_LEVEL_NAME, accountId.toString().concat(levelName));
                return level;
            }else{
                cache.evictIfPresent(accountId.toString().concat(levelName));
            }
        }
        if(ObjectUtil.isEmpty(orgLevelBO)){
            //重新请求
            Result<OrgLevelBO> levelResult = remoteOrgMemberService.getOrgLevelByName(levelName);
            Optional<OrgLevelBO> levelBO = ResultUtil.of(levelResult).getData();
            if (levelBO.isPresent()) {
                orgLevelBO = levelBO.get();
                //设置缓存
                setTenantLevelByName(accountId,levelResult);
            }
        }
        return orgLevelBO;
    }

    /**
     * 功能描述:
     * 〈设置系统组织缓存〉
     * @param memberResult memberResult
     * @author 蝉鸣
     */
    public static void setTenantMemberInfoCache(Long accountId, Result<OrgMemberBO> memberResult) {
        OrgMemberBO orgMemberBO = memberResult.getData();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_TENANT_MEMBER_NAME));
        cacheOptional.ifPresent(cache -> cache.put(accountId.toString().concat(orgMemberBO.getMemberName()), orgMemberBO));
    }

    /**
     * 功能描述:
     * 〈获取系统组织缓存〉
     * @param memberName memberName
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static OrgMemberBO getTenantMemberByName(String memberName) {
        Long accountId = Objects.requireNonNull(getLoginUser()).getAccountId();
        OrgMemberBO orgMemberBO = null;
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_TENANT_MEMBER_NAME));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(accountId.toString().concat(memberName)));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof OrgMemberBO member){
                //重置key有效时间
                resetExpire(CacheConstants.SYS_TENANT_MEMBER_NAME, accountId.toString().concat(memberName));
                return member;
            }else{
                cache.evictIfPresent(accountId.toString().concat(memberName));
            }
        }
        if(ObjectUtil.isEmpty(orgMemberBO)){
            //重新请求
            Result<OrgMemberBO> memberResult = remoteOrgMemberService.getOrgMemberByName(memberName);
            Optional<OrgMemberBO> memberBO = ResultUtil.of(memberResult).getData();
            if (memberBO.isPresent()) {
                orgMemberBO = memberBO.get();
                //设置缓存
                setTenantMemberInfoCache(accountId,memberResult);
            }
        }
        return orgMemberBO;
    }


    /**
     * 功能描述:
     * 〈设置系统组织缓存〉
     * @param memberResult memberResult
     * @author 蝉鸣
     */
    public static void setMemberByUserInfoCache(Result<OrgMemberRelBO> memberResult) {
        OrgMemberRelBO orgMemberBO = memberResult.getData();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_TENANT_MEMBER_NAME));
        cacheOptional.ifPresent(cache -> cache.put(orgMemberBO.getUserId(), orgMemberBO));
    }

    /**
     * 功能描述:
     * 〈获取系统成员缓存〉
     * @param userId userId
     * @return 正常返回:{@link OrgMemberBO}
     * @author 蝉鸣
     */
    public static OrgMemberRelBO getMemberByUserId(Long userId) {
        OrgMemberRelBO orgMemberBO = null;
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_TENANT_MEMBER_NAME));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(userId));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof OrgMemberRelBO member){
                //重置key有效时间
                resetExpire(CacheConstants.SYS_TENANT_MEMBER_NAME, userId);
                return member;
            }else{
                cache.evictIfPresent(userId);
            }
        }
        if(ObjectUtil.isEmpty(orgMemberBO)){
            //重新请求
            Result<OrgMemberRelBO> memberResult = remoteOrgMemberService.getOrgMemberUserDefaultRelByUserId(userId);
            Optional<OrgMemberRelBO> memberBO = ResultUtil.of(memberResult).getData();
            if (memberBO.isPresent()) {
                orgMemberBO = memberBO.get();
                //设置缓存
                setMemberByUserInfoCache(memberResult);
            }
        }
        return orgMemberBO;
    }

    /**
     * 功能描述:
     * 〈设置用户缓存〉
     * @param userId userId
     * @author 蝉鸣
     */
    public static <T> void setUserPrefixCache(Long userId,String prefix,Object object) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(prefix));
        cacheOptional.ifPresent(cache -> cache.put(userId, object));
    }

    /**
     * 功能描述:
     * 〈获取用户缓存〉
     * @param userId userId
     * @return 正常返回:{@link SysAccountInfoBO}
     * @author 蝉鸣
     */
    public static <T> T getUserPrefixCache(Long userId,String prefix,Class<T>clazz) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(prefix));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<T> optional = Optional.ofNullable(cache.get(userId, clazz));
            if(optional.isPresent()){
                return optional.get();
            }
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈缓存是否存在〉
     * @param prefix prefix
     * @param dataKey dataKey
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public static Boolean getExistsCache(String prefix,Object dataKey) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(prefix));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(dataKey));
            if(valueWrapper.isPresent()){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 功能描述:
     * 〈清除带有前缀的数据key缓存〉
     * @param prefix prefix
     * @param dataKey dataKey
     * @author 蝉鸣
     */
    public static void delDataPrefixCache(String prefix,Object dataKey) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(prefix));
        cacheOptional.ifPresent(cache -> cache.evictIfPresent(dataKey));
    }

    /**
     * 功能描述:
     * 〈清除包含前缀的缓存〉
     * @param prefix prefix
     * @author 蝉鸣
     */
    public static void delPrefixCache(String prefix) {
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(prefix));
        cacheOptional.ifPresent(Cache::clear);
    }

    /**
     * 功能描述:
     * 〈重置key过期时间，默认2小时〉
     * @param key key
     * @author 蝉鸣
     */
    public static void resetExpire(String prefix,Object key) {
        //重置key有效时间
        RedissonUtil.expire(prefix.concat(SymbolConst.COLON).concat(key.toString()), Duration.ofHours(NumberConst.NUM_2));
    }

    /**
     * 功能描述:
     * 〈查询是否当前租户管理员〉
     * @author 蝉鸣
     */
    public static Boolean isTenantAdmin() {
        //先模拟返回
        LoginUserBO loginUser = getLoginUser();
        if(ObjectUtil.isNull(loginUser)){
            return Boolean.FALSE;
        }
        Long accountId = loginUser.getAccountId();
        SysAccountBO accountBO = getAccountInfoCache(accountId);
        if(ObjectUtil.isNull(accountBO)){
            return Boolean.FALSE;
        }
        List<SysRoleBO> accountRoleCache = getAccountRoleCache(accountId);
        if(ObjectUtil.isNull(accountBO)){
            return Boolean.FALSE;
        }
        //如果当前账户拥有初始化角色则是为管理员
        long count = accountRoleCache.stream().filter(role -> ObjectUtil.isNotNull(role.getInitFlag()) && role.getInitFlag().equals(YesOrNoEnum.YES.getValue())).count();
        if(count > NumberConst.NUM_0.longValue()){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 功能描述:
     * 〈查询租户具有哪些应用模块:避免一些初始化数据〉
     * @author 蝉鸣
     */
    public static List<Long> getAppModules() {
        List<Long> appModuleIds = CollUtil.newArrayList();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_APP_MODULE));
        if(cacheOptional.isPresent()){
            Cache cache = cacheOptional.get();
            Optional<Cache.ValueWrapper> valueWrapper = Optional.ofNullable(cache.get(CacheConstants.SYS_APP_MODULE));
            if(valueWrapper.isPresent() && valueWrapper.get().get() instanceof List<?> moduleIds){
                //重置key有效时间
                resetExpire(CacheConstants.SYS_APP_MODULE, CacheConstants.SYS_APP_MODULE);
                return moduleIds.stream().filter(ObjectUtil::isNotEmpty).map(id->Long.parseLong(id.toString())).toList();
            }else{
                cache.evictIfPresent(CacheConstants.SYS_APP_MODULE);
            }
        }
        if(ObjectUtil.isEmpty(appModuleIds)){
            //重新请求
            Result<List<Long>> moduleResult = remoteUserService.getAppModules();
            Optional<List<Long>> module = ResultUtil.of(moduleResult).getData();
            if (module.isPresent()) {
                appModuleIds = module.get();
                //设置缓存
                setAppModules(moduleResult);
            }
        }
        return appModuleIds;
    }

    /**
     * 功能描述:
     * 〈设置租户应用缓存〉
     * @param moduleResult moduleResult
     * @author 蝉鸣
     */
    public static void setAppModules(Result<List<Long>> moduleResult) {
        List<Long> moduleIds = moduleResult.getData();
        Optional<Cache> cacheOptional = Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_APP_MODULE));
        cacheOptional.ifPresent(cache -> cache.put(CacheConstants.SYS_APP_MODULE, moduleIds));
    }

    /**
     * 功能描述:
     * 〈删除应用模块缓存〉
     * @author 蝉鸣
     */
    public static void delAppModules() {
        Optional.ofNullable(cacheManager.getCache(CacheConstants.SYS_APP_MODULE))
                .ifPresent(cache -> cache.evictIfPresent(CacheConstants.SYS_APP_MODULE));
    }

}
