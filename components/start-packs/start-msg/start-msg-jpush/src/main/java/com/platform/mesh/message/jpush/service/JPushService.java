package com.platform.mesh.message.jpush.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.jiguang.sdk.api.PushApi;
import cn.jiguang.sdk.bean.push.PushSendParam;
import cn.jiguang.sdk.bean.push.PushSendResult;
import cn.jiguang.sdk.bean.push.audience.Audience;
import cn.jiguang.sdk.bean.push.callback.Callback;
import cn.jiguang.sdk.bean.push.message.inapp.InAppMessage;
import cn.jiguang.sdk.bean.push.message.notification.NotificationMessage;
import cn.jiguang.sdk.bean.push.options.Options;
import cn.jiguang.sdk.constants.ApiConstants;
import cn.jiguang.sdk.exception.ApiErrorException;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.message.jpush.constant.JPushConst;
import com.platform.mesh.message.jpush.domain.bo.JPushBO;
import com.platform.mesh.message.jpush.properties.JPushProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 极光推送
 * @author 蝉鸣
 */
@Service
public class JPushService {

    private static final Logger logger = LoggerFactory.getLogger(JPushService.class);

    private final JPushProperties jPushProperties;
    private final PushApi pushApi;

    /**
     * 构造器注入，更推荐的方式
     */
    @Autowired(required = false)
    public JPushService(JPushProperties jPushProperties,
                        PushApi pushApi) {
        this.jPushProperties = jPushProperties;
        this.pushApi = pushApi;
    }

    /**
     * 功能描述:
     * 推送消息给指定用户(支持安卓和IOS)
     * 优先基于注册ID推送,一次推送最多 1000 个
     * @param jPushBO 推送信息封装对象，包含推送所需的各种参数
     * @author 蝉鸣
     */
    public void send(JPushBO jPushBO) {
        // 设置推送参数
        PushSendParam param = new PushSendParam();
        // 构建统一的通知消息对象
        param.setNotification(this.buildNotificationMessage(jPushBO));
        // 构建消息类型
        param.setInApp(this.buildInAppMessage(jPushBO));
        // 构建目标人群
        param.setAudience(this.buildAudience(jPushBO));
        // 配置推送选项，如推送触发事件
        param.setOptions(this.buildOptions(jPushBO));
        // 配置回调
        param.setCallback(this.buildCallback(jPushBO));
        // 设置推送目标平台为全平台
        param.setPlatform(ApiConstants.Platform.ALL);
        try {
            if(ObjectUtil.isNotNull(pushApi)){
                PushSendResult result = pushApi.send(param);
                logger.info("[极光推送] 推送结果: {}", JSONUtil.toJsonStr(result));
            }
        } catch (ApiErrorException e) {
            logger.error("[极光推送] 推送异常: {}",JSONUtil.toJsonStr(e.getApiError().getError()));
        }
    }

    /**
     * 构建Audience对象(优先基于注册ID推送)
     * 此方法根据PushVo对象中的信息来构造Audience对象，主要用于设置目标受众
     * 如果存在registrationIdList，则设置为基于注册ID的推送；如果存在aliasList，则设置为基于别名的推送
     * @param jPushBO 包含推送相关信息的vo对象，用于获取受众信息
     * @return 返回构建好的Audience对象
     */
    private Object buildAudience(JPushBO jPushBO) {
        if (jPushBO.getIsAll()) {
            // 默认设置为全部受众(广播模式)
            return ApiConstants.Audience.ALL;
        }
        //定向模式
        Audience audience = new Audience();
        // 检查是否存在设备ID列表
        if (CollUtil.isNotEmpty(jPushBO.getRegistIdList())) {
            audience.setRegistrationIdList(jPushBO.getRegistIdList());
        } else if (CollUtil.isNotEmpty(jPushBO.getAliasList())) {
            // 如果没有设备ID列表，检查是否存在别名列表
            audience.setAliasList(jPushBO.getAliasList());
        }
        return audience;
    }


    /**
     * 构建统一的通知消息对象，包含 Android 和 iOS 的通知样式
     * @param jPushBO 推送信息封装对象，包含推送所需的各种参数
     * @return 返回构建好的通知消息对象
     */
    private NotificationMessage buildNotificationMessage(JPushBO jPushBO) {
        // 构建 Android 通知内容
        NotificationMessage.Android android = buildAndroidNotification(jPushBO);
        // 构建 iOS 通知内容
        NotificationMessage.IOS ios = buildIosNotification(jPushBO);
        // 构建 HMOS 通知内容
        NotificationMessage.HMOS hmos = buildHMosNotification(jPushBO);
        // 绑定到统一的通知消息对象中
        NotificationMessage notification = new NotificationMessage();
        notification.setIos(ios);
        notification.setAndroid(android);
        notification.setHmos(hmos);
        return notification;
    }

    /**
     * 构建 iOS 通知
     * @param jPushBO 通知标题
     * @return 返回构建好的 iOS 通知对象
     */
    private NotificationMessage.IOS buildIosNotification(JPushBO jPushBO) {
        // 构建 iOS 通知内容
        NotificationMessage.IOS ios = new NotificationMessage.IOS();
        Map<String, String> alert = new HashMap<>();
        alert.put(JPushConst.ALERT_TITLE, jPushBO.getTitle());
        alert.put(JPushConst.ALERT_CONTENT, jPushBO.getContent());
        // 支持标题+副标题的富文本展示
        ios.setAlert(alert);
        // 应用角标
        ios.setBadge(jPushBO.getBadge());
        // 推送唤醒
        ios.setContentAvailable(Boolean.FALSE);
        // 通知优先级和交付时间的中断级别,取值只能是 active,critical,passive,time-sensitive 中的一个
        ios.setInterruptionLevel("active");
        // 通知扩展
        ios.setMutableContent(Boolean.TRUE);
        // 通知提示声音或警告通知
        ios.setSound("default");
        // 通知分组
//        ios.setThreadId("default");
        // 附加参数
        ios.setExtras(filterSensitiveExtend(jPushBO.getExtendMap()));

        return ios;
    }

