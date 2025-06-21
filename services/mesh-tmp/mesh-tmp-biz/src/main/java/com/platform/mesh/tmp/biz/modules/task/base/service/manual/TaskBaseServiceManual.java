package com.platform.mesh.tmp.biz.modules.task.base.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.tmp.biz.modules.task.basedata.domain.po.TaskBaseData;
import com.platform.mesh.tmp.biz.modules.task.basedata.service.ITaskBaseDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务
 * @author 蝉鸣
 */
@Service
public class TaskBaseServiceManual {

    private final static Logger log = LoggerFactory.getLogger(TaskBaseServiceManual.class);

    @Autowired
    private ITaskBaseDataService taskBaseDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param taskBaseDataList taskBaseDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<TaskBaseData> taskBaseDataList) {
        if(CollUtil.isEmpty(taskBaseDataList)){
            return;
        }
        //批量新增信息
        taskBaseDataService.saveBatch(taskBaseDataList);
    }

    /**
     * 功能描述:
     * 〈DB Data 数据批量修改〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //查询已经存在的新增数据
        List<TaskBaseData> onBusinessDataList = taskBaseDataService.lambdaQuery().eq(TaskBaseData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(TaskBaseData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onBusinessDataList)) {
            return;
        }
        AppUtil.editDbData(onBusinessDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(onBusinessDataList)){
            return;
        }
        taskBaseDataService.updateBatchById(onBusinessDataList);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    public void transDbDataBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        if(CollUtil.isEmpty(dataIds) || ObjectUtil.isEmpty(scopeUserId) || ObjectUtil.isEmpty(scopeOrgId)) {
            return;
        }
        taskBaseDataService.lambdaUpdate()
                .set(TaskBaseData::getScopeUserId, scopeUserId)
                .set(TaskBaseData::getScopeOrgId, scopeOrgId)
                .in(TaskBaseData::getDataId, dataIds)
                .update();
    }
}