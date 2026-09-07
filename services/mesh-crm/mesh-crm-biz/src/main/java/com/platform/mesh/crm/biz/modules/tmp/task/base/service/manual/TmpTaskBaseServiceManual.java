package com.platform.mesh.crm.biz.modules.tmp.task.base.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.tmp.task.base.domain.po.TmpTaskBase;
import com.platform.mesh.crm.biz.modules.tmp.task.basedata.domain.po.TmpTaskBaseData;
import com.platform.mesh.crm.biz.modules.tmp.task.basedata.service.ITmpTaskBaseDataService;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelDTO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.po.TmpTaskBaseRel;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.service.ITmpTaskBaseRelService;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
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
public class TmpTaskBaseServiceManual {

    private final static Logger log = LoggerFactory.getLogger(TmpTaskBaseServiceManual.class);

    @Autowired
    private ITmpTaskBaseDataService taskBaseDataService;

    @Autowired
    private ITmpTaskBaseRelService taskDataRelService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param tmpTaskBaseDataList taskBaseDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<TmpTaskBaseData> tmpTaskBaseDataList) {
        if(CollUtil.isEmpty(tmpTaskBaseDataList)){
            return;
        }
        TmpTaskBaseData data = CollUtil.getFirst(tmpTaskBaseDataList);
        //删除旧数据
        taskBaseDataService.lambdaUpdate().eq(TmpTaskBaseData::getDataId,data.getDataId()).remove();
        //批量新增信息
        taskBaseDataService.saveBatch(tmpTaskBaseDataList);
    }

    /**
     * 功能描述:
     * 〈增加关联任务关系〉
     * @param tmpTaskBase taskBase
     * @param relDTOList relDTOList
     * @author 蝉鸣
     */
    public void addTaskBaseAndRel(TmpTaskBase tmpTaskBase, List<TmpTaskBaseRelDTO> relDTOList) {
        if(CollUtil.isEmpty(relDTOList)){
            return;
        }
        List<TmpTaskBaseRel> relList = CollUtil.newArrayList();
        relDTOList.forEach(relDTO -> {
            TmpTaskBaseRel tmpTaskBaseRel = new TmpTaskBaseRel();
            BeanUtil.copyProperties(tmpTaskBase, tmpTaskBaseRel, ObjFieldUtil.ignoreDefault());
            tmpTaskBaseRel.setTaskId(tmpTaskBase.getId());
            tmpTaskBaseRel.setRelModuleId(relDTO.getRelModuleId());
            tmpTaskBaseRel.setRelDataId(relDTO.getRelDataId());
            relList.add(tmpTaskBaseRel);
        });
        taskDataRelService.saveBatch(relList);
    }
}