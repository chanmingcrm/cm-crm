package com.platform.mesh.crm.biz.modules.tmp.task.baserel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.dto.TmpTaskBaseRelDTO;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.po.TmpTaskBaseRel;
import com.platform.mesh.crm.biz.modules.tmp.task.baserel.domain.vo.TmpTaskBaseRelVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务数据关联信息
 * @author 蝉鸣
 */
public interface ITmpTaskBaseRelService extends IService<TmpTaskBaseRel> {


    /**
     * 功能描述:
     * 〈获取当前任务数据关联信息〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link TmpTaskBaseRelVO}
     * @author 蝉鸣
     */
    TmpTaskBaseRelVO getDataRelInfoById(Long dataRelId);

    /**
     * 功能描述:
     * 〈新增任务数据关联〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link TmpTaskBaseRelVO}
     * @author 蝉鸣
     */
    TmpTaskBaseRelVO addDataRel(TmpTaskBaseRelDTO dataRelDTO);

    /**
     * 功能描述:
     * 〈删除任务数据关联〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteDataRel(Long dataRelId);
}
