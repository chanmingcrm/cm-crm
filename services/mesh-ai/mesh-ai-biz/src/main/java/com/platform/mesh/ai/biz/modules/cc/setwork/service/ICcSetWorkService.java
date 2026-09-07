package com.platform.mesh.ai.biz.modules.cc.setwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.dto.CcSetWorkDTO;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.po.CcSetWork;
import com.platform.mesh.ai.biz.modules.cc.setwork.domain.vo.CcSetWorkVO;

import java.time.LocalTime;
import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 排班信息
 * @author 蝉鸣
 */
public interface ICcSetWorkService extends IService<CcSetWork> {

    /**
     * 功能描述:
     * 〈获取当前排班信息〉
     * @param workId workId
     * @return 正常返回:{@link CcSetWorkVO}
     * @author 蝉鸣
     */
    CcSetWorkVO getCcSetWorkById(Long workId);

    /**
     * 功能描述:
     * 〈新增排班〉
     * @param workDTO workDTO
     * @return 正常返回:{@link CcSetWorkVO}
     * @author 蝉鸣
     */
    CcSetWorkVO addCcSetWork(CcSetWorkDTO workDTO);

    /**
     * 功能描述:
     * 〈修改排班〉
     * @param workDTO workDTO
     * @return 正常返回:{@link CcSetWorkVO}
     * @author 蝉鸣
     */
    CcSetWorkVO editCcSetWork(CcSetWorkDTO workDTO);

    /**
     * 功能描述:
     * 〈删除排班〉
     * @param workId workId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCcSetWork(Long workId);

    /**
     * 功能描述:
     * 〈获取当前时间的排班〉
     * @return 正常返回:{@link List<CcSetWork>}
     * @author 蝉鸣
     */
    List<CcSetWork> getCurrentSetWork(LocalTime localTime);
}