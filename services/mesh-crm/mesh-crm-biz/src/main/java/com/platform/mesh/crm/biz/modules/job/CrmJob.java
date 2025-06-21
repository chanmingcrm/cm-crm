package com.platform.mesh.crm.biz.modules.job;

import com.platform.mesh.crm.biz.modules.init.db.service.ICrmDbService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description 数据转化定时任务调度服务
 * @author 蝉鸣
 */
@Component
public class CrmJob {

    private static final Logger log = LoggerFactory.getLogger(CrmJob.class);

    @Autowired
    private ICrmDbService dbService;

    /**
     * 定时数据转化
     */
    @XxlJob("crmTransDataJobHandler")
    public void msgNoticeHandleJobHandler() throws Exception {
        log.info(">>>数据转化定时任务开始执行！！！");
        XxlJobHelper.log(">>>数据转化定时任务开始执行！！！");
        dbService.transDbData();
    }

}
