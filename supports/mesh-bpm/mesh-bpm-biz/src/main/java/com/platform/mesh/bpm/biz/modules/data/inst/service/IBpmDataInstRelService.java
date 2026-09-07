package com.platform.mesh.bpm.biz.modules.data.inst.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmPDTO;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.vo.BpmDataInstRelVO;
import com.platform.mesh.bpm.biz.modules.data.inst.service.manual.BpmDataInstRelServiceManual;
import com.platform.mesh.mybatis.plus.extention.MPage;

import java.util.List;
import java.util.Map;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 数据流程实例
 * @author 蝉鸣
 */
public interface IBpmDataInstRelService extends IService<BpmDataInstRel> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmDataInstRelServiceManual}
     * @author 蝉鸣
     */
    BpmDataInstRelServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈根据表单Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link List<BpmDataInstRelVO>}
     * @author 蝉鸣
     */
    List<BpmDataInstRelVO> getDataInstRelByDataId(Long dataId);

    /**
     * 功能描述:
     * 〈校验数据是否已经有运行中的流程审批〉
     * @param dataId dataId
     * @param tempProcessId tempProcessId
     * @param filterProcessId filterProcessId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean checkDataRelHasRun(Long dataId, Long tempProcessId, Boolean filterProcessId);

    /**
     * 功能描述:
     * 〈根据模块获取运行中待审批数据〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<Long>}
     * @author 蝉鸣
     */
    MPage<Long> getRunDataIdsByModuleSchema(BpmPDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取模块待审批数量总计〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    Map<String, Long> getRunDataNumByModuleSchema(BpmPDTO pageDTO);

    /**
     * 功能描述:
     * 〈删除流程数据关联〉
     * @param moduleId moduleId
     * @param dataIs dataIs
     * @param tenantId tenantId
     * @author 蝉鸣
     */
    void delBpmDataRel(Long moduleId, List<Long> dataIs);
}

