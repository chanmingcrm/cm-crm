package com.platform.mesh.tmp.biz.modules.task.userrelhis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.dto.TaskUserRelHisDTO;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.po.TaskUserRelHis;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.vo.TaskUserRelHisVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务人员历史信息
 * @author 蝉鸣
 */
public interface ITaskUserRelHisService extends IService<TaskUserRelHis> {


    /**
     * 功能描述:
     * 〈获取当前任务人员历史信息〉
     * @param userRelHisId userRelHisId
     * @return 正常返回:{@link TaskUserRelHisVO}
     * @author 蝉鸣
     */
    TaskUserRelHisVO getUserRelHisInfoById(Long userRelHisId);

    /**
     * 功能描述:
     * 〈新增任务人员历史〉
     * @param userRelHisDTO userRelHisDTO
     * @return 正常返回:{@link TaskUserRelHisVO}
     * @author 蝉鸣
     */
    TaskUserRelHisVO addUserRelHis(TaskUserRelHisDTO userRelHisDTO);

    /**
     * 功能描述:
     * 〈修改任务人员历史〉
     * @param userRelHisDTO userRelHisDTO
     * @return 正常返回:{@link TaskUserRelHisVO}
     * @author 蝉鸣
     */
    TaskUserRelHisVO editUserRelHis(TaskUserRelHisDTO userRelHisDTO);

    /**
     * 功能描述:
     * 〈删除任务人员历史〉
     * @param userRelHisId userRelHisId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteUserRelHis(Long userRelHisId);
}