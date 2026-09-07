package com.platform.mesh.bpm.biz.modules.data.nodedata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.dto.BpmDataFormNodeDataDTO;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.po.BpmDataFormNodeData;
import com.platform.mesh.bpm.biz.modules.data.nodedata.domain.vo.BpmDataFormNodeDataVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 业务数据实例流程节点表单数据信息
 * @author 蝉鸣
 */
public interface IBpmDataFormNodeDataService extends IService<BpmDataFormNodeData> {

    /**
     * 功能描述:
     * 〈获取当前业务数据实例流程节点表单数据信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmDataFormNodeDataVO}
     * @author 蝉鸣
     */
    BpmDataFormNodeDataVO getDataFormNodeDataInfoByInstNodeId(Long instNodeId);

    /**
     * 功能描述:
     * 〈新增业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataDTO dataFormNodeDataDTO
     * @return 正常返回:{@link BpmDataFormNodeDataVO}
     * @author 蝉鸣
     */
    BpmDataFormNodeDataVO addDataFormNodeData(BpmDataFormNodeDataDTO dataFormNodeDataDTO);

    /**
     * 功能描述:
     * 〈修改业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataDTO dataFormNodeDataDTO
     * @return 正常返回:{@link BpmDataFormNodeDataVO}
     * @author 蝉鸣
     */
    BpmDataFormNodeDataVO editDataFormNodeData(BpmDataFormNodeDataDTO dataFormNodeDataDTO);

    /**
     * 功能描述:
     * 〈删除业务数据实例流程节点表单数据〉
     * @param dataFormNodeDataId dataFormNodeDataId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteDataFormNodeData(Long dataFormNodeDataId);

    /**
     * 功能描述:
     * 〈获取阶段流程名称〉
     * @param instProcessId instProcessId
     * @author 蝉鸣
     */
    String getProcessStageName(Long instProcessId);

}
