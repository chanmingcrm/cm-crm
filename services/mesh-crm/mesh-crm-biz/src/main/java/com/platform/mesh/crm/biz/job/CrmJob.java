package com.platform.mesh.crm.biz.job;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.init.db.service.ICrmDbService;
import com.platform.mesh.crm.biz.modules.crm.predrainage.service.ICrmPreDrainageService;
import com.platform.mesh.upms.api.modules.conf.domian.bo.ConfSysSetBO;
import com.platform.mesh.upms.api.modules.conf.enums.ConfSourceEnum;
import com.platform.mesh.upms.api.modules.conf.feign.RemoteConfService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 数据转化定时任务调度服务
 * @author 蝉鸣
 */
@Component
public class CrmJob {

    private static final Logger log = LoggerFactory.getLogger(CrmJob.class);

    @Autowired
    private ICrmDbService dbService;

    @Autowired
    private RemoteConfService remoteConfService;

    @Autowired
    private ICrmPreDrainageService crmPreDrainageService;

    /**
     * 定时数据转化
     */
    @XxlJob("crmTransDataJobHandler")
    public void crmTransDataJobHandler() throws Exception {
        log.info(">>>数据转化定时任务开始执行！！！");
        XxlJobHelper.log(">>>数据转化定时任务开始执行！！！");
        dbService.transDbData();
    }

    /**
     * 同步抖音线索数据
     */
    @XxlJob("crmSyncDouYinJobHandler")
    public void crmSyncDouYinJobHandler() throws Exception {
        XxlJobHelper.log(">>>同步抖音线索数据任务开始执行！！！");
        //查询所有的配置信息
        List<ConfSysSetBO> sysSetBOS = remoteConfService.selectList(ConfSourceEnum.DOU_YIN.getValue()).getData();
        if(CollUtil.isEmpty(sysSetBOS)){
            return;
        }
        //同步当前配置下的线索信息
        for (ConfSysSetBO sysSetBO : sysSetBOS) {
            crmPreDrainageService.syncDouYinClue(sysSetBO);
        }
    }

    /**
     * 同步企微客户线索数据
     */
    @XxlJob("crmSyncWxWorkJobHandler")
    public void crmSyncWxWorkJobHandler() throws Exception {
        XxlJobHelper.log(">>>同步企微客户线索数据任务开始执行！！！");
        //查询所有的配置信息
        List<ConfSysSetBO> sysSetBOS = remoteConfService.selectList(ConfSourceEnum.WX_WORK.getValue()).getData();
        if(CollUtil.isEmpty(sysSetBOS)){
            return;
        }
        //同步当前配置下的线索信息
        for (ConfSysSetBO sysSetBO : sysSetBOS) {
            crmPreDrainageService.syncWxWorkContact(sysSetBO);
        }
    }

}
