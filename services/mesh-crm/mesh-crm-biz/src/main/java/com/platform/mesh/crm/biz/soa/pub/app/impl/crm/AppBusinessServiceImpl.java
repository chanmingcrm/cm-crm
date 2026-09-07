package com.platform.mesh.crm.biz.soa.pub.app.impl.crm;


import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.pub.type.app.AppFeedbackService;
import com.platform.mesh.app.api.modules.pub.type.app.domain.bo.MsgAppBO;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.service.ICrmOnBusinessDataService;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 客户审批回调
 * @author 蝉鸣
 */
@Service
public class AppBusinessServiceImpl implements AppFeedbackService {

    @Autowired
    private ICrmOnBusinessDataService crmOnBusinessDataService;

    /**
     * 功能描述:
     * 〈业务名称〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String actionName(){
        return SqlUtil.getTableName(CrmOnBusiness.class, TableName.class);
    }

    /**
     * 功能描述:
     * 〈同步名称〉
     * @param msgAppBO msgAppBO
     * @author 蝉鸣
     */
    @Override
    public void syncName(MsgAppBO msgAppBO){
        //同步数据名称
        crmOnBusinessDataService.syncName(msgAppBO);
    }

}
