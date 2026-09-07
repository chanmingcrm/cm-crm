package com.platform.mesh.app.api.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.pub.type.app.domain.bo.MsgAppBO;

public interface IAppDataService<T extends AppDataPO> extends IService<T> {

    /**
     * 功能描述:
     * 〈同步名称〉
     * @param msgAppBO msgAppBO
     * @author 蝉鸣
     */
    void syncName(MsgAppBO msgAppBO);

}
