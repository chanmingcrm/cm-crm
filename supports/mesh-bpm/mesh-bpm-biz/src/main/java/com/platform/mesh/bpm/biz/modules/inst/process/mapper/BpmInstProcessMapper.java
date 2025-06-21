package com.platform.mesh.bpm.biz.modules.inst.process.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.bo.BpmInstProcessTodoBO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessOaVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 流程过程信息
 * @author 蝉鸣
 */
public interface BpmInstProcessMapper extends BaseMapper<BpmInstProcess> {

    MPage<BpmInstProcessOaVO> getProcessInstTodo(IPage<BpmInstProcess> page, @Param("todoBo") BpmInstProcessTodoBO processTodoBO);

    MPage<BpmInstProcessOaVO> getProcessInstDone(IPage<BpmInstProcess> page, @Param("todoBo") BpmInstProcessTodoBO processTodoBO);

    MPage<BpmInstProcessOaVO> getProcessInstEnd(IPage<BpmInstProcess> page, @Param("todoBo") BpmInstProcessTodoBO processTodoBO);

    MPage<BpmInstProcessOaVO> getProcessInstFollow(IPage<BpmInstProcess> processMPage, @Param("todoBo") BpmInstProcessTodoBO processTodoBO);
}

