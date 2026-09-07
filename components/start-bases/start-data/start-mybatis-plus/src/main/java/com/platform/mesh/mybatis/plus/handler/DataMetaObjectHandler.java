package com.platform.mesh.mybatis.plus.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.enums.MateFillEnum;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import lombok.AllArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @description 数据填充处理
 * @author 蝉鸣
 */
@Component
@AllArgsConstructor
public class DataMetaObjectHandler implements MetaObjectHandler {


    /**
     * 线程隔离，避免多线程数据冲突
     */
    private static final ThreadLocal<Boolean> ENABLE_DATA_META = new ThreadLocal<>();

    /**
     * 开启数据填充设定
     * @param enable enable
     */
    public static void setEnableDataMeta(Boolean enable) {
        ENABLE_DATA_META.set(enable);
    }

    /**
     * 清除数据填充设定
     */
    public static void unEnableDataMeta() {
        ENABLE_DATA_META.remove();
    }


    @Override
    public void insertFill(MetaObject metaObject) {
        //未开启数据填充
        if(ObjectUtil.isNotEmpty(ENABLE_DATA_META.get()) && !ENABLE_DATA_META.get()){
            return;
        }
        //注入时间
        LocalDateTime now = LocalDateTime.now();
        if (emptyValue(metaObject,MateFillEnum.CREATE_TIME.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.CREATE_TIME.getCode(), LocalDateTime.class,now);
        }
        if (emptyValue(metaObject,MateFillEnum.UPDATE_TIME.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.UPDATE_TIME.getCode(), LocalDateTime.class,now);
        }
        if (emptyValue(metaObject,MateFillEnum.DEL_FLAG.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.DEL_FLAG.getCode(), Integer.class, YesOrNoEnum.YES.getValue());
        }
        //注入用户信息
        LoginUserBO loginUser = getLoginUser();
        if(ObjectUtil.isNull(loginUser)){
            if (emptyValue(metaObject,MateFillEnum.SCOPE_USER_ID.getCode())) {
                this.strictInsertFill(metaObject, MateFillEnum.SCOPE_USER_ID.getCode(), Long.class, NumberConst.NUM_0.longValue());
            }
            if (emptyValue(metaObject,MateFillEnum.SCOPE_ORG_ID.getCode())) {
                this.strictInsertFill(metaObject, MateFillEnum.SCOPE_ORG_ID.getCode(), Long.class,NumberConst.NUM_0.longValue());
            }
            return;
        }
        SysAccountBO userAccountCache = UserCacheUtil.getAccountInfoCache(loginUser.getAccountId());
        if (emptyValue(metaObject,MateFillEnum.CREATE_USER_ID.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.CREATE_USER_ID.getCode(), Long.class, loginUser.getUserId());
        }
        if (emptyValue(metaObject,MateFillEnum.CREATE_USER_NAME.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.CREATE_USER_NAME.getCode(), String.class, loginUser.getNickname());
        }
        if (emptyValue(metaObject,MateFillEnum.UPDATE_USER_ID.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.UPDATE_USER_ID.getCode(), Long.class, loginUser.getUserId());
        }
        if (emptyValue(metaObject,MateFillEnum.UPDATE_USER_NAME.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.UPDATE_USER_NAME.getCode(), String.class, loginUser.getNickname());
        }
        if (emptyValue(metaObject,MateFillEnum.SCOPE_USER_ID.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.SCOPE_USER_ID.getCode(), Long.class,getOrEmpty(loginUser.getUserId()));
        }
        if (emptyValue(metaObject,MateFillEnum.SCOPE_ORG_ID.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.SCOPE_ORG_ID.getCode(), Long.class,getOrEmpty(userAccountCache.getScopeOrgId()));
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //注入时间
        if (emptyValue(metaObject,MateFillEnum.UPDATE_TIME.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.UPDATE_TIME.getCode(), LocalDateTime::now, LocalDateTime.class);
        }
        //注入用户信息
        LoginUserBO loginUser = getLoginUser();
        if(ObjectUtil.isNull(loginUser)){
            return;
        }
        if (emptyValue(metaObject,MateFillEnum.UPDATE_USER_ID.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.UPDATE_USER_ID.getCode(), Long.class,getOrEmpty(loginUser.getUserId()));
        }
        if (emptyValue(metaObject,MateFillEnum.UPDATE_USER_NAME.getCode())) {
            this.strictInsertFill(metaObject, MateFillEnum.UPDATE_USER_NAME.getCode(), String.class, loginUser.getNickname());
        }
    }

    /**
     * 功能描述:
     * 〈判断值是否为空〉
     * @param metaObject metaObject
     * @param metaName metaName
     * @author 蝉鸣
     */
    private boolean emptyValue(MetaObject metaObject,String metaName) {
        if(ObjectUtil.isEmpty(metaObject) || ObjectUtil.isEmpty(metaName)){
            return Boolean.FALSE;
        }
        if(ObjectUtil.isEmpty(metaObject.findProperty(metaName,Boolean.TRUE))){
            return Boolean.FALSE;
        }
        Object metaValue = metaObject.getValue(metaName);
        return ObjectUtil.isEmpty(metaValue) || StrUtil.isBlank(metaValue.toString());
    }

    /**
     * 功能描述:
     * 〈获取登录人员〉
     * @author 蝉鸣
     */
    private LoginUserBO getLoginUser() {
        try{
            return UserCacheUtil.getLoginUser();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈获取值〉
     * @author 蝉鸣
     */
    private Long getOrEmpty(Long value) {
        if(ObjectUtil.isEmpty(value)){
            return NumberConst.NUM_0.longValue();
        }
        return value;
    }


}
