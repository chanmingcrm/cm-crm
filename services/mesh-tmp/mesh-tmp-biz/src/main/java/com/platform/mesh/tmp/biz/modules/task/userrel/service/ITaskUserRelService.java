package com.platform.mesh.tmp.biz.modules.task.userrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.dto.TaskUserRelDTO;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.po.TaskUserRel;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.vo.TaskUserRelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务人员信息
 * @author 蝉鸣
 */
public interface ITaskUserRelService extends IService<TaskUserRel> {


    /**
     * 功能描述:
     * 〈获取当前任务人员信息〉
     * @param userRelId userRelId
     * @return 正常返回:{@link TaskUserRelVO}
     * @author 蝉鸣
     */
    TaskUserRelVO getUserRelInfoById(Long userRelId);

    /**
     * 功能描述:
     * 〈新增任务人员〉
     * @param userRelDTO userRelDTO
     * @return 正常返回:{@link TaskUserRelVO}
     * @author 蝉鸣
     */
    TaskUserRelVO addUserRel(TaskUserRelDTO userRelDTO);

    /**
     * 功能描述:
     * 〈修改任务人员〉
     * @param userRelDTO userRelDTO
     * @return 正常返回:{@link TaskUserRelVO}
     * @author 蝉鸣
     */
    TaskUserRelVO editUserRel(TaskUserRelDTO userRelDTO);

    /**
     * 功能描述:
     * 〈删除任务人员〉
     * @param userRelId userRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteUserRel(Long userRelId);
}