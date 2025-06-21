package com.platform.mesh.tmp.biz.modules.task.postrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.dto.TaskPostRelDTO;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.po.TaskPostRel;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.vo.TaskPostRelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务工职关系信息
 * @author 蝉鸣
 */
public interface ITaskPostRelService extends IService<TaskPostRel> {


    /**
     * 功能描述:
     * 〈获取当前任务工职关系信息〉
     * @param postRelId postRelId
     * @return 正常返回:{@link TaskPostRelVO}
     * @author 蝉鸣
     */
    TaskPostRelVO getPostRelInfoById(Long postRelId);

    /**
     * 功能描述:
     * 〈新增任务工职关系〉
     * @param postRelDTO postRelDTO
     * @return 正常返回:{@link TaskPostRelVO}
     * @author 蝉鸣
     */
    TaskPostRelVO addPostRel(TaskPostRelDTO postRelDTO);

    /**
     * 功能描述:
     * 〈修改任务工职关系〉
     * @param postRelDTO postRelDTO
     * @return 正常返回:{@link TaskPostRelVO}
     * @author 蝉鸣
     */
    TaskPostRelVO editPostRel(TaskPostRelDTO postRelDTO);

    /**
     * 功能描述:
     * 〈删除任务工职关系〉
     * @param postRelId postRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deletePostRel(Long postRelId);
}