package com.platform.mesh.bpm.biz.modules.hist.node.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.po.BpmHistNode;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.vo.BpmHistNodeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 流程节点信息
 * @author 蝉鸣
 */
public interface BpmHistNodeMapper extends BaseMapper<BpmHistNode> {


    List<BpmHistNodeVO> selectHistNodeByInstProcessId(@Param("instProcessId") Long instProcessId);
}

