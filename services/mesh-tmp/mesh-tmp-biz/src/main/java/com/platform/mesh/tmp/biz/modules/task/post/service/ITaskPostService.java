package com.platform.mesh.tmp.biz.modules.task.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.post.domain.dto.TaskPostDTO;
import com.platform.mesh.tmp.biz.modules.task.post.domain.po.TaskPost;
import com.platform.mesh.tmp.biz.modules.task.post.domain.vo.TaskPostVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务工职信息
 * @author 蝉鸣
 */
public interface ITaskPostService extends IService<TaskPost> {


    /**
     * 功能描述:
     * 〈获取当前任务工职信息〉
     * @param postId postId
     * @return 正常返回:{@link TaskPostVO}
     * @author 蝉鸣
     */
    TaskPostVO getPostInfoById(Long postId);

    /**
     * 功能描述:
     * 〈新增任务工职〉
     * @param postDTO postDTO
     * @return 正常返回:{@link TaskPostVO}
     * @author 蝉鸣
     */
    TaskPostVO addPost(TaskPostDTO postDTO);

    /**
     * 功能描述:
     * 〈修改任务工职〉
     * @param postDTO postDTO
     * @return 正常返回:{@link TaskPostVO}
     * @author 蝉鸣
     */
    TaskPostVO editPost(TaskPostDTO postDTO);

    /**
     * 功能描述:
     * 〈删除任务工职〉
     * @param postId postId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deletePost(Long postId);
}