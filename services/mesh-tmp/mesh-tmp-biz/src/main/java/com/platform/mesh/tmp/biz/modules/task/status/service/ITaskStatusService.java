package com.platform.mesh.tmp.biz.modules.task.status.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.status.domain.dto.TaskStatusDTO;
import com.platform.mesh.tmp.biz.modules.task.status.domain.po.TaskStatus;
import com.platform.mesh.tmp.biz.modules.task.status.domain.vo.TaskStatusVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务状态信息
 * @author 蝉鸣
 */
public interface ITaskStatusService extends IService<TaskStatus> {


    /**
     * 功能描述:
     * 〈获取当前任务状态信息〉
     * @param statusId statusId
     * @return 正常返回:{@link TaskStatusVO}
     * @author 蝉鸣
     */
    TaskStatusVO getStatusInfoById(Long statusId);

    /**
     * 功能描述:
     * 〈新增任务状态〉
     * @param statusDTO statusDTO
     * @return 正常返回:{@link TaskStatusVO}
     * @author 蝉鸣
     */
    TaskStatusVO addStatus(TaskStatusDTO statusDTO);

    /**
     * 功能描述:
     * 〈修改任务状态〉
     * @param statusDTO statusDTO
     * @return 正常返回:{@link TaskStatusVO}
     * @author 蝉鸣
     */
    TaskStatusVO editStatus(TaskStatusDTO statusDTO);

    /**
     * 功能描述:
     * 〈删除任务状态〉
     * @param statusId statusId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteStatus(Long statusId);
}