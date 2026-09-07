package com.platform.mesh.wxwork.app.domain;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 企微JS签名对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class JsSignBO extends BaseBO {

    /**
     * 凭证
     */
    private String ticket;

    /**
     * 随机字符串
     */
    private String nonceStr;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 签名
     */
    private String sign;

}
