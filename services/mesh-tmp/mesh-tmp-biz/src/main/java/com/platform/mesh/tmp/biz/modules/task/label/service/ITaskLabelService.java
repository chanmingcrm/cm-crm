package com.platform.mesh.tmp.biz.modules.task.label.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.label.domain.dto.TaskLabelDTO;
import com.platform.mesh.tmp.biz.modules.task.label.domain.po.TaskLabel;
import com.platform.mesh.tmp.biz.modules.task.label.domain.vo.TaskLabelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务标签信息
 * @author 蝉鸣
 */
public interface ITaskLabelService extends IService<TaskLabel> {


    /**
     * 功能描述:
     * 〈获取当前任务标签信息〉
     * @param labelId labelId
     * @return 正常返回:{@link TaskLabelVO}
     * @author 蝉鸣
     */
    TaskLabelVO getLabelInfoById(Long labelId);

    /**
     * 功能描述:
     * 〈新增任务标签〉
     * @param labelDTO labelDTO
     * @return 正常返回:{@link TaskLabelVO}
     * @author 蝉鸣
     */
    TaskLabelVO addLabel(TaskLabelDTO labelDTO);

    /**
     * 功能描述:
     * 〈修改任务标签〉
     * @param labelDTO labelDTO
     * @return 正常返回:{@link TaskLabelVO}
     * @author 蝉鸣
     */
    TaskLabelVO editLabel(TaskLabelDTO labelDTO);

    /**
     * 功能描述:
     * 〈删除任务标签〉
     * @param labelId labelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteLabel(Long labelId);
}