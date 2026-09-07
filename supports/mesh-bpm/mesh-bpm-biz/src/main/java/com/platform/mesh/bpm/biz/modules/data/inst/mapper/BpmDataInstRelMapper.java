package com.platform.mesh.bpm.biz.modules.data.inst.mapper;


import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmPDTO;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.vo.BpmDataInstRelVO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 功能描述:
     * 〈根据模块获取运行中待审批数据〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<Long>}
     * @author 蝉鸣
     */
    MPage<Long> getRunDataIdsByModuleSchema(MPage<Long> mPage,@Param("pageDTO") BpmPDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取模块待审批数量总计〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    List<SimpVO> getRunDataNumByModuleSchema(@Param("pageDTO") BpmPDTO pageDTO);


    /**
     * 功能描述:
     * 〈获取当前数据关联流程信息〉
     * @param dataId dataId
     * @return 正常返回:{@link List<BpmDataInstRelVO>}
     * @author 蝉鸣
     */
    List<BpmDataInstRelVO> getDataInstRelByDataId(@Param("dataId") Long dataId);

    /**
     * 功能描述:
     * 〈删除流程数据关联〉
     * @param moduleId moduleId
     * @param dataIs dataIs
     * @param tenantId tenantId
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    void delBpmDataRel(@Param("moduleId")Long moduleId, @Param("dataIds")List<Long> dataIs);
}

