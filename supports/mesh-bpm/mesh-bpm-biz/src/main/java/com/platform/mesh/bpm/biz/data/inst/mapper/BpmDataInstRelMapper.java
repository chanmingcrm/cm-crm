package com.platform.mesh.bpm.biz.data.inst.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.bpm.biz.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.utils.result.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 流程数据实例动作
 * @author 蝉鸣
 */
public interface BpmDataInstRelMapper extends BaseMapper<BpmDataInstRel> {

    /**
     * 功能描述:
     * 〈校验数据是否已经有运行中的流程审批〉
     * @param dataId dataId
     * @param tempProcessId tempProcessId
     * @param filterProcessId filterProcessId
     * @return 正常返回:{@link List<BpmDataInstRel>}
     * @author 蝉鸣
     */
    List<BpmDataInstRel> checkDataRelHasRun(
            @Param("dataId") Long dataId
            ,@Param("tempProcessId") Long tempProcessId
            ,@Param("runFlag") Integer runFlag
            ,@Param("filterProcessId") Boolean filterProcessId);
}

