package com.platform.mesh.bpm.biz.modules.data.noderel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.dto.BpmDataFormNodeRelDTO;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.po.BpmDataFormNodeRel;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.vo.BpmDataFormNodeRelVO;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 业务数据模板流程节点表单关系信息
 * @author 蝉鸣
 */
public interface IBpmDataFormNodeRelService extends IService<BpmDataFormNodeRel> {

    /**
     * 功能描述:
     * 〈获取当前业务数据模板流程节点表单关系信息〉
     * @param tempNodeId tempNodeId
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    BpmDataFormNodeRelVO getDataFormNodeRelInfoByTempNodeId(Long tempNodeId);

    /**
     * 功能描述:
     * 〈获取当前业务数据模板流程节点表单关系信息〉
     * @param nodeIds nodeIds
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    List<BpmDataFormNodeRelVO> getDataFormNodeRelInfoByNodeId(List<Long> nodeIds);

    /**
     * 功能描述:
     * 〈新增业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelDTO dataFormNodeRelDTO
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    BpmDataFormNodeRelVO addDataFormNodeRel(BpmDataFormNodeRelDTO dataFormNodeRelDTO);

    /**
     * 功能描述:
     * 〈批量新增业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelDTOS dataFormNodeRelDTOS
     * @author 蝉鸣
     */
    void batchAddDataFormNodeRel(List<BpmDataFormNodeRelDTO> dataFormNodeRelDTOS);

    /**
     * 功能描述:
     * 〈修改业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelDTO dataFormNodeRelDTO
     * @return 正常返回:{@link BpmDataFormNodeRelVO}
     * @author 蝉鸣
     */
    BpmDataFormNodeRelVO editDataFormNodeRel(BpmDataFormNodeRelDTO dataFormNodeRelDTO);

    /**
     * 功能描述:
     * 〈删除业务数据模板流程节点表单关系〉
     * @param dataFormNodeRelId dataFormNodeRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteDataFormNodeRel(Long dataFormNodeRelId);
}
