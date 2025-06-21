package com.platform.mesh.tmp.biz.modules.task.postrelhis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.dto.TaskPostRelHisDTO;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.po.TaskPostRelHis;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.vo.TaskPostRelHisVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务工职关系历史信息
 * @author 蝉鸣
 */
public interface ITaskPostRelHisService extends IService<TaskPostRelHis> {


    /**
     * 功能描述:
     * 〈获取当前任务工职关系历史信息〉
     * @param postRelHisId postRelHisId
     * @return 正常返回:{@link TaskPostRelHisVO}
     * @author 蝉鸣
     */
    TaskPostRelHisVO getPostRelHisInfoById(Long postRelHisId);

    /**
     * 功能描述:
     * 〈新增任务工职关系历史〉
     * @param postRelHisDTO postRelHisDTO
     * @return 正常返回:{@link TaskPostRelHisVO}
     * @author 蝉鸣
     */
    TaskPostRelHisVO addPostRelHis(TaskPostRelHisDTO postRelHisDTO);

    /**
     * 功能描述:
     * 〈修改任务工职关系历史〉
     * @param postRelHisDTO postRelHisDTO
     * @return 正常返回:{@link TaskPostRelHisVO}
     * @author 蝉鸣
     */
    TaskPostRelHisVO editPostRelHis(TaskPostRelHisDTO postRelHisDTO);

    /**
     * 功能描述:
     * 〈删除任务工职关系历史〉
     * @param postRelHisId postRelHisId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deletePostRelHis(Long postRelHisId);
}