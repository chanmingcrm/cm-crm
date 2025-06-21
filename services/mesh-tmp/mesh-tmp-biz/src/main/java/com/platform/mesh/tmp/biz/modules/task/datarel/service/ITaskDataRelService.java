package com.platform.mesh.tmp.biz.modules.task.datarel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.dto.TaskDataRelDTO;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.po.TaskDataRel;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.vo.TaskDataRelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务数据关联信息
 * @author 蝉鸣
 */
public interface ITaskDataRelService extends IService<TaskDataRel> {


    /**
     * 功能描述:
     * 〈获取当前任务数据关联信息〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link TaskDataRelVO}
     * @author 蝉鸣
     */
    TaskDataRelVO getDataRelInfoById(Long dataRelId);

    /**
     * 功能描述:
     * 〈新增任务数据关联〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link TaskDataRelVO}
     * @author 蝉鸣
     */
    TaskDataRelVO addDataRel(TaskDataRelDTO dataRelDTO);

    /**
     * 功能描述:
     * 〈修改任务数据关联〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link TaskDataRelVO}
     * @author 蝉鸣
     */
    TaskDataRelVO editDataRel(TaskDataRelDTO dataRelDTO);

    /**
     * 功能描述:
     * 〈删除任务数据关联〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteDataRel(Long dataRelId);
}