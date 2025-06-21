package com.platform.mesh.tmp.biz.modules.task.labelrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.dto.TaskLabelRelDTO;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.po.TaskLabelRel;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.vo.TaskLabelRelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务标签关系信息
 * @author 蝉鸣
 */
public interface ITaskLabelRelService extends IService<TaskLabelRel> {


    /**
     * 功能描述:
     * 〈获取当前任务标签关系信息〉
     * @param labelRelId labelRelId
     * @return 正常返回:{@link TaskLabelRelVO}
     * @author 蝉鸣
     */
    TaskLabelRelVO getLabelRelInfoById(Long labelRelId);

    /**
     * 功能描述:
     * 〈新增任务标签关系〉
     * @param labelRelDTO labelRelDTO
     * @return 正常返回:{@link TaskLabelRelVO}
     * @author 蝉鸣
     */
    TaskLabelRelVO addLabelRel(TaskLabelRelDTO labelRelDTO);

    /**
     * 功能描述:
     * 〈修改任务标签关系〉
     * @param labelRelDTO labelRelDTO
     * @return 正常返回:{@link TaskLabelRelVO}
     * @author 蝉鸣
     */
    TaskLabelRelVO editLabelRel(TaskLabelRelDTO labelRelDTO);

    /**
     * 功能描述:
     * 〈删除任务标签关系〉
     * @param labelRelId labelRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteLabelRel(Long labelRelId);
}