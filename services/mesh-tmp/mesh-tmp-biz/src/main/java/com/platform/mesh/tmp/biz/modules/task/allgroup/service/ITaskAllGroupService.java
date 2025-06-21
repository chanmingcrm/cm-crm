package com.platform.mesh.tmp.biz.modules.task.allgroup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.dto.TaskAllGroupDTO;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.po.TaskAllGroup;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.vo.TaskAllGroupVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务分组信息
 * @author 蝉鸣
 */
public interface ITaskAllGroupService extends IService<TaskAllGroup> {


    /**
     * 功能描述:
     * 〈获取当前任务分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link TaskAllGroupVO}
     * @author 蝉鸣
     */
    TaskAllGroupVO getAllGroupInfoById(Long allGroupId);

    /**
     * 功能描述:
     * 〈新增任务分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link TaskAllGroupVO}
     * @author 蝉鸣
     */
    TaskAllGroupVO addAllGroup(TaskAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈修改任务分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link TaskAllGroupVO}
     * @author 蝉鸣
     */
    TaskAllGroupVO editAllGroup(TaskAllGroupDTO allGroupDTO);

    /**
     * 功能描述:
     * 〈删除任务分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroup(Long allGroupId);
}