package com.platform.mesh.tmp.biz.modules.task.post.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.post.domain.dto.TaskPostDTO;
import com.platform.mesh.tmp.biz.modules.task.post.domain.vo.TaskPostVO;
import com.platform.mesh.tmp.biz.modules.task.post.exception.TaskPostExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.post.mapper.TaskPostMapper;
import com.platform.mesh.tmp.biz.modules.task.post.service.ITaskPostService;
import com.platform.mesh.tmp.biz.modules.task.post.service.manual.TaskPostServiceManual;
import com.platform.mesh.tmp.biz.modules.task.post.domain.po.TaskPost;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务工职
 * @author 蝉鸣
 */
@Service
public class TaskPostServiceImpl extends ServiceImpl<TaskPostMapper, TaskPost> implements ITaskPostService  {

    @Autowired
    private TaskPostServiceManual taskPostServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param postId postId  
     * @return 正常返回:{@link TaskPostVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostVO getPostInfoById(Long postId) {
        TaskPost taskPost = this.getById(postId);
        return taskPostServiceManual.getPostInfoById(taskPost);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param postDTO postDTO
     * @return 正常返回:{@link TaskPostVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostVO addPost(TaskPostDTO postDTO) {
        TaskPost taskPost = BeanUtil.copyProperties(postDTO, TaskPost.class);
        this.save(taskPost);
        return BeanUtil.copyProperties(taskPost, TaskPostVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param postDTO postDTO
     * @return 正常返回:{@link TaskPostVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostVO editPost(TaskPostDTO postDTO) {
        if(ObjectUtil.isEmpty(postDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskPostDTO::getId);
            throw TaskPostExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskPost taskPost = BeanUtil.copyProperties(postDTO, TaskPost.class);
        this.updateById(taskPost);
        return BeanUtil.copyProperties(taskPost, TaskPostVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param postId postId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deletePost(Long postId) {
        
        return this.removeById(postId);
    }
}