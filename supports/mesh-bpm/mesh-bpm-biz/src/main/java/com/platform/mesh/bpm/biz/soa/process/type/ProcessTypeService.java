package com.platform.mesh.bpm.biz.soa.process.type;


import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;

/**
 * @description 流程类型工厂
 * @author 蝉鸣
 */
public interface ProcessTypeService {

    /**
     * 功能描述:
     * 〈过程类型〉
     * @return 正常返回:{@link ProcessTypeEnum}
     * @author 蝉鸣
     */
    ProcessTypeEnum processType();

    /**
     * 功能描述:
     * 〈添加模板〉
     * @param addDTO addDTO
     * @author 蝉鸣
     */
    void addTemp(BpmTempProcessDesignDTO addDTO);

    /**
     * 功能描述:
     * 〈获取模板〉
     * @param getVO getVO
     * @author 蝉鸣
     */
    void getTemp(BpmTempProcessDesignVO getVO);

    /**
     * 功能描述:
     * 〈删除模板〉
     * @param tempProcessId tempProcessId
     * @author 蝉鸣
     */
    void delTemp(Long tempProcessId);

    /**
     * 功能描述:
     * 〈初始化实例〉
     * @param instProcess instProcess
     * @author 蝉鸣
     */
     void initInst(BpmInstProcess instProcess);


    /**
     * 功能描述:
     * 〈添加流程实例子项〉
     * @param addSubDTO addSubDTO
     * @author 蝉鸣
     */
    void addInstSub(BpmInstNodeSubDTO addSubDTO);

    /**
     * 功能描述:
     * 〈添加历史记录〉
     * @param instNode instNode
     * @author 蝉鸣
     */
    void addHist(BpmInstNode instNode);

    /**
     * 功能描述:
     * 〈获取实例〉
     * @param getVO getVO
     * @author 蝉鸣
     */
    void getInst(BpmInstProcessDesignVO getVO);

    /**
     * 功能描述:
     * 〈获取实例历史〉
     * @param getVO getVO
     * @author 蝉鸣
     */
    void getHist(BpmHistProcessInfoVO getVO);

    /**
     * 功能描述:
     * 〈拷贝流程模板〉
     * @param sourceProcess sourceProcess
     * @param targetProcess targetProcess
     * @author 蝉鸣
     */
    void copyTemp(BpmTempProcess sourceProcess, BpmTempProcess targetProcess);

}
