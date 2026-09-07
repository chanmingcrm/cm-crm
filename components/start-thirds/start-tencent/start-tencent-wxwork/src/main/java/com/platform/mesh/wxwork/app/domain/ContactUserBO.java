package com.platform.mesh.wxwork.app.domain;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 企微客户联系人对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ContactUserBO extends BaseBO {

    /**
     * 外部联系人的userid
     */
    private String externalUserid;

    /**
     * 外部联系人的名称
     */
    private String name;

    /**
     * 外部联系人的类型:1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户
     */
    private Integer type;

    /**
     * 外部联系人头像
     */
    private String avatar;

    /**
     * 外部联系人性别 0-未知 1-男性 2-女性。第三方应用和代开发应用均不可获取，统一返回0。上游企业不可获取下游企业客户该字段，返回值为0，表示未定义
     */
    private Integer gender;

}
