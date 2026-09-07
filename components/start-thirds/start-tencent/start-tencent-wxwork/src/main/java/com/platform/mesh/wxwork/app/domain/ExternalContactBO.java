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
public class ExternalContactBO extends BaseBO {


    /**
     * 联系人
     */
    private ContactUserBO externalContact;

    /**
     * 跟进人
     */
    private FollowUserBO followInfo;

}