    /**
     * 构建 Android 通知
     * @param jPushBO 通知标题
     * @return 返回构建好的 Android 通知对象
     */
    private NotificationMessage.Android buildAndroidNotification(JPushBO jPushBO) {
        // Android Intent（点击通知打开指定页面,这里默认打开首页）
        NotificationMessage.Android.Intent intent = new NotificationMessage.Android.Intent();
        intent.setUrl(StrUtil.EMPTY);
        // 构建 Android 通知内容:alert必须非空
        NotificationMessage.Android android = new NotificationMessage.Android();
        if(StrUtil.isBlank(jPushBO.getContent())){
            android.setAlert(jPushBO.getTitle());
        }else{
            android.setAlert(jPushBO.getContent());
        }
        android.setTitle(jPushBO.getTitle());
        android.setStyle(NumberConst.NUM_0);
        android.setBuilderId(NumberConst.NUM_0);
        android.setPriority(NumberConst.NUM_0);
        android.setAlertType(NumberConst.NUM_7);
        android.setCategory(JPushConst.ALERT_CATEGORY);
        android.setIntent(intent);
        if(NumberUtil.isNumber(jPushBO.getBadge())){
            android.setBadgeSetNumber(Integer.getInteger(jPushBO.getBadge()));
        }
        // 附加参数
        android.setExtras(filterSensitiveExtend(jPushBO.getExtendMap()));

        return android;
    }

    /**
     * 构建 HMOS 通知
     * @param jPushBO 通知标题
     * @return 返回构建好的 HMOS 通知对象
     */
    private NotificationMessage.HMOS buildHMosNotification(JPushBO jPushBO) {
        // HMOS Intent（点击通知打开指定页面,这里默认打开首页）
        NotificationMessage.HMOS.Intent hmIntent = new NotificationMessage.HMOS.Intent();
        hmIntent.setUrl(JPushConst.HMOS_URL);
        // 构建 HMOS 通知内容
        NotificationMessage.HMOS hmos = new NotificationMessage.HMOS();
        if(StrUtil.isBlank(jPushBO.getContent())){
            hmos.setAlert(jPushBO.getTitle());
        }else{
            hmos.setAlert(jPushBO.getContent());
        }
        hmos.setTitle(jPushBO.getTitle());
        hmos.setIntent(hmIntent);
        if(NumberUtil.isNumber(jPushBO.getBadge())){
            hmos.setBadgeSetNumber(Integer.getInteger(jPushBO.getBadge()));
        }
        // 附加参数
        hmos.setExtras(filterSensitiveExtend(jPushBO.getExtendMap()));

        return hmos;
    }

    /**
     * 面向于通知栏消息类型，需搭配notification参数一起使用，对于通知权限关闭的用户可设置启用此功能。
     * 不可与 message 同时并存
     * @return Map<String, Object>
     */
    private InAppMessage buildInAppMessage(JPushBO jPushBO) {
        InAppMessage inAppMessage = new InAppMessage();
        inAppMessage.setInAppMessage(Boolean.FALSE);
        return inAppMessage;
    }

    /**
     * 构建 推送配置项
     * @return Callback
     */
    private Options buildOptions(JPushBO jPushBO) {
        Options options = new Options();
        // IOS推送 Notification 时需要在 options 中通过 apns_production 字段来设定推送环境。
        // True 表示推送生产环境，False 表示要推送开发环境;如果不指定则为推送生产环境；一次只能推送给一个环境。
        if(jPushProperties.getDev()){
            options.setApnsProduction(Boolean.FALSE);
        }else{
            options.setApnsProduction(Boolean.TRUE);
        }
        //推送请求下发通道,VIP才能使用
        options.setThirdPartyChannel(buildThirdPartyChannel());
        return options;
    }

    /**
     * 构建 回调
     * @return Callback
     */
    private Callback buildCallback(JPushBO jPushBO) {
        Callback callback = new Callback();
        callback.setParams(jPushBO.getExtendMap());
        return callback;
    }

    /**
     * 构建 第三方通道 通知
     * @return Map<String, Object>
     */
    private Map<String, Object> buildThirdPartyChannel() {
        Map<String, Object> thirdPartyMap = new HashMap<>();

        //华为通道
//        Map<String, Object> huaweiMap = new HashMap<>();
//        huaweiMap.put("distribution", "ospush");
//        huaweiMap.put("importance", "NORMAL");
//        huaweiMap.put("category", "MARKETING");
//        thirdPartyMap.put("huawei", huaweiMap);

        return thirdPartyMap;
    }

    /**
     * 过滤掉敏感字段（示例）
     * @param param 原始参数映射，可能包含敏感信息
     * @return 返回一个过滤掉敏感字段后的参数映射
     */
    private Map<String, Object> filterSensitiveExtend(Map<String, Object> param) {
        if (ObjectUtil.isEmpty(param)) {
            return new HashMap<>();
        }
        for (String field : JPushConst.IGNORE_FIELD) {
            param.remove(field);
        }
        return param;
    }

}
