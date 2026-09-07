package com.platform.mesh.wxwork.msg.domain;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 企微机器人消息对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class NewsMsgBO extends BaseBO {

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * url路径
     */
    private String url;

    /**
     * 图片路径
     */
    private String picUrl;

}
