package com.platform.mesh.wxwork.app.domain;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 企微客户联系人对象
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FollowUserBO extends BaseBO {

    /**
     * 添加了此外部联系人的企业成员userid
     */
    private String userid;

    /**
     * 该成员对此外部联系人的备注
     */
    private String remark;

    /**
     * 图片路径
     */
    private String description;

    /**
     * 该成员添加此外部联系人的时间
     */
    private Integer createtime;

    /**
     * 该成员添加此外部联系人所打企业标签的id
     */
    private List<String> tagId;

    /**
     * 该成员对此客户备注的手机号码
     */
    private List<String> remarkMobiles;

    /**
     * 该成员添加此客户的来源
     */
    private Integer addWay;

    /**
     * 发起添加的userid
     */
    private String operUserid;

}
