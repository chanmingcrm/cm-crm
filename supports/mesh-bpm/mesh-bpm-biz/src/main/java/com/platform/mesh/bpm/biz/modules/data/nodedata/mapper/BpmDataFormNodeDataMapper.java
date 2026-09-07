package com.platform.mesh.bpm.biz.modules.data.nodedata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.po.BpmDataFormNodeData;
import org.apache.ibatis.annotations.Param;

/**
 * @description 业务数据实例流程节点表单数据
 * @author 蝉鸣
 */
public interface BpmDataFormNodeDataMapper extends BaseMapper<BpmDataFormNodeData> {

    BpmDataFormNodeData getLastDataFormNodeDataByInstProcessId(@Param("instProcessId") Long instProcessId);

}