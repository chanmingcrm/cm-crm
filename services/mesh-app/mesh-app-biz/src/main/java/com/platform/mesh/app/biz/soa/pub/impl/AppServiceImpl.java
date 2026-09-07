package com.platform.mesh.app.biz.soa.pub.impl;


import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.service.IAppFormColumnSetProcessService;
import com.platform.mesh.core.constants.ServiceNameConst;
import com.platform.mesh.upms.api.pub.upms.UpmsFeedbackService;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 客户审批回调
 * @author 蝉鸣
 */
@Service
public class AppServiceImpl implements UpmsFeedbackService {


    @Autowired
    private IAppFormColumnSetProcessService appFormColumnSetProcessService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return ServiceNameConst.APP_SERVICE;
    }

    /**
     * 功能描述:
     * 〈添加回调处理〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    @Override
    public void transOrgData(MsgUpmsBO msgUpmsBO){
        System.out.println("应用执行");
    }

}
