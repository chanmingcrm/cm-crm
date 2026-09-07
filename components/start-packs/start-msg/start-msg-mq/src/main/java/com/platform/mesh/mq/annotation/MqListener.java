package com.platform.mesh.mq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 * 〈声明业务主题监听方法〉
 * @author 蝉鸣
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MqListener {
    /**
     * 功能描述:
     * 〈获取监听的业务主题〉
     * @return 业务主题
     * @author 蝉鸣
     */
    String topic();
}
