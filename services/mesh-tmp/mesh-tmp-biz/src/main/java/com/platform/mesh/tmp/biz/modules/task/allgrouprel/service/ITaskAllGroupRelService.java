package com.platform.mesh.tmp.biz.modules.task.allgrouprel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.dto.TaskAllGroupRelDTO;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.po.TaskAllGroupRel;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.vo.TaskAllGroupRelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务分组关系信息
 * @author 蝉鸣
 */
public interface ITaskAllGroupRelService extends IService<TaskAllGroupRel> {


    /**
     * 功能描述:
     * 〈获取当前任务分组关系信息〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link TaskAllGroupRelVO}
     * @author 蝉鸣
     */
    TaskAllGroupRelVO getAllGroupRelInfoById(Long allGroupRelId);

    /**
     * 功能描述:
     * 〈新增任务分组关系〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link TaskAllGroupRelVO}
     * @author 蝉鸣
     */
    TaskAllGroupRelVO addAllGroupRel(TaskAllGroupRelDTO allGroupRelDTO);

    /**
     * 功能描述:
     * 〈修改任务分组关系〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link TaskAllGroupRelVO}
     * @author 蝉鸣
     */
    TaskAllGroupRelVO editAllGroupRel(TaskAllGroupRelDTO allGroupRelDTO);

    /**
     * 功能描述:
     * 〈删除任务分组关系〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAllGroupRel(Long allGroupRelId);
}