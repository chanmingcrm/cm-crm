package com.platform.mesh.tmp.biz.modules.task.statusrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.dto.TaskStatusRelDTO;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.po.TaskStatusRel;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.vo.TaskStatusRelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务状态关系信息
 * @author 蝉鸣
 */
public interface ITaskStatusRelService extends IService<TaskStatusRel> {


    /**
     * 功能描述:
     * 〈获取当前任务状态关系信息〉
     * @param statusRelId statusRelId
     * @return 正常返回:{@link TaskStatusRelVO}
     * @author 蝉鸣
     */
    TaskStatusRelVO getStatusRelInfoById(Long statusRelId);

    /**
     * 功能描述:
     * 〈新增任务状态关系〉
     * @param statusRelDTO statusRelDTO
     * @return 正常返回:{@link TaskStatusRelVO}
     * @author 蝉鸣
     */
    TaskStatusRelVO addStatusRel(TaskStatusRelDTO statusRelDTO);

    /**
     * 功能描述:
     * 〈修改任务状态关系〉
     * @param statusRelDTO statusRelDTO
     * @return 正常返回:{@link TaskStatusRelVO}
     * @author 蝉鸣
     */
    TaskStatusRelVO editStatusRel(TaskStatusRelDTO statusRelDTO);

    /**
     * 功能描述:
     * 〈删除任务状态关系〉
     * @param statusRelId statusRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteStatusRel(Long statusRelId);
}