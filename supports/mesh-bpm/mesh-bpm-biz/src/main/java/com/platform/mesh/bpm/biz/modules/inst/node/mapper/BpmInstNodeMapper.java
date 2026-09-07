package com.platform.mesh.bpm.biz.modules.inst.node.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.bo.BpmInstNodeBO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 流程节点信息
 * @author 蝉鸣
 */
public interface BpmInstNodeMapper extends BaseMapper<BpmInstNode> {

    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @return 正常返回:{@link List<BpmInstNodeVO>}
     * @author 蝉鸣
     */
    List<BpmInstNodeVO> selectInstNodeByInstProcessId(@Param("instProcessId") Long instProcessId);

    /**
     * 功能描述:
     * 〈根据运行状态获取实例下节点信息〉
     * @return 正常返回:{@link List<BpmInstNodeVO>}
     * @author 蝉鸣
     */
    List<BpmInstNodeVO> selectNodeVOByInstProcessIdAndRunFlag(@Param("instProcessId") Long instProcessId,@Param("runFlag")  Integer runFlag);

    /**
     * 功能描述:
     * 〈获取实例节点关联信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNodeBO}
     * @author 蝉鸣
     */
    BpmInstNodeBO getInstNodeData(@Param("instNodeId") Long instNodeId);

}

